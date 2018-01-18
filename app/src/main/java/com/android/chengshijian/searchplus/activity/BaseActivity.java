package com.android.chengshijian.searchplus.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.view.IActivityView;

/**
 * 其它Activity的基类 本项目中 大部分Activity都继承它
 *
 * Created by ChengShiJian on 2018/1/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements IActivityView {

    public abstract int getLayoutResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initView();
        initData();
        initListener();
    }

    /**
     *  使底部导航栏透明化
     *
     *  华为等有底部导航栏的手机能看到效果
     *
     */
    public void transparentNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置显示返回箭头
     *
     * @param isEnabled 是否有效
     */
    public void setDisplayHomeAsUpEnabled(boolean isEnabled) {
        if (isEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     *
     *  在Activity自定义Toolbar时调用
     *
     *  设置是否有Toolbar
     *
     */
    public void setHasToolbar(boolean isHaveToolbar, Integer id) {
        if (isHaveToolbar) {
            if (id == null) {
                id = R.id.toolbar;
            }
            Toolbar toolbar = findViewById(id);
            setSupportActionBar(toolbar);
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题（String类型）
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * 设置标题
     *
     * @param resId 标题（int类型）
     */
    public void setActionBarTitle(int resId) {
        getSupportActionBar().setTitle(resId);
    }

    /**
     * 重写方法 设置点击actionbar上面的菜单的点击事件
     *
     * @param item
     * @return
     */
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

    /**
     * 启动Activity
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
