package com.android.chengshijian.searchplus.model;

import cn.bmob.v3.BmobObject;

/**
 *
 * 存储数据到云端的历史记录类
 *
 * 由于java语言不支持多继承，
 *
 * 所以另外建了一个新类有一个Favorite对象和账号（用以区分是哪个用户的）
 *
 * Created by ChengShiJian on 2018/1/16.
 */

public class CloudFavorite extends BmobObject {

    private String mAccount;

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    private Favorite mFavorite;

    public Favorite getFavorite() {
        return mFavorite;
    }

    public void setFavorite(Favorite favorite) {
        mFavorite = favorite;
    }
}
