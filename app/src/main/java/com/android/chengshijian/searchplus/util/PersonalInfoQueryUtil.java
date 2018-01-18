package com.android.chengshijian.searchplus.util;

import android.support.annotation.NonNull;

import com.android.chengshijian.searchplus.listener.OnQueryPersonalInfoListener;
import com.android.chengshijian.searchplus.listener.OnStringRequestListener;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.Personal;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 个人信息查询工具类
 *
 * Created by ChengShiJian on 2018/1/13.
 */

public class PersonalInfoQueryUtil {

    public static void getPersonalInfo(final OnQueryPersonalInfoListener listener) {
        listener.onStartQuery();
        HttpRequestUtil.stringRequestByGet(Constant.PERSONAL_INFO_URL, new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {
                List<String> c = getLists(s);
                Personal p = getPersonal(c);
                listener.onSuccess(p);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return DataUtil.getGradeCookieMap();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return null;
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
    private static Personal getPersonal(List<String> c) {
        Personal p=new Personal();
        p.setAccount(c.get(0));
        p.setName(c.get(1));
        p.setSex(c.get(2));
        p.setBirth(c.get(3));
        p.setId(c.get(4));
        p.setDepart(c.get(5));
        p.setEnterTime(c.get(6));
        p.setMajor(c.get(7));
        p.setLeaveSchoolLimit(c.get(8));
        p.setClassB(c.get(9));
        p.setClassN(c.get(10));
        p.setDevelopLevel(c.get(11));
        return p;
    }

    @NonNull
    private static List<String> getLists(String s) {
        Document document = AnalyzeHtmlUtil.formatHtmlString2Document(s);
        Elements e1 = document.getElementsByTag("tr");
        List<String> c = new ArrayList<>();
        for (int i = 2; i < e1.size(); i++) {
            for (Element e2 : e1.get(i).getElementsByTag("td")) {
                if (e2.attr("colspan").equals("2") || e2.attr("width").equals("35%")) {
                    if (!" ".equals(e2.text())) {
                        c.add(e2.text());
                    }
                }
            }
        }
        return c;
    }
}
