package com.android.chengshijian.searchplus.view;

import com.android.chengshijian.searchplus.view.common.AutoCompleteTextViewInterface;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by ChengShiJian on 2017/10/9.
 */

public interface LoginView extends AutoCompleteTextViewInterface {
    String getEmail();
    String getPassword();
    void showLoginMsg(BmobUser user, BmobException e);
}
