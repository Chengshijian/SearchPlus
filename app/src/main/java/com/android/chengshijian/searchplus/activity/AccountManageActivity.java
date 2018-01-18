package com.android.chengshijian.searchplus.activity;

import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.fragment.AppAccountManageFragment;
import com.android.chengshijian.searchplus.fragment.SchoolAccountManageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 账户管理类
 * <p>
 * Created by ChengShiJian on 2018/1/17.
 */

public class AccountManageActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabLayoutFragmentPagerAdapter mFragmentPagerAdapter;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @Override
    public void initView() {
        initCustomActionBar();
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new SchoolAccountManageFragment());
        mFragments.add(new AppAccountManageFragment());

        mTitles = new ArrayList<>();
        mTitles.add("学号管理");
        mTitles.add("App账号管理");

        for (int i = 0; i < mTitles.size(); i++) {
            mTabLayout
                    .addTab(mTabLayout
                            .newTab()
                            .setText(mTitles.get(i)));
        }

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mFragmentPagerAdapter = new TabLayoutFragmentPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_manage;
    }

    private void initCustomActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        LayoutInflater mInflater = LayoutInflater.from(this);
        View actionBar = mInflater.inflate(R.layout.custom_account_manage_action_bar, null);
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);//必须加这段代码，否则空指针异常
        ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0, 0);
    }

    public class TabLayoutFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public TabLayoutFragmentPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
            super(fm);
            mTitles = titles;
            mFragments = fragments;
        }


        @Override
        public Parcelable saveState() {
            super.saveState();
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            super.getPageTitle(position);
            return mTitles.get(position % mTitles.size());
        }
    }
}
