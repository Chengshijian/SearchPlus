package com.android.chengshijian.searchplus.util;

import com.android.chengshijian.searchplus.R;

import java.util.Calendar;

/**
 * 时间工具类
 * <p>
 * Created by ChengShiJian on 2018/1/13.
 */

public class TimeUtil {

    /**
     *
     * 凌晨:3:00--6:00
     * 早晨:6:00---8:00
     * 上午:8:00--11:00
     * 中午:11:00--13:00
     * 下午:13:00--17:00
     * 傍晚:17:00--19:00
     * 晚上:19:00--23:00
     * 深夜:23:00--3:00
     *
     */

    //是否凌晨
    public static boolean isDaybreak() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 3
                && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 5;
    }

    //是否早晨
    public static boolean isEarlyMorning() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 6
                && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 7;
    }

    //是否早上
    public static boolean isMorning() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 8
                && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 10;
    }

    //是否中午
    public static boolean isMidDay() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 11
                && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 12;
    }

    //是否下午
    public static boolean isAfternoon() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 13
                && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 16;
    }

    //是否傍晚
    public static boolean isDusk() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 17
                && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 18;
    }

    //是否晚上
    public static boolean isEvening() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 19
                && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 22;
    }

    //是否深夜
    public static boolean isDeepNight() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 23
                || Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 0
                || Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 1
                || Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 2;
    }

    public static int getTimeMillisBySecond(int second) {
        return second * 60 * 1000;
    }

    //通过小时获取毫秒数
    public static int getTimeMillisByHour(int hour) {
        return hour * 60 * 60 * 1000;
    }

    //判断是否和指定id的字符串相等
    public static boolean isDurationEquals(int id) {
        return DataUtil.getSelectDuration().equals(StringUtil.getString(id));
    }

    //获取自动登录的时间间隔
    public static int getAutoLoginDuration() {
        if (isDurationEquals(R.string.per)) {
            return getTimeMillisBySecond(0);
        } else if (isDurationEquals(R.string.ten_minute)) {
            return getTimeMillisBySecond(10);
        } else if (isDurationEquals(R.string.twenty_minute)) {
            return getTimeMillisBySecond(20);
        } else if (isDurationEquals(R.string.thirty_minute)) {
            return getTimeMillisBySecond(30);
        } else if (isDurationEquals(R.string.one_hour)) {
            return getTimeMillisByHour(1);
        } else if (isDurationEquals(R.string.two_hour)) {
            return getTimeMillisByHour(2);
        } else if (isDurationEquals(R.string.three_hour)) {
            return getTimeMillisByHour(3);
        } else {
            return getTimeMillisBySecond(30);//默认返回30分钟时间
        }
    }
}
