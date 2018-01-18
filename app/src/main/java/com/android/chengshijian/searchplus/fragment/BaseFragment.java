package com.android.chengshijian.searchplus.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.view.IFragmentView;

/**
 * 其他Fragment的基类
 * <p>
 * Created by ChengShiJian on 2018/1/9.
 */

public abstract class BaseFragment extends Fragment implements IFragmentView {
    private Toolbar mToolbar;
    private View mView;

    /**
     * 获取布局Id
     *
     * @return id
     */
    public abstract int getLayoutResId();

    /**
     * 是否有toolbar
     *
     * @return 有返回true, 无返回false
     */
    public abstract boolean isHaveToolbar();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutResId(), container, false);
        initToolBar();
        initView();
        initData();
        initListener();
        return mView;
    }

    public View findViewById(int id) {
        return mView.findViewById(id);
    }

    public AppCompatActivity getAppCompatActivity() {
        return (AppCompatActivity) getActivity();
    }

    public void setToolbarTitle(String title) {
        getAppCompatActivity().getSupportActionBar().setTitle(title);
    }

    public void setToolbarTitle(int id) {
        getAppCompatActivity().getSupportActionBar().setTitle(getResources().getString(id));
    }

    public void setDisplayHomeAsUpEnabled(boolean enabled) {
        ActionBar actionBar = getAppCompatActivity().getSupportActionBar();
        if (actionBar != null) {
            if (enabled) {
                setHasOptionsMenu(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setBackArrowEnabled(boolean isEnabled) {
        if (isEnabled) {
            setHasOptionsMenu(true);
            setDisplayHomeAsUpEnabled(true);
        } else {
            setHasOptionsMenu(false);
        }
    }

    private void setToolBar(Toolbar toolBar) {
        if (toolBar != null) {
            getAppCompatActivity().setSupportActionBar(toolBar);
        }
    }

    private void initToolBar() {
        if (isHaveToolbar()) {
            mToolbar = (Toolbar) findViewById(getToolBarResId());
            setToolBar(mToolbar);
        }
    }

    //如果toolbar的名字有变动，重写即可
    protected int getToolBarResId() {
        return R.id.toolbar;
    }

    public void transparentNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    public void startActivity(Class<?> cls) {
        getActivity().startActivity(new Intent(getActivity(), cls));
    }

    public void runOnUiThread(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }
}
