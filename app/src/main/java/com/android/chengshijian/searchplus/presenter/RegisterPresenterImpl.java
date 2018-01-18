package com.android.chengshijian.searchplus.presenter;

import com.android.chengshijian.searchplus.biz.BmobUserImpl;
import com.android.chengshijian.searchplus.listener.OnRegisterListener;
import com.android.chengshijian.searchplus.util.ViewUtil;
import com.android.chengshijian.searchplus.view.RegisterView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 *
 * 注册执行者的实现类
 *
 * Created by ChengShiJian on 2017/10/9.
 */

public class RegisterPresenterImpl implements RegisterPresenter,OnRegisterListener {

    public static final String TWO_PASSWORD_NOT_SAME="TWO_PASSWORD_NOT_SAME";
    public static final String REGIDTER="REGISTER";

    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView registerView) {
        mRegisterView = registerView;
    }

    @Override
    public void register() {

        if(mRegisterView.getPasswordBefore().equals(mRegisterView.getPasswordAfter())) {
            BmobUser user = new BmobUser();
            user.setUsername(mRegisterView.getEmail());
            user.setEmail(mRegisterView.getEmail());
            user.setPassword(mRegisterView.getPasswordBefore());
            new BmobUserImpl(null, this).register(user);
        }else {
            mRegisterView.showRegisterMsg(TWO_PASSWORD_NOT_SAME,null,null);
        }
    }

    @Override
    public void onRegister(BmobUser user, BmobException e) {
        mRegisterView.showRegisterMsg(REGIDTER,user,e);
    }

    @Override
    public void autoCompleteEmail() {
        ViewUtil.autoCompleteEmail(mRegisterView.getEmailAutoCompleteTextView());
    }
}
