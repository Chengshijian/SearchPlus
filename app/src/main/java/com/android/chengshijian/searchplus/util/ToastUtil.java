package com.android.chengshijian.searchplus.util;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * Toast操作工具类
 *
 * Created by chengshijian on 2017/1/30.
 */

public class ToastUtil {
    private static Toast sToast;
    private static Context sContext;

    public static void init(Context context){
        sContext=context;
    }

    public static void showToast(Context context, int resId, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, resId, duration);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    public static void showToast(Context context, CharSequence text, int duration) {

        if (sToast == null) {
            sToast = Toast.makeText(context, text, duration);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    public static void showShortToast(int resId) {
        if (sToast == null) {
            sToast = Toast.makeText(sContext, resId, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    public static void showShortToast(CharSequence text) {
        if (sToast == null) {
            sToast = Toast.makeText(sContext, text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    public static void showLongToast(int resId) {

        if (sToast == null) {
            sToast = Toast.makeText(sContext, resId, Toast.LENGTH_LONG);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    public static void showLongToast(CharSequence text) {

        if (sToast == null) {
            sToast = Toast.makeText(sContext, text, Toast.LENGTH_LONG);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }
}
