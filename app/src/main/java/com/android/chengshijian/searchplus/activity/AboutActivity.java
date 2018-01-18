package com.android.chengshijian.searchplus.activity;

import com.android.chengshijian.searchplus.R;

/**
 * Created by ChengShiJian on 2018/1/17.
 */

public class AboutActivity extends BaseActivity {
    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        setDisplayHomeAsUpEnabled(true);
        setActionBarTitle("关于" + getString(R.string.app_name));
        transparentNavigationBar();
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_about;
    }
}
