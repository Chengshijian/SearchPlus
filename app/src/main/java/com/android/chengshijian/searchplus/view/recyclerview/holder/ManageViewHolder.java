package com.android.chengshijian.searchplus.view.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.view.IHolderView;

import org.litepal.crud.DataSupport;

/**
 * Created by ChengShiJian on 2018/1/17.
 */

public abstract class ManageViewHolder<U extends DataSupport> extends RecyclerView.ViewHolder implements IHolderView{
        protected TextView mAccountTv;
        protected ImageView mSelectIv;

        public ManageViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        @Override
        public void initView() {
            mAccountTv = itemView.findViewById(R.id.account);
            mSelectIv = itemView.findViewById(R.id.select);
        }
}
