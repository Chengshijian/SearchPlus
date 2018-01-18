package com.android.chengshijian.searchplus.view;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by ChengShiJian on 2018/1/17.
 */

public interface IBaseManageFragmentView<T> {
    void onSaveAccountToSharedPreferences(int pos);
    void onAddToHistory(int pos);
    List<T> getList();
    int onGetPosition();
    RecyclerView.Adapter onGetAdapter(int clickedPos);
    int onGetSelectedPos();
    void onDelete(int adapterPosition);
    void onClickedUpdateUI(int pos,int selectPos);
    void onAddAccount();
}
