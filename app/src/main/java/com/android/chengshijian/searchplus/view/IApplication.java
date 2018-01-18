package com.android.chengshijian.searchplus.view;

/**
 * Created by ChengShiJian on 2018/1/8.
 */

public interface IApplication {
    void initToastUtil();
    void initLogger();

    void initSharedPreferences();

    void initHttpRequestUtil();

    void initLitePal();

    void initUserSettings();

    void initBmob();
}
