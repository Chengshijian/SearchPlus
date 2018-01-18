package com.android.chengshijian.searchplus.util;

import android.support.annotation.NonNull;

import com.android.chengshijian.searchplus.listener.OnQueryLectureListener;
import com.android.chengshijian.searchplus.listener.OnQueryLectureTermListener;
import com.android.chengshijian.searchplus.listener.OnStringRequestListener;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.Lecture;
import com.android.chengshijian.searchplus.model.LectureTermResult;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.orhanobut.logger.Logger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 讲座查询工具类
 *
 * Created by ChengShiJian on 2018/1/13.
 */

public class LectureQueryUtil {

    public static void getLectures(final String year, final OnQueryLectureListener listener) {
        listener.onStartQuery();
        HttpRequestUtil.stringRequestByPost(Constant.LECTURE_PUBLISHED_URL, new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {
                Logger.i(s);
                if (s.contains(Constant.HAVE_PUBLISHED)) {

                    Document document = AnalyzeHtmlUtil.formatHtmlString2Document(s);
                    Elements elements1 = document.getElementsByClass("color-rowNext");
                    Elements elements2 = document.getElementsByClass("color-row");
                    List<List<String>> infos = getLists(elements1, elements2);

                    List<Lecture> lectures = new ArrayList<>();
                    for (int i = 0; i < infos.size(); i++) {
                        Lecture lecture = new Lecture();
                        lecture.setNum(infos.get(i).get(0));
                        lecture.setName(infos.get(i).get(1));
                        lecture.setUser(infos.get(i).get(2));
                        lecture.setTime(infos.get(i).get(3));
                        lecture.setAddress(infos.get(i).get(4));
                        lecture.setDepart(infos.get(i).get(5));
                        lecture.setLimitPersonNum(infos.get(i).get(6));
                        lecture.setPublishedPlan(infos.get(i).get(7));
                        if (infos.get(i).size() == 10) {
                            lecture.setNumOfTerm(infos.get(i).get(8));
                        }
                        lectures.add(lecture);
                    }
                    if(lectures.size()>0) {
                        listener.onSuccess(lectures);
                    }else {
                        listener.onLectureSizeIsZero();
                    }

                } else {
                    listener.onFailed();
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return DataUtil.getGradeCookieMap();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("op", "1");
                map.put("firstyear", year);
                return map;
            }

            @Override
            public void parseNetworkResponse(NetworkResponse response) {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onErrorResponse(volleyError);
            }
        });

    }

    public static void getLectureTerms(final OnQueryLectureTermListener listener) {
        listener.onStartQuery();
        HttpRequestUtil.stringRequestByPost(Constant.LECTURE_PUBLISHED_URL, new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {

                /**
                 * 解析如下html字符串,装进Map集合中
                 *
                 * <select name="firstyear" id="firstyear" style="width:200px" onChange="sm()">
                 *     <option  value="20172">2017-2018学年第二学期</option> <option  value="20171">2017-2018学年第一学期</option> <option  value="20162">2016-2017学年第二学期</option> <option  value="20161">2016-2017学年第一学期</option> <option  value="20152">2015-2016学年第二学期</option> <option  value="20151">2015-2016学年第一学期</option> <option  value="20142">2014-2015学年第二学期</option> <option  value="20141">2014-2015学年第一学期</option> <option  value="20132">2013-2014学年第二学期</option> <option  selected  value="20131">2013-2014学年第一学期</option> <option  value="20122">2012-2013学年第二学期</option> <option  value="20121">2012-2013学年第一学期</option> <option  value="20112">2011-2012学年第二学期</option> <option  value="20111">2011-2012学年第一学期</option> <option  value="20102">2010-2011学年第二学期</option> <option  value="20101">2010-2011学年第一学期</option>
                 *     </select></td>
                 */
                Document document = AnalyzeHtmlUtil.formatHtmlString2Document(s);
                Element element = document.getElementById("firstyear");
                Elements elements = element.getElementsByTag("option");
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                for (Element e : elements) {
                    map.put(e.attr("value"), e.text());
                }

                listener.onSuccess(new LectureTermResult(map));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return DataUtil.getGradeCookieMap();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("op", "1");
                return map;
            }

            @Override
            public void parseNetworkResponse(NetworkResponse response) {

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onErrorResponse(volleyError);
            }
        });
    }


    @NonNull
    private static List<List<String>> getLists(Elements elements1, Elements elements2) {
        List<List<String>> infos = new ArrayList<>();
        for (Element element : elements1) {
            List<String> infos1 = new ArrayList<>();
            for (Element element1 : element.getElementsByTag("td")) {
                infos1.add(element1.text());
            }
            infos.add(infos1);
        }

        for (Element element : elements2) {
            List<String> infos1 = new ArrayList<>();
            for (Element element1 : element.getElementsByTag("td")) {
                infos1.add(element1.text());
            }
            infos.add(infos1);
        }
        return infos;
    }
}