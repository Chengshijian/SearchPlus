package com.android.chengshijian.searchplus.listener;

/**
 *
 * 选择事件监听器接口
 *
 * 也可以不设计接口，而是采用返回值的形式
 *
 * 这里设计了接口，用以监听应该怎么设置学期的位置
 *
 * Created by ChengShiJian on 2018/1/13.
 */

public interface OnSelectListener {

    /**
     *
     * 当是第一个学期时
     *
     */
    void onFirstTerm();

    /**
     *
     * 当是第二个学期时
     *
     */
    void onNextTerm();
}

