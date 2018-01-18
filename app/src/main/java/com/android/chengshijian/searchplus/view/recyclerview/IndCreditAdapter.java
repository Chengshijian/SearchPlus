package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.model.IndCredit;
import com.android.chengshijian.searchplus.model.IndCreditResult;
import com.android.chengshijian.searchplus.view.IHolderView;


/**
 * Created by chengshijian on 2017/4/13.
 */

public class IndCreditAdapter extends RecyclerView.Adapter<IndCreditAdapter.IndCreditViewHolder> {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_GRADE_ITEM = 1;

    private IndCreditResult mResult;

    public IndCreditAdapter(IndCreditResult result) {
        mResult=result;
    }

    @Override
    public IndCreditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                return new IndCreditViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_ind_credit_title_item, parent, false));

            case TYPE_GRADE_ITEM:
                return new IndCreditViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_ind_credit_detail_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(IndCreditViewHolder holder, int position) {
            holder.bindData(mResult, position);

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_TITLE;
            default:
                return TYPE_GRADE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mResult.getCredits().size() + 1;
    }

    public class IndCreditViewHolder extends RecyclerView.ViewHolder implements IHolderView<IndCreditResult> {
        private TextView mName;
        private TextView mTime;
        private TextView mType;
        private TextView mCredit;
        private TextView mTotalGrade;

        public IndCreditViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        public void initView() {
            mName = itemView.findViewById(R.id.name);
            mTime = itemView.findViewById(R.id.time);
            mType = itemView.findViewById(R.id.type);
            mCredit = itemView.findViewById(R.id.credit);
            mTotalGrade=itemView.findViewById(R.id.total_grade);
        }
        @Override
        public void bindData(IndCreditResult data, int position) {
            if(position>0) {
                IndCredit credit=data.getCredits().get(position-1);
                mName.setText(credit.getName());
                mTime.setText(credit.getIdTime());
                mType.setText(credit.getCreditType());
                mCredit.setText(credit.getCredit());
            }else {
                mTotalGrade.setText(data.getTotalCredit());
            }
        }
    }
}
