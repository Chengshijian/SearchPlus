package com.android.chengshijian.searchplus.util;

import com.android.chengshijian.searchplus.app.BaseApplication;

/**
 *
 * 字符串操作工具类
 *
 * Created by ChengShiJian on 2018/1/15.
 */

public class StringUtil {

    //获取字符串
    public static String getString(int id) {
        return BaseApplication.getContextApplication().getString(id);
    }
}
