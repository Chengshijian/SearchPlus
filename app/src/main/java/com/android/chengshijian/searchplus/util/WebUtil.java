package com.android.chengshijian.searchplus.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by ChengShiJian on 2018/1/18.
 */

public class WebUtil {
    /**
     * 跳转到外部浏览器打开 url
     *
     * @param context
     * @param url
     */
    public static void openExternal(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
