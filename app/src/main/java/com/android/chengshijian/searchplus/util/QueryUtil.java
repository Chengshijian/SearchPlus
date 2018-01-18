package com.android.chengshijian.searchplus.util;

import com.android.chengshijian.searchplus.listener.OnPrepareQueryListener;
import com.android.chengshijian.searchplus.model.Constant;

import java.util.Calendar;

/**
 *
 * 查询工具类
 *
 * Created by ChengShiJian on 2018/1/12.
 *
 */

public class QueryUtil {

    //准备查询
    public static void prepareQuery(int type,OnPrepareQueryListener listener) {
        if (isHaveAccount()) {
            long nowTime = Calendar.getInstance().getTimeInMillis();
            long cookieSavedTime = Long.valueOf(SharedPreferencesHelper.getStringValue(Constant.COOKIE_GET_TIME));
            if (nowTime - cookieSavedTime < (TimeUtil.getAutoLoginDuration())) {//设置时间
                listener.onCanQuery(type);
            } else {
                listener.onNeedInvalidate(type);
            }
        } else {
            listener.onNotHaveAccount();
        }
    }

    //当前是否有用户
    public static boolean isHaveAccount() {
        String s = SharedPreferencesHelper.getStringValue(Constant.SCHOOL_ACCOUNT);
        return !Constant.NONE.equals(s);
    }
}
