package com.android.chengshijian.searchplus.activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.fragment.SettingsFragment;
import com.android.chengshijian.searchplus.view.IActivityView;

/**
 *
 * 设置类
 *
 * 结合PreferenceFragment类实现
 *
 * Created by ChengShiJian on 2018/1/14.
 */

public class SettingsActivity extends AppCompatActivity implements IActivityView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        loadFragment();
        initActionBar();
        transparentNavigationBar();
    }

    private void initActionBar() {
        getSupportActionBar().setTitle("设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadFragment() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.settings_container);
        if (fragment == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.settings_container, SettingsFragment.newInstance())
                    .commit();
        }
    }


    public void transparentNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }


    @Override
    public void initListener() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
