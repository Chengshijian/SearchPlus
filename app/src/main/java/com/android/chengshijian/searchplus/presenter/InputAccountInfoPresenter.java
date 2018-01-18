package com.android.chengshijian.searchplus.presenter;

import com.android.chengshijian.searchplus.listener.OnLoadIdentifyCodeListener;
import com.android.chengshijian.searchplus.listener.OnLoginSchoolListener;
import com.android.chengshijian.searchplus.model.User;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.LoginUtil;
import com.android.chengshijian.searchplus.util.SharedPreferencesHelper;

/**
 *
 * 输入学号的执行者类
 *
 * Created by ChengShiJian on 2018/1/8.
 */

public class InputAccountInfoPresenter {

    private IInputAccountInfo mIInputAccountInfo;
    private OnLoginSchoolListener mOnLoginSchoolListener;
    private OnLoadIdentifyCodeListener mIdentifyCodeListener;

    public InputAccountInfoPresenter(IInputAccountInfo IInputAccountInfo, OnLoginSchoolListener loginListener, OnLoadIdentifyCodeListener loadIdentifyCodeListener) {
        mIInputAccountInfo = IInputAccountInfo;
        mOnLoginSchoolListener = loginListener;
        mIdentifyCodeListener = loadIdentifyCodeListener;
    }

    public void saveInfo() {
        String account = mIInputAccountInfo.getAccount();
        String password = mIInputAccountInfo.getPassword();

        SharedPreferencesHelper.putStringValue(Constant.SCHOOL_ACCOUNT, account);
        SharedPreferencesHelper.putStringValue(Constant.SCHOOL_PASSWORD, password);

        DataUtil.addAddedAccountToHistory(account);

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);

        user.save();
    }


    public void loadIdentifyCode() {
        LoginUtil.loadIdentifyingCode(Constant.IDENTIFYING_CODE_REQUEST_URL, mIdentifyCodeListener);
    }

    public void check() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginUtil.tryLogin(Constant.LOGIN_URL, new User(mIInputAccountInfo.getAccount(), mIInputAccountInfo.getPassword()), mOnLoginSchoolListener);
            }
        }).start();

    }
}
