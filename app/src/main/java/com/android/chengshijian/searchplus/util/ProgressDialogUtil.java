package com.android.chengshijian.searchplus.util;

import android.app.Activity;
import android.app.ProgressDialog;

import com.android.chengshijian.searchplus.R;

/**
 *
 * 进度框工具类
 *
 * Created by ChengShiJian on 2018/1/12.
 */

public class ProgressDialogUtil {

    public static ProgressDialog getRequestDataDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getString(R.string.is_get_data));
        initDialog(dialog);
        return dialog;
    }

    public static ProgressDialog getInitDataDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getString(R.string.is_init));
        initDialog(dialog);
        return dialog;
    }

    private static void initDialog(ProgressDialog dialog) {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
    }
}
