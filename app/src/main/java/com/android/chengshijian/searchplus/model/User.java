package com.android.chengshijian.searchplus.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 *
 * 用户实体类
 *
 * 主要用来存储学号
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public class User extends DataSupport {
    @Column(defaultValue = "unknown")
    private String mName;
    @Column(unique = true, defaultValue = "unknown")
    private String mAccount;
    private String mPassword;

    public User() {
    }

    public User(String account, String password) {
        mAccount = account;
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    @Override
    public String toString() {
        return "User{" +
                "mName='" + mName + '\'' +
                ", mAccount='" + mAccount + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
