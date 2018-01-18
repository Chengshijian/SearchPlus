package com.android.chengshijian.searchplus.listener;

/**
 *
 * 查询成绩事件监听器接口
 *
 * Created by ChengShiJian on 2018/1/9.
 */
public interface OnQueryGradeListener extends OnQueryListener,OnSuccessListener{

    /**
     *
     * 当记录数为0
     *
     */
    void onRecordIs0();
}
