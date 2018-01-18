package com.android.chengshijian.searchplus.util;

import android.support.annotation.NonNull;

import com.android.chengshijian.searchplus.listener.OnQueryIndCreditListener;
import com.android.chengshijian.searchplus.listener.OnStringRequestListener;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.IndCredit;
import com.android.chengshijian.searchplus.model.IndCreditResult;
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
 * 学分查询工具类
 *
 * Created by ChengShiJian on 2018/1/12.
 */

public class CreditQueryUtil {

    /**
     * 获取学分
     *
     * @param listener
     */
    public static void getIndCredits(final OnQueryIndCreditListener listener) {
        listener.onStartQuery();
        HttpRequestUtil.stringRequestByGet(Constant.INDEPENDENT_CREDIT_URL, new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {
                if (s.contains(Constant.RECORD_IS_ZERO)) {
                    listener.onRecordIs0();
                } else {
                    List<IndCredit> credits = getIndCredits(s);
                    if (credits.size() == 0) {
                        listener.onNotHaveCredit();
                    } else {
                        listener.onSuccess(getIndCreditResult(credits));
                    }

                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return DataUtil.getGradeCookieMap();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return DataUtil.getIndCreditParamsMap();
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
    private static List<IndCredit> getIndCredits(String s) {
        Document document = AnalyzeHtmlUtil.formatHtmlString2Document(s);
        Elements elements = document.getElementsByClass("color-rowNext");
        List<IndCredit> credits = new ArrayList<>();
        for (Element element : elements) {
            Elements e = element.getElementsByTag("td");
            IndCredit credit = new IndCredit();
            credit.setId(e.get(3).text());
            credit.setName(e.get(4).text());
            credit.setIdTime(e.get(5).text());
            credit.setCreditType(e.get(6).text());
            credit.setCredit(e.get(8).text());
            credits.add(credit);
        }
        return credits;
    }

    private static IndCreditResult getIndCreditResult(List<IndCredit> credits) {
        float totalCredit = 0;
        for (IndCredit credit : credits) {
            totalCredit += Float.valueOf(credit.getCredit());
        }
        return new IndCreditResult(credits, String.valueOf(totalCredit));
    }
}
