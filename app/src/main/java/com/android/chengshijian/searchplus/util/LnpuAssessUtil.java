package com.android.chengshijian.searchplus.util;

import android.support.annotation.NonNull;

import com.android.chengshijian.searchplus.listener.OnAssessListener;
import com.android.chengshijian.searchplus.listener.OnStringRequestListener;
import com.android.chengshijian.searchplus.model.Assess;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.User;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.orhanobut.logger.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * 一键评教工具类
 *
 * Created by 程世健 on 2017/7/17.
 */

public class LnpuAssessUtil {
    private static List<String> mNames = new ArrayList<>();
    private static int mCurrentThreadNum = 0;//当前评教任务数量
    private static Timer mTimer = new Timer();

    public static void oneKeyAsess(final OnAssessListener listener) {
        final User user = DataUtil.getCurrentSchoolUser();
        HttpRequestUtil.stringRequestByPost(Constant.ASSESS_MAIN_URL, new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {
                //第二步，获得评教信息（老师）
                HttpRequestUtil.stringRequestByPost(Constant.ASSESS_TEACHER_INFO_URL + getSeriesID(s), new OnStringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        if (s.contains(Constant.USER_NOT_LOGIN_IN)) {
                            listener.onLoginDataOutOfTime();

                        } else {
                            final List<Assess> infos = getAssesses(s);
                            listener.onAssessTeacherDataReceived(infos);
                            mCurrentThreadNum = infos.size();
                            final int[] index = {0};
                            mTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (infos.size() > index[0]) {
                                        startAssessForTeacher(infos.get(index[0]++), listener);
                                    }
                                }
                            }, 0, 3000);
                        }
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return DataUtil.getAssessCookieMap();
                    }

                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("SeriesID", "962");
                        return map;
                    }

                    @Override
                    public void parseNetworkResponse(NetworkResponse response) {

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Class volleyErrorClass = volleyError.getClass();
                        listener.onAssessTeacherDataError(volleyErrorClass);

                    }
                });
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new HashMap<>();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("WebUserNO", user.getAccount());
                map.put("Password", user.getPassword());
                return map;
            }

            @Override
            public void parseNetworkResponse(NetworkResponse response) {
                Map<String, String> headers = response.headers;
                String rawCookies = headers.get(Constant.SET_COOKIE).replace("; path=/", "");
                SharedPreferencesHelper.putStringValue(Constant.COOKIE_ASSESS, rawCookies);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Class volleyErrorClass = volleyError.getClass();
                listener.onAssessBaseDataError(volleyErrorClass);

            }
        });
    }

    @NonNull
    private static List<Assess> getAssesses(String s) {
        Document document = AnalyzeHtmlUtil.formatHtmlString2Document(s);
        Elements elements = document.getElementsByAttribute("onClick");
        for (Element element : elements) {
            if (!("").equals(element.getElementsByAttributeValue("height", "25").text())) {
                mNames.add(element.getElementsByAttributeValue("height", "25").text().split("\\s+")[5]);
                break;
            }
        }

        List<String> strings = new ArrayList<>();
        for (Element element : elements) {
            if (!"GoPerPage()".equals(element.attr("onClick")) && !"ExitPage()".equals(element.attr("onClick")) && !"GeneralComment()".equals(element.attr("onClick"))) {
                strings.add(element.attr("onClick").replace("javascript:document.location='ACTIONJSSHOWCONTENTRESULT.APPPROCESS?", "").replace("';showQuerying()", ""));
            }
        }

        List<String[]> stringList = new ArrayList<>();
        for (String s1 : strings) {
            stringList.add(s1.split("&"));
        }

        final List<Assess> infos = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            Assess assess = new Assess();
            assess.setSeriesID(stringList.get(i)[0].split("=")[1]);
            if (stringList.get(i)[1].split("=").length != 1) {
                assess.setPaperID(stringList.get(i)[1].split("=")[1]);
            } else {
                assess.setPaperID("");
            }
            assess.setTaskID(stringList.get(i)[2].split("=")[1]);
            assess.setTeacherNO(stringList.get(i)[3].split("=")[1]);
            assess.setName(mNames.get(i));
            infos.add(assess);
        }
        return infos;
    }


    private static String getSeriesID(String s) {
        Elements links = Jsoup.parse(s).select("tr");
        for (Element e : links) {
            if (e.attr("onClick").contains("javascript:document.location='ACTIONJSCHOSEAPPRAISECONTENT.APPPROCESS?")) {
                return e.attr("onClick").replace("javascript:document.location='ACTIONJSCHOSEAPPRAISECONTENT.APPPROCESS?SeriesID=", "").replace("';showQuerying()", "");
            }
        }
        return null;
    }

    private static String getStartAssessForTeacherURL(Assess assess) {
        return Constant.START_ASSESS_FOR_TEACHER_URL + assess.getSeriesID() + Constant.PAPER_ID + assess.getPaperID() + Constant.TASK_ID + assess.getTaskID() + Constant.TEACHER_NO + assess.getTeacherNO();
    }

    private static void startAssessForTeacher(final Assess assess, final OnAssessListener listener) {
        listener.onStartAssessForTeacher(assess);
        HttpRequestUtil.stringRequestByGet(getStartAssessForTeacherURL(assess), new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {
                Document document = AnalyzeHtmlUtil.formatHtmlString2Document(s);
                Elements elements = document.getElementsByAttributeValue("name", "ResultID");
                Elements elements1 = document.getElementsByAttributeValue("name", "ItemTypeID");
                Elements elements2 = document.getElementsByAttributeValue("name", "adjustCode");
                List<String> strings = getStrings(elements, elements1, elements2);
                assess(strings, assess, listener);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return DataUtil.getAssessCookieMap();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("SeriesID", assess.getSeriesID());
                map.put("PaperID", assess.getPaperID());
                map.put("TaskID", assess.getTaskID());
                map.put("TeacherNO", assess.getTeacherNO());
                return map;
            }

            @Override
            public void parseNetworkResponse(NetworkResponse response) {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logger.e(volleyError.getMessage());
            }
        });
    }

    @NonNull
    private static List<String> getStrings(Elements elements, Elements elements1, Elements elements2) {
        List<String> strings = new ArrayList<>();
        for (Element element : elements1) {
            strings.add(element.attr("value"));
        }
        for (Element element : elements) {
            strings.add(element.attr("value"));
        }

        for (Element element : elements2) {
            strings.add(element.attr("value"));
        }
        return strings;
    }

    private static void assess(final List<String> strings, final Assess assess, final OnAssessListener listener) {
        String result;
        HttpPost httpRequest = new HttpPost(Constant.ASSESS_URL);//创建HttpPost对象

        if (strings.size() != 1) {
            httpRequest.addHeader(Constant.COOKIE, DataUtil.getAssessCookie());
            List<NameValuePair> params = getParams(strings, assess);
            try {
                httpRequest.setEntity(new UrlEncodedFormEntity(params, org.apache.http.protocol.HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    result = EntityUtils.toString(httpEntity);//取出应答字符串
                    mCurrentThreadNum--;
                    listener.onAssessForTeacherFinished(result, assess);
                    if (mCurrentThreadNum == 0) {
                        mTimer.cancel();
                        listener.onAssessSuccess();
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                result = e.getMessage();
            }
        }
    }

    private static List<NameValuePair> getParams(List<String> strings, Assess assess) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("ItemTypeScore" + strings.get(0), "99"));
        params.add(new BasicNameValuePair("ItemTypeID", strings.get(0)));
        params.add(new BasicNameValuePair("ResultMemo", ""));
        params.add(new BasicNameValuePair("adjustCode", strings.get(strings.size() - 1)));
        params.add(new BasicNameValuePair("SeriesID", assess.getSeriesID()));
        params.add(new BasicNameValuePair("PaperID", assess.getPaperID()));
        params.add(new BasicNameValuePair("TaskID", assess.getTaskID()));
        params.add(new BasicNameValuePair("TeacherNO", assess.getTeacherNO()));
        params.add(new BasicNameValuePair("PaperScore", "99"));
        for (int i = 1; i <= strings.size() - 3; i++) {
            params.add(new BasicNameValuePair("ResultID", strings.get(i)));
            params.add(new BasicNameValuePair("Score" + strings.get(i), "100"));
        }
        params.add(new BasicNameValuePair("ResultID", strings.get(strings.size() - 2)));
        params.add(new BasicNameValuePair("Score" + strings.get(strings.size() - 2), "80"));
        return params;
    }
}
