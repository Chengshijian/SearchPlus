package com.android.chengshijian.searchplus.listener;


import com.android.chengshijian.searchplus.model.Assess;

import java.util.List;

/**
 * 一键评教事件监听器接口
 *
 * Created by chengshijian on 2017/7/24.
 *
 * Modify by chengshijian on 2018/1/17.
 */

public interface OnAssessListener {

    /**
     *
     * 当登录超时时
     *
     */
    void onLoginDataOutOfTime();

    /**
     * 当教师数据获取失败时
     *
     * @param infos 评教对象集合
     *
     */
    void onAssessTeacherDataReceived(final List<Assess> infos);

    /**
     * 当获取评教的老师数据失败时
     *
     * @param volleyErrorClass
     */
    void onAssessTeacherDataError(Class volleyErrorClass);

    /**
     *
     * 当评教全部完成时
     *
     */
    void onAssessSuccess();

    /**
     * 当获取基本的数据失败时
     *
     * @param volleyErrorClass
     */
    void onAssessBaseDataError(Class volleyErrorClass);

    /**
     * 当开始为教师评教时
     *
     * @param assess
     */
    void onStartAssessForTeacher(final Assess assess);

    /**
     * 当给一个教师评教完成时
     *
     * @param resultHtml html
     * @param assess 评教对象
     */
    void onAssessForTeacherFinished(String resultHtml, final Assess assess);
}
