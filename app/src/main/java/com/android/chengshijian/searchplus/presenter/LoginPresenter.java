package com.android.chengshijian.searchplus.presenter;

import com.android.chengshijian.searchplus.presenter.common.AutoComplete;

/**
 * Created by ChengShiJian on 2017/10/9.
 *
 * 登录的处理类
 */

public interface LoginPresenter extends AutoComplete {

    /**
     * 登录接口
     * 当点击登录按钮时调用
     */
    void login();

    /**
     * 开始注册接口
     * 当在登录界面点击注册时调用
     */
    void beginRegister();

    void save();

}
