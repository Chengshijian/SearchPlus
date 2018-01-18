package com.android.chengshijian.searchplus.listener;

import com.android.volley.VolleyError;

/**
 *
 * 请求失败监听器接口
 *
 * 需要此接口中的方法的接口会继承他
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public interface OnErrorResponseListener {

    /**
     *
     * 当请求失败时
     *
     * @param volleyError
     */
    void onErrorResponse(VolleyError volleyError);
}
