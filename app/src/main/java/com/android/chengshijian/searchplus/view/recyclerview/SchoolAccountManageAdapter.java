package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.model.User;
import com.android.chengshijian.searchplus.view.recyclerview.holder.ManageViewHolder;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ChengShiJian on 2018/1/17.
 */

public class SchoolAccountManageAdapter extends AccountManageAdapter<User> {

    public SchoolAccountManageAdapter(List<User> list, int selectPosition) {
        super(list, selectPosition);
    }

    public void delete(int position) {
        DataSupport.deleteAll(User.class, "mAccount=?", mList.get(position).getAccount());
        notifyItemRemoved(position);
        mList.remove(position);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SchoolAccountManageViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_account, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SchoolAccountManageViewHolder) holder).itemView.setTag(position);
        ((SchoolAccountManageViewHolder) holder).bindData(mList.get(position), getSelectPosition());
    }

    public class SchoolAccountManageViewHolder extends ManageViewHolder<User> {

        public SchoolAccountManageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Object data, int position) {
            mAccountTv.setText(((User) data).getAccount());
            if ((Integer) (itemView.getTag()) == position) {
                mSelectIv.setVisibility(View.VISIBLE);
            } else {
                mSelectIv.setVisibility(View.GONE);
            }
        }
    }
}
