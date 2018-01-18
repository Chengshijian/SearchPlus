package com.android.chengshijian.searchplus.listener;

/**
 *
 * 查询自主学分事件监听器接口
 *
 * Created by ChengShiJian on 2018/1/12.
 */

public interface OnQueryIndCreditListener extends OnQueryGradeListener {

    /**
     *
     * 当没有查询到自主学分时
     *
     */
    void onNotHaveCredit();
}
