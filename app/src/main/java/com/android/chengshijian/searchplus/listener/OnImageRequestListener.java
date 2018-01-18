package com.android.chengshijian.searchplus.listener;

import android.graphics.Bitmap;

import com.android.volley.NetworkResponse;

/**
 *
 * 请求图片事件监听器接口
 *
 * （主要要来监听验证码的获取过程）
 *
 * Created by ChengShiJian on 2018/1/9.
 *
 */

public interface OnImageRequestListener extends OnErrorResponseListener {

    /**
     * 当请求成功时
     *
     * @param bitmap Bitmap对象
     */
    void onResponse(Bitmap bitmap);

    /**
     *
     * 当解析NetworkResponse时（用来获取Cookie）
     *
     * @param response
     */
    void parseNetworkResponse(NetworkResponse response);
}
