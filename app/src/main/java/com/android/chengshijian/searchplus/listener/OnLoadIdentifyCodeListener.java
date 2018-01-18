package com.android.chengshijian.searchplus.listener;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;

/**
 *
 * 获取验证码的事件监听器接口
 *
 * Created by ChengShiJian on 2018/1/8.
 */

public interface OnLoadIdentifyCodeListener {

    /**
     * 当获取验证码成功时
     *
     * @param code 验证码
     * @param bitmap 验证码的图片形式
     *
     */
    void onLoadIdentifyCodeSuccess(String code, Bitmap bitmap);

    /**
     * 当失败时
     *
     * @param type 哪一个功能失败的
     * @param volleyError
     */
    void onErrorResponse(int type, VolleyError volleyError);

    /**
     *
     * 当获得Cookie时
     *
     * @param cookie
     */
    void onGetCookie(String cookie);
}
