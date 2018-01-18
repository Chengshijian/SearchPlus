package com.android.chengshijian.searchplus.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 *
 * 分享工具类
 *
 * Created by 程世健 on 2017/7/19.
 */

public class ShareUtils {

    public static void shareText(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        context.startActivity(
                Intent.createChooser(intent, "分享到"));
    }

    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }
}
