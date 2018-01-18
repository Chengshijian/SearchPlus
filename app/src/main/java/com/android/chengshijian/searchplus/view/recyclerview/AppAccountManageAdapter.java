package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.model.App;
import com.android.chengshijian.searchplus.view.recyclerview.holder.ManageViewHolder;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ChengShiJian on 2018/1/17.
 */

public class AppAccountManageAdapter extends AccountManageAdapter<App> {
    public AppAccountManageAdapter(List<App> list, int selectPosition) {
        super(list, selectPosition);
    }

    public void delete(int position) {
        DataSupport.deleteAll(App.class, "mAccount=?", mList.get(position).getAccount());
        notifyItemRemoved(position);
        mList.remove(position);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppAccountManageViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_account, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AppAccountManageViewHolder) holder).itemView.setTag(position);
        ((AppAccountManageViewHolder) holder).bindData(mList.get(position), getSelectPosition());
    }


    public class AppAccountManageViewHolder extends ManageViewHolder<App> {

        public AppAccountManageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Object data, int position) {
            mAccountTv.setText(((App) data).getAccount());
            if ((Integer) (itemView.getTag()) == position) {
                mSelectIv.setVisibility(View.VISIBLE);
            } else {
                mSelectIv.setVisibility(View.GONE);
            }
        }
    }
}
