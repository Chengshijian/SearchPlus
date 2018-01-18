package com.android.chengshijian.searchplus.listener;

/**
 *
 * 登录学校教务处事件监听器
 *
 * Created by ChengShiJian on 2018/1/8.
 */

public interface OnLoginSchoolListener extends OnCommonLoginListener {

    /**
     *
     * 当开始登录时
     *
     */
    void onStartLogin();

    /**
     *
     * 当学号小于10位时
     *
     */
    void onAccountLowerThan10();

    /**
     *
     * 当登录成功时
     *
     */
    void onLoginSuccess();
}
