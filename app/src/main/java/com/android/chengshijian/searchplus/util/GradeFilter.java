package com.android.chengshijian.searchplus.util;

/**
 *
 * 成绩与等级转换类
 *
 * Created by chengshijian on 2017/2/19.
 */

public final class GradeFilter {
    //将指定成绩解析成百分制成绩
    public static String filter2Grade(String level) {
        switch (level) {
            case "未考评":
                return "-1";
            case "缺考":
                return "1";
            case "其他":
                return "2";
            case "不及格":
                return "59";
            case "及格":
                return "65";
            case "中":
                return "75";
            case "良":
                return "85";
            case "优":
                return "95";
        }
        return level;


    }

    public static String filter2Level(String grade) {
        switch (grade) {
            case "-1":
                return "未考评";
            case "1":
                return "缺考";
            case "2":
                return "其他";
            case "59":
                return "不及格";
            case "60":
                return "及格";
            case "70":
                return "中";
            case "80":
                return "良";
            case "90":
                return "优";
        }
        return grade;
    }
}
