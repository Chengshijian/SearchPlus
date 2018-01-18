package com.android.chengshijian.searchplus.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.android.chengshijian.searchplus.R;

/**
 *
 * Fragment的基类
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {

    /**
     * 抽象方法
     *
     * 其子类必须返回一个Fragment对象
     *
     * @return
     */
    public abstract Fragment createFragment();

    /**
     * 获取布局id
     *
     * 默认返回R.layout.fragment_activity
     *
     * 如需要可重写，返回其他的
     *
     * @return 布局id
     */
    protected int getLayoutResId() {
        return R.layout.fragment_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
