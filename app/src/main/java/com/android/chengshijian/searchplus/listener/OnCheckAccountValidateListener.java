package com.android.chengshijian.searchplus.listener;

/**
 *
 * 验证学号是否有效接口
 *
 * Created by ChengShiJian on 2018/1/10.
 */

public interface OnCheckAccountValidateListener extends OnCommonLoginListener {

    /**
     *
     * 当开始检查时
     *
     */
    void onStartCheck();

    /**
     *
     * 当得到Cookie的时（在获取验证码阶段会得到Cookie）
     *
     * @param cookie
     */
    void onGetCookie(String cookie);

    /**
     * 当检查成功时（即学号是有效的）
     *
     * @param type 类型（是给哪一个功能检查的，类型常量在LoginUtil中）
     *
     */
    void onCheckSuccess(int type);
}
