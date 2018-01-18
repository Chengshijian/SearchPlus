package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.model.Grade;
import com.android.chengshijian.searchplus.util.GradeFilter;
import com.android.chengshijian.searchplus.view.IHolderView;

import java.util.List;

/**
 * Created by ChengShiJian on 2018/1/9.
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeRecyclerViewHolder> {
    private List<Grade> mGrades;

    public GradeAdapter(List<Grade> grades) {
        mGrades = grades;
    }

    @Override
    public GradeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GradeRecyclerViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.grade_info_rcv, parent, false));
    }

    @Override
    public void onBindViewHolder(GradeRecyclerViewHolder holder, int position) {
        holder.bindData(mGrades.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mGrades.size();
    }

    public class GradeRecyclerViewHolder extends RecyclerView.ViewHolder implements IHolderView<Grade> {

        private TextView mNameTextView;
        private TextView mCreditTextView;
        private TextView mEndOfTermGradeTextView;
        private TextView mTotalGradeTextView;
        private TextView mStateTextView;
        private TextView mNormalGradeTextView;


        public GradeRecyclerViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        @Override
        public void bindData(Grade grade, int position) {
            mNameTextView.setText(grade.getName());
            mCreditTextView.setText(grade.getCredit());
            mEndOfTermGradeTextView.setText(grade.getEndOfTermGrade());
            mTotalGradeTextView.setText(grade.getTotalGrade());
            mNormalGradeTextView.setText(grade.getNormalGrade());
            if (Float.valueOf(GradeFilter.filter2Grade(grade.getTotalGrade())) >= 60) {
                mStateTextView.setText(R.string.pass);
            } else {
                mStateTextView.setText(R.string.not_pass);
            }
        }

        @Override
        public void initView() {
            mNameTextView = (TextView) findViewById(R.id.name);
            mCreditTextView = (TextView) findViewById(R.id.credit);
            mEndOfTermGradeTextView = (TextView) findViewById(R.id.end_of_term_grade);
            mTotalGradeTextView = (TextView) findViewById(R.id.total_grade);
            mStateTextView = (TextView) findViewById(R.id.state);
            mNormalGradeTextView = (TextView) findViewById(R.id.normal_grade);
        }


        private View findViewById(int id) {
            return itemView.findViewById(id);
        }

    }
}
