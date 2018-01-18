package com.android.chengshijian.searchplus.biz;

import android.content.Context;

import cn.bmob.v3.BmobUser;

/**
 * Created by ChengShiJian on 2017/10/9.
 */

public interface BmobUserBiz {
    /**
     * @link BmobUser 要实现的登录接口
     * @param user
     */
    void login(BmobUser user);

    /**
     *  @link BmobUser 要实现的开始注册接口，
     *  就是在的登录界面，点击注册时要执行的动作
     * @param context
     */
    void beginRegister(Context context);

    /**
     * @link BmobUser 要实现的注册接口，
     * 就是在注册界面，点击注册时，要执行的逻辑
     * @param user
     */
    void register(BmobUser user);


    void saveData(String email, String password);
}
