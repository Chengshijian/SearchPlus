package com.android.chengshijian.searchplus.view.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.model.Personal;
import com.android.chengshijian.searchplus.view.IHolderView;

/**
 * Created by ChengShiJian on 2018/1/14.
 */

public class PersonalInfoViewHolder extends RecyclerView.ViewHolder implements IHolderView<Personal> {
    public ImageView mImageView;
    public TextView mLabelTv;
    public TextView mTextTv;

    public PersonalInfoViewHolder(View itemView) {
        super(itemView);
        initView();
    }

    @Override
    public void initView() {
        mLabelTv =itemView.findViewById(R.id.label);
        mImageView=itemView.findViewById(R.id.image);
        mTextTv=itemView.findViewById(R.id.text);
    }

    @Override
    public void bindData(Personal data, int position) {

    }

    public void setData(int resId,String label,String text) {
        mImageView.setImageResource(resId);
        mLabelTv.setText(label);
        mTextTv.setText(text);
    }
}
