package com.android.chengshijian.searchplus.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import com.android.chengshijian.searchplus.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 多种操作类，他是一个抽象类
 * <p>
 * 历史记录类和收藏类，很多地方都是相似的
 * <p>
 * 所以提取出来了这个类
 * <p>
 * 历史记录类和收藏类都会继承这个类
 * <p>
 * 只要实现它里面的抽象方法并做一些其他操作即可
 * <p>
 * Created by ChengShiJian on 2018/1/15.
 */

public abstract class MultiOperateActivity<T> extends BaseActivity {

    protected SwipeMenuRecyclerView mRecyclerView;
    protected List<T> mList;

    /**
     * 获取RecyclerView的id
     *
     * @return RecyclerView的id
     */
    protected abstract int getRecyclerResId();

    /**
     * 获取LayoutManager
     *
     * @return RecyclerView.LayoutManager对象
     */
    public abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void initView() {
        mRecyclerView = findViewById(getRecyclerResId());
    }

    @Override
    public void initData() {
        setDisplayHomeAsUpEnabled(true);
        transparentNavigationBar();
        mList = new ArrayList<>();
        mRecyclerView.setOnItemStateChangedListener(new OnItemStateChangedListener() {
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {

                    // 拖拽的时候背景就透明了，这里我们可以添加一个特殊背景。
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(MultiOperateActivity.this, R.color.colorPrimaryDark));
                } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {

                } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {

                    // 在手松开的时候还原背景。
                    ViewCompat.setBackground(viewHolder.itemView, ContextCompat.getDrawable(MultiOperateActivity.this, R.color.back_color));
                }
            }
        });
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(getResources().getColor(R.color.back_color), 0, 14));
    }

    @Override
    public void initListener() {

    }
}
