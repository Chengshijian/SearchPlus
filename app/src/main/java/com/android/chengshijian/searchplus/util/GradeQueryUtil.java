package com.android.chengshijian.searchplus.util;

import android.support.annotation.NonNull;

import com.android.chengshijian.searchplus.listener.OnQueryGradeListListener;
import com.android.chengshijian.searchplus.listener.OnQueryGradeTermListener;
import com.android.chengshijian.searchplus.listener.OnStringRequestListener;
import com.android.chengshijian.searchplus.listener.OnQueryGradeListener;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.Grade;
import com.android.chengshijian.searchplus.model.GradeList;
import com.android.chengshijian.searchplus.model.GradeListResult;
import com.android.chengshijian.searchplus.model.GradeListTitle;
import com.android.chengshijian.searchplus.model.GradeResult;
import com.android.chengshijian.searchplus.model.User;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * 成绩查询工具类
 *
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public class GradeQueryUtil {

    public static void getTerms(final OnQueryGradeTermListener listener) {
        listener.onStartQuery();
        HttpRequestUtil.stringRequestByGet(Constant.TERM_QUERY_URL, new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {
                Document document = AnalyzeHtmlUtil.formatHtmlString2Document(s);
                Elements elements = document.getElementsByTag("option");
                List<String> datas = new LinkedList<>();
                for (Element element : elements) {
                    datas.add(element.text());
                }
                int maxTerm = Integer.valueOf(elements.get(0).attr("value"));
                listener.onResponse(datas, maxTerm);
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

    public static void getGrades(final String yearNum, final OnQueryGradeListener listener) {
        listener.onStartQuery();
        HttpRequestUtil.stringRequestByPost(Constant.QUERY_GRADES_RESULT_REQUEST_URL, new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {
                if (Constant.RECORD_IS_ZERO.indexOf(s) == -1) {
                    generateData(s, listener);
                } else {
                    listener.onRecordIs0();
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return DataUtil.getGradeCookieMap();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("YearTermNO", yearNum);
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

    //生成数据
    private static void generateData(String htmlString, OnQueryGradeListener listener) {
        Document document = AnalyzeHtmlUtil.formatHtmlString2Document(htmlString);
        List<String> userInfo = AnalyzeHtmlUtil.formatHtmlBySelect(document);
        List<String> gradeInfoRowNext = AnalyzeHtmlUtil.formatHtmlByClass(document, "color-rowNext");
        List<String> gradeInfoRow = AnalyzeHtmlUtil.formatHtmlByClass(document, "color-row");
        //学号: 1511030305    姓名: 辛宏民

        String[] userString = userInfo.get(0).split("\\s+");
        User user = new User();
        user.setAccount(userString[1]);
        user.setName(userString[3]);

        List<Grade> examAGrades = new ArrayList<>();
        List<Grade> testAGrades = new ArrayList<>();
        List<Grade> examBGrades = new ArrayList<>();
        List<Grade> testBGrades = new ArrayList<>();
        List<Grade> examRGrades = new ArrayList<>();
        List<Grade> testRGrades = new ArrayList<>();
        for (String string : gradeInfoRowNext) {
            analyzeData(examAGrades, testAGrades, examBGrades, testBGrades, examRGrades, testRGrades, string);
        }
        for (String string : gradeInfoRow) {
            analyzeData(examAGrades, testAGrades, examBGrades, testBGrades, examRGrades, testRGrades, string);
        }

        float totalGrade = 0;
        float totalCredit = 0;
        for (Grade grade : examAGrades) {
            totalGrade += Float.valueOf(grade.getTotalGrade()) * Float.valueOf(grade.getCredit());
            totalCredit += Float.valueOf(grade.getCredit());
        }

        for (Grade grade : testAGrades) {
            totalGrade += Float.valueOf(GradeFilter.filter2Grade(grade.getTotalGrade())) * Float.valueOf(grade.getCredit());
            totalCredit += Float.valueOf(grade.getCredit());
        }
        listener.onSuccess(new GradeResult(user, examAGrades, testAGrades, examBGrades, testBGrades, examRGrades, testRGrades, String.valueOf(totalGrade / totalCredit)));
    }


    //分析数据
    private static void analyzeData(List<Grade> examAGrades, List<Grade> testAGrades, List<Grade> examBGrades, List<Grade> testBGrades, List<Grade> examRGrades, List<Grade> testRGrades, String string) {
        if (string.contains("考查")) {
            splitABR(testAGrades, testBGrades, testRGrades, string);
        } else if (string.contains("考试")) {
            splitABR(examAGrades, examBGrades, examRGrades, string);
        }
    }


    private static void splitABR(List<Grade> grades, List<Grade> otherGrades, List<Grade> theOtherGrades, String string) {
        if (string.contains("期末考试")) {
            addToList(grades, string);
        } else if (string.contains("B考")) {
            addToList(otherGrades, string);
        } else if (string.contains("重修考试")) {
            addToList(theOtherGrades, string);
        }
    }

    //封装
    private static void addToList(List<Grade> grades, String string) {
        // 专业基础课 110265  操作系统实验 考查 24 .75 期末考试 中 中
        String[] gradeString = string.split("\\s+");
        Grade grade = new Grade();
        grade.setProperty(gradeString[0]);
        grade.setNum(gradeString[1]);
        grade.setName(gradeString[2]);
        grade.setType(gradeString[3]);
        grade.setTime(gradeString[4]);
        grade.setCredit(gradeString[5]);
        grade.setGradeType(gradeString[6]);
        grade.setEndOfTermGrade(gradeString[7]);
        handleTotalGrade(gradeString, grade);//单独处理总成绩
        calculateNormalGrade(grade);//计算平时成绩
        grades.add(grade);
    }

    private static void handleTotalGrade(String[] gradeString, Grade grade) {
        if (gradeString.length >= 9) {
            grade.setTotalGrade(gradeString[8]);
        } else {
            grade.setTotalGrade(gradeString[7]);//有时老师直接给了总成绩，没给期末成绩
        }
    }

    private static void calculateNormalGrade(Grade grade) {
        if (grade.getType().equals("考查")) {
            //如果是考查课，设置平时成绩和总成绩一致
            grade.setNormalGrade(grade.getTotalGrade());
        } else {
            //否则进行计算
            float totalGrade = Float.valueOf(GradeFilter.filter2Grade(grade.getTotalGrade()));
            float endOfTermGrade = Float.valueOf(GradeFilter.filter2Grade(grade.getEndOfTermGrade()));
            float normalGrade = (float) (totalGrade - endOfTermGrade * 0.7);
            BigDecimal bigDecimal = new BigDecimal(normalGrade);
            normalGrade = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).floatValue();//截短舍入
            if (normalGrade > 30) {
                normalGrade = 30;//最大30分平时成绩
            }
            grade.setNormalGrade(String.valueOf(normalGrade));
        }
    }

    /**
     * -----------------------------------------获取成绩单数据--------------------------------------------
     */

    public static void getGradeLists(final OnQueryGradeListListener listener) {
        listener.onStartQuery();
        HttpRequestUtil.stringRequestByGet(Constant.GRADE_LIST_URL, new OnStringRequestListener() {
            @Override
            public void onResponse(String s) {
                if (s.contains(Constant.NOT_HAVE_AUTHORITY)) {
                    listener.onNotHaveAuthority();
                } else if (!s.contains(Constant.RECORD_IS_ZERO)) {//记录数不为0
                    if(isCanNext(AnalyzeHtmlUtil.formatHtmlString2Document(s))) {
                        GradeListResult result = getGradeListResult(s);
                        listener.onSuccess(result);
                    }else {
                        listener.onRecordIs0();
                    }
                } else {
                    listener.onRecordIs0();
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return DataUtil.getGradeCookieMap();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                return Collections.emptyMap();
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
    private static GradeListResult getGradeListResult(String s) {
        Document document = AnalyzeHtmlUtil.formatHtmlString2Document(s);
        List<GradeList> gradeLists = getGradeLists(document);
        GradeListTitle title = getGradeListTitle(document, gradeLists);
        return new GradeListResult(gradeLists, title);
    }

    @NonNull
    private static GradeListTitle getGradeListTitle(Document document, List<GradeList> gradeLists) {
        GradeListTitle title = getGradeListTitle(document);
        float totalGPA = getTotalGPA(gradeLists);
        title.setTotalGPA(String.valueOf(totalGPA));
        BigDecimal decimal = new BigDecimal(Float.valueOf(String.valueOf(totalGPA)) / gradeLists.size());
        title.setAverageGPA(String.valueOf(decimal.setScale(2, BigDecimal.ROUND_DOWN).floatValue()));
        return title;
    }

    @NonNull
    private static GradeListTitle getGradeListTitle(Document document) {
        Elements elements3 = document.getElementsByAttributeValue("style", "height:30px");
        Elements elements4 = elements3.get(1).getElementsByTag("tbody");
        Elements elements5 = elements4.get(0).getElementsByTag("td");
        GradeListTitle title = new GradeListTitle();

        title.setAccount(elements5.get(0).text().split("：")[1]);
        title.setName(elements5.get(1).text().split("：")[1]);
        title.setEnterTime(elements5.get(2).text().split("：")[1]);
        title.setDevelopLevel(elements5.get(3).text().split("：")[1]);
        Elements elements6 = elements3.get(2).getElementsByTag("tbody");
        Elements elements7 = elements6.get(0).getElementsByTag("td");
        title.setDepart(elements7.get(0).text().split("：")[1]);
        if(elements7.get(1).text().split("：").length!=1&&elements7.get(2).text().split("：").length!=1) {
            title.setMajor(elements7.get(1).text().split("：")[1]);
            title.setClassB(elements7.get(2).text().split("：")[1]);
        }



        return title;
    }

    private static boolean isCanNext(Document document) {
        Elements elements3 = document.getElementsByAttributeValue("style", "height:30px");
        Elements elements4 = elements3.get(1).getElementsByTag("tbody");
        Elements elements5 = elements4.get(0).getElementsByTag("td");
        if (elements5.get(0).text().split("：").length > 1) {//以防Cookie过期，出现闪退
            return true;
        } else {
            return false;
        }
    }

    @NonNull
    private static List<GradeList> getGradeLists(Document document) {
        Elements elements = document.getElementsByAttributeValue("style", "height:23px");
        return getGradeLists(elements);
    }

    @NonNull
    private static List<GradeList> getGradeLists(Elements elements) {
        List<GradeList> gradeLists = new ArrayList<>();
        for (Element elements1 : elements) {
            if (!Constant.LONG_BLANK.equals(elements1.text())) {
                Elements elements2 = elements1.getElementsByTag("td");
                GradeList g = new GradeList();
                g.setTime(elements2.get(0).text());
                g.setCourseNum(elements2.get(1).text());
                g.setCourseName(elements2.get(2).text());
                g.setCourseModel(elements2.get(3).text());
                g.setCourseTime(elements2.get(4).text());
                g.setCredit(elements2.get(5).text());
                g.setGrade(elements2.get(6).text());
                g.setGPA(elements2.get(7).text());
                gradeLists.add(g);
            }
        }
        return gradeLists;
    }

    private static float getTotalGPA(List<GradeList> gradeLists) {
        float sum = 0;
        for (GradeList gradeList : gradeLists) {
            sum += Float.parseFloat(gradeList.getGPA().replace(" ", ""));
        }
        BigDecimal decimal = new BigDecimal(sum);
        return decimal.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
    }
}
