package com.android.chengshijian.searchplus.activity;

import android.support.v4.app.Fragment;

import com.android.chengshijian.searchplus.fragment.QueryGradeFragment;

/**
 * 查询成绩类
 *
 * 此类结合Fragment实现
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public class QueryGradeActivity extends BaseFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new QueryGradeFragment();
    }
}
