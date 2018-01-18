package com.android.chengshijian.searchplus.listener;

/**
 *
 * 查询成绩单事件监听器接口
 *
 * Created by ChengShiJian on 2018/1/12.
 */
public interface OnQueryGradeListListener extends OnQueryGradeListener{

    /**
     *
     * 当没有权限时
     *
     */
    void onNotHaveAuthority();
}
