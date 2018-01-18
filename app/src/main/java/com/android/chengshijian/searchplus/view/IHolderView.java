package com.android.chengshijian.searchplus.view;

/**
 * Created by ChengShiJian on 2018/1/9.
 */

public interface IHolderView<T> extends IView {
    void bindData(T data, int position);
}
