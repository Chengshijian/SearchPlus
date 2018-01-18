package com.android.chengshijian.searchplus.util;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 *
 * Snackbar工具类
 *
 * 由ToastUtil改编
 *
 * Created by ChengShiJian on 2018/1/16.
 */
public class SnackbarUtil {

    private static Snackbar sSnackbar;

    public static void showSnackbar(View view, int resId, int duration) {
        if (sSnackbar == null) {
            sSnackbar = Snackbar.make(view, resId, duration);
        } else {
            sSnackbar.setText(resId);
        }
        sSnackbar.show();
    }

    public static void showSnackbar(View view, CharSequence text, int duration) {

        if (sSnackbar == null) {
            sSnackbar = Snackbar.make(view, text, duration);
        } else {
            sSnackbar.setText(text);
        }
        sSnackbar.show();
    }

    public static void showShortSnackbar(View view,int resId) {
        if (sSnackbar == null) {
            sSnackbar = Snackbar.make(view, resId, Snackbar.LENGTH_SHORT);
        } else {
            sSnackbar.setText(resId);
        }
        sSnackbar.show();
    }

    public static void showShortSnackbar(View view,CharSequence text) {
        if (sSnackbar == null) {
            sSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        } else {
            sSnackbar.setText(text);
        }
        sSnackbar.show();
    }

    public static void showLongSnackbar(View view,int resId) {

        if (sSnackbar == null) {
            sSnackbar = Snackbar.make(view, resId, Snackbar.LENGTH_LONG);
        } else {
            sSnackbar.setText(resId);
        }
        sSnackbar.show();
    }

    public static void showLongSnackbar(View view,CharSequence text) {

        if (sSnackbar == null) {
            sSnackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        } else {
            sSnackbar.setText(text);
        }
        sSnackbar.show();
    }
}
