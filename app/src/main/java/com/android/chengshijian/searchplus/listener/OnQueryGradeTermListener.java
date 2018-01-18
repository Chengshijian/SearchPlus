package com.android.chengshijian.searchplus.listener;

import java.util.List;

/**
 *
 * 查询成绩获取学期事件监听器接口
 *
 * Created by ChengShiJian on 2018/1/9.
 */

public interface OnQueryGradeTermListener extends OnQueryListener {

    /**
     * 当获得学期成功时
     *
     * @param terms 学期集合
     * @param maxTerm
     */
    void onResponse(List<String> terms, int maxTerm);
}
