package com.android.chengshijian.searchplus.activity;
import android.support.v4.app.Fragment;

import com.android.chengshijian.searchplus.fragment.LoginFragment;


/**
 * Created by ChengShiJian on 2017/10/9.
 */

public class LoginActivity extends BaseFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }
}
