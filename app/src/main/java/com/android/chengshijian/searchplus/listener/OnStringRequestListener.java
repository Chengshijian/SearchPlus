package com.android.chengshijian.searchplus.listener;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;

import java.util.Map;

/**
 *
 * 请求网页的事件监听器接口
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public interface OnStringRequestListener extends OnErrorResponseListener {

    /**
     *
     * 当回应时，即请求成功时
     *
     * @param s
     */
    void onResponse(String s);

    /**
     *
     * 当需要得到hears时
     *
     * @return
     * @throws AuthFailureError
     */
    Map<String, String> getHeaders() throws AuthFailureError;

    /**
     *
     *当需要得到Params时
     *
     * @return
     * @throws AuthFailureError
     */
    Map<String, String> getParams() throws AuthFailureError;

    /**
     *
     * 当解析网络时（获取Cookie）
     * @param response
     */
    void parseNetworkResponse(NetworkResponse response);
}
