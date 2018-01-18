package com.android.chengshijian.searchplus.listener;

import com.android.volley.VolleyError;

/**
 *
 * 通用登录监听器接口
 *
 * 需要此接口中的方法的接口会继承他
 *
 * Created by ChengShiJian on 2018/1/10.
 */

public interface OnCommonLoginListener {

    /**
     *
     * 当失败时
     *
     * @param type
     * @param volleyError
     */
    void onErrorResponse(int type, VolleyError volleyError);

    /**
     *
     * 当链接关闭时
     *
     */
    void onAddressClosed();

    /**
     *
     * 当登录失败时
     *
     */
    void onLoginFailed();
}
