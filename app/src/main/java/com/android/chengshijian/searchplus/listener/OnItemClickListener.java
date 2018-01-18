package com.android.chengshijian.searchplus.listener;

import android.view.View;

/**
 *
 * 为谷歌方法的RecyclerView设计的点击事件接口
 *
 * Created by ChengShiJian on 2018/1/10.
 */

public interface OnItemClickListener {

    /**
     *
     * 当点击一个Item时
     *
     * @param view View
     * @param position 点击的位置
     *
     */
    void onItemClick(View view, int position);
}
