package com.android.chengshijian.searchplus.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.chengshijian.searchplus.model.Constant;

/**
 *
 * 操作SharedPreferences的工具类
 *
 * Created by ChengShiJian on 2018/1/8.
 */

public class SharedPreferencesHelper {
    private static SharedPreferences mPreferences;

    public static void init(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static void putStringValue(String key, String value) {
        mPreferences
                .edit()
                .putString(key, value)
                .commit();
    }

    public static void putBooleanValue(String key, Boolean value) {
        mPreferences
                .edit()
                .putBoolean(key, value)
                .commit();
    }

    public static String getStringValue(String key) {
        return mPreferences.getString(key, Constant.NONE);
    }

    //获取Boolean值，默认返回true
    public static boolean getBooleanValue(String key) {
        return mPreferences.getBoolean(key, true);
    }

    public static void clear() {
        putStringValue(Constant.SCHOOL_ACCOUNT, Constant.NONE);
        putStringValue(Constant.SCHOOL_PASSWORD, Constant.NONE);
    }
}
