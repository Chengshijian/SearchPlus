package com.android.chengshijian.searchplus.listener;

import com.android.chengshijian.searchplus.model.Lecture;

import java.util.List;

/**
 *
 * 查询讲座事件监听器接口
 *
 * Created by ChengShiJian on 2018/1/13.
 */

public interface OnQueryLectureListener extends OnSuccessListener<List<Lecture>>,OnQueryListener{

    /**
     *
     * 当失败时
     *
     */
    void onFailed();

    /**
     *
     * 当讲座为0时
     *
     */
    void onLectureSizeIsZero();
}
