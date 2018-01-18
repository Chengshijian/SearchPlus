package com.android.chengshijian.searchplus.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 *
 * App账号类
 *
 * 实体类
 *
 * Created by ChengShiJian on 2018/1/17.
 */

public class App extends DataSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String mAccount;
    private String mPassword;

    public App() {
    }

    public App(String account, String password) {
        mAccount = account;
        mPassword = password;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
