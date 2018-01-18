package com.android.chengshijian.searchplus.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.chengshijian.searchplus.listener.OnCheckUpdateListener;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.Version;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 程世健 on 2017/7/28.
 */

public class CheckUpdateUtil {

    public static void checkUpdate(final OnCheckUpdateListener listener) {
        OkHttpUtils
                .get()
                .url(Constant.UPDATE_URL)
                .addParams(Constant.API_TOKEN, Constant.APP_ID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onError(call,e,id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new GsonBuilder().create();
                        listener.onSuccess(gson.fromJson(response, Version.class));
                    }
                });
    }
}