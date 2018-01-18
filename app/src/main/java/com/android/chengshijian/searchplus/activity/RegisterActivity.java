package com.android.chengshijian.searchplus.activity;

import android.support.v4.app.Fragment;

import com.android.chengshijian.searchplus.fragment.RegisterFragment;

/**
 *
 * 注册类
 * Activity+Fragment实现
 *
 * Created by ChengShiJian on 2017/10/9.
 */

public class RegisterActivity extends BaseFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new RegisterFragment();
    }
}
