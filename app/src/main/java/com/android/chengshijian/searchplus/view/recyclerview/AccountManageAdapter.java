package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by ChengShiJian on 2018/1/10.
 */

public abstract class AccountManageAdapter<T> extends RecyclerView.Adapter{

    public List<T> mList;
    private int mSelectPosition;

    public AccountManageAdapter(List<T> list, int selectPosition) {
        mList = list;
        mSelectPosition = selectPosition;
    }

    public int getSelectPosition() {
        return mSelectPosition;
    }


    public void notifyItemChanged(int position, int selectPos) {
        mSelectPosition = selectPos;
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
