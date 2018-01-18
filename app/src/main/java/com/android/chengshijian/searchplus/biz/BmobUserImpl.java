package com.android.chengshijian.searchplus.biz;

import android.content.Context;
import android.content.Intent;


import com.android.chengshijian.searchplus.activity.RegisterActivity;
import com.android.chengshijian.searchplus.listener.OnLoginListener;
import com.android.chengshijian.searchplus.listener.OnRegisterListener;
import com.android.chengshijian.searchplus.model.App;
import com.android.chengshijian.searchplus.util.DataUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ChengShiJian on 2017/10/9.
 *
 * MVP设计模式
 *
 * @linl BmobUserBiz 接口的实现类，此类用于实现
 * @link BmobUserBiz 要求实现的逻辑
 */

public class BmobUserImpl implements BmobUserBiz{

    /**
     * 登录时间监听器接口
     */
    private OnLoginListener mLoginListener;

    /**
     * 注册时间监听器接口
     */
    private OnRegisterListener mRegisterListener;

    /**
     *
     * @param loginListener 登录时间监听器接口
     * @param registerListener 注册时间监听器接口
     */
    public BmobUserImpl(OnLoginListener loginListener, OnRegisterListener registerListener) {
        mLoginListener = loginListener;
        mRegisterListener = registerListener;
    }

    /**
     * 登录
     * @param user BmobUser 对象
     */
    @Override
    public void login(BmobUser user) {
        
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                mLoginListener.onLogin(user,e);
            }
        });

    }

    /**
     * 开始注册
     * @param context 前后文
     */
    @Override
    public void beginRegister(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    /**
     * 注册
     * @param user BmobUser 对象
     */
    @Override
    public void register(BmobUser user) {
        user.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                mRegisterListener.onRegister(user,e);
            }
        });
    }

    @Override
    public void saveData(String email, String password) {
        DataUtil.saveAppAccountToSharedPreferences(email,password);
        new App(email,password).save();
    }
}
