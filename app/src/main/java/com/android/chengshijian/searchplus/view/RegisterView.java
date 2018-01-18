package com.android.chengshijian.searchplus.view;

import com.android.chengshijian.searchplus.view.common.AutoCompleteTextViewInterface;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by ChengShiJian on 2017/10/9.
 */

public interface RegisterView extends AutoCompleteTextViewInterface {
    String getEmail();
    String getPasswordBefore();
    String getPasswordAfter();
    void showRegisterMsg(String msgType, BmobUser user, BmobException e);
}