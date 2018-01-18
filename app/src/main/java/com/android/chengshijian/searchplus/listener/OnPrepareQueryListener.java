package com.android.chengshijian.searchplus.listener;

/**
 *
 * 准备查询事件监听器接口
 *
 * 此事件监听器主要用于在启动一个功能模块时，检查是否可以启动
 *
 * Created by ChengShiJian on 2018/1/12.
 */

public interface OnPrepareQueryListener {

    /**
     *
     * 当可以进行查询时
     *
     * @param type
     */
    void onCanQuery(int type);

    /**
     *
     * 当需要进行验证时
     * @param type
     *
     */
    void onNeedInvalidate(int type);

    /**
     *
     * 当系统没有检测到账号时
     *
     */
    void onNotHaveAccount();
}
