package com.android.chengshijian.searchplus.presenter;

import android.content.Context;

import com.android.chengshijian.searchplus.biz.BmobUserBiz;
import com.android.chengshijian.searchplus.biz.BmobUserImpl;
import com.android.chengshijian.searchplus.listener.OnLoginListener;
import com.android.chengshijian.searchplus.util.ViewUtil;
import com.android.chengshijian.searchplus.view.LoginView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by ChengShiJian on 2017/10/9.
 *
 * LoginPresenter接口和OnLoginListener接口的实现类
 *
 */

public class LoginPresenterImpl implements LoginPresenter,OnLoginListener {

    /**
     * 登录视图接口
     */
    private LoginView mLoginView;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * BmobUserBiz 对象
     */
    private BmobUserBiz mUserBiz;

    /**
     * @param context 上下文
     * @param loginView 登录视图接口
     */
    public LoginPresenterImpl(Context context, LoginView loginView) {
        mLoginView = loginView;
        mContext=context;
        mUserBiz=new BmobUserImpl(this,null);
    }

    @Override
    public void login() {
        BmobUser user=new BmobUser();
        user.setUsername(mLoginView.getEmail());
        user.setEmail(mLoginView.getEmail());
        user.setPassword(mLoginView.getPassword());
        mUserBiz.login(user);
    }

    @Override
    public void beginRegister() {
        mUserBiz.beginRegister(mContext);
    }

    @Override
    public void save() {
        mUserBiz.saveData(mLoginView.getEmail(),mLoginView.getPassword());
    }

    @Override
    public void onLogin(BmobUser user, BmobException e) {
        mLoginView.showLoginMsg(user,e);
    }

    @Override
    public void autoCompleteEmail() {
        ViewUtil.autoCompleteEmail(mLoginView.getEmailAutoCompleteTextView());
    }
}
