package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.model.GradeList;
import com.android.chengshijian.searchplus.model.GradeListResult;
import com.android.chengshijian.searchplus.model.GradeListTitle;
import com.android.chengshijian.searchplus.view.IHolderView;

/**
 * Created by chengshijian on 2017/5/18.
 * Modify by chengshijian on 2018/1/12.
 *
 */

public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.GradeListViewHolder> {
    private static final int TYPE_INFO = 0;
    private static final int TYPE_TITLE = 1;
    private static final int TYPE_DETAIL = 2;
    private GradeListResult mResult;

    public GradeListAdapter(GradeListResult result) {
        mResult = result;
    }

    @Override
    public GradeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_INFO:
                return new GradeListViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_grade_list_info_item, parent, false));
            case TYPE_TITLE:
                return new GradeListViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_grade_list_title_item, parent, false));
            case TYPE_DETAIL:
                return new GradeListViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_grade_list_detail_item, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(GradeListViewHolder holder, int position) {
            holder.bindData(mResult,position);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_INFO;
            case 1:
                return TYPE_TITLE;
            default:
                return TYPE_DETAIL;
        }
    }

    @Override
    public int getItemCount() {
        return mResult.getGradeLists().size() + 2;
    }

    public class GradeListViewHolder extends RecyclerView.ViewHolder implements IHolderView<GradeListResult> {

        private TextView mNumTextView;
        private TextView mNameTextView;
        private TextView mEnterTimeTextView;
        private TextView mDevelopLevelTextView;
        private TextView mDepartTextView;
        private TextView mMajorTextView;
        private TextView mClassBTextView;
        private TextView mTimeTextView;
        private TextView mCourseNameTextView;
        private TextView mCourseModelTextView;
        private TextView mCourseTimeTextView;
        private TextView mCreditTextView;
        private TextView mGradeTextView;
        private TextView mGPATextView;
        private TextView mTotalGPATextView;
        private TextView mAverageGPATextView;


        public GradeListViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        @Override
        public void initView() {
            mNumTextView=itemView.findViewById(R.id.account);
            mNameTextView=itemView.findViewById(R.id.name);
            mEnterTimeTextView=itemView.findViewById(R.id.time);
            mDevelopLevelTextView=itemView.findViewById(R.id.level);
            mDepartTextView=itemView.findViewById(R.id.depart);
            mMajorTextView=itemView.findViewById(R.id.major);
            mClassBTextView=itemView.findViewById(R.id.classB);
            mTotalGPATextView=itemView.findViewById(R.id.total_gpa);
            mAverageGPATextView=itemView.findViewById(R.id.aver_gpa);

            mTimeTextView=itemView.findViewById(R.id.time);
            mCourseNameTextView=itemView.findViewById(R.id.cname);
            mCourseModelTextView= itemView.findViewById(R.id.ctype);
            mCourseTimeTextView=itemView.findViewById(R.id.ctime);
            mCreditTextView=itemView.findViewById(R.id.credit);
            mGradeTextView=itemView.findViewById(R.id.grade);
            mGPATextView=itemView.findViewById(R.id.gpa);
        }

        private void bindTitleData(GradeListTitle title) {
            mNumTextView.setText(title.getAccount());
            mNameTextView.setText(title.getName());
            mEnterTimeTextView.setText(title.getEnterTime());
            mDevelopLevelTextView.setText(title.getDevelopLevel());
            mDepartTextView.setText(title.getDepart());
            mMajorTextView.setText(title.getMajor());
            mClassBTextView.setText(title.getClassB());
            mTotalGPATextView.setText(title.getTotalGPA());
            mAverageGPATextView.setText(title.getAverageGPA());
        }

        private void bindGradeListData(GradeList gradeList) {
            mTimeTextView.setText(gradeList.getTime());
            mCourseNameTextView.setText(gradeList.getCourseName());
            mCourseModelTextView.setText(gradeList.getCourseModel());
            mCourseTimeTextView.setText(gradeList.getCourseTime());
            mCreditTextView.setText(gradeList.getCredit());
            mGradeTextView.setText(gradeList.getGrade());
            mGPATextView.setText(gradeList.getGPA());
        }

        @Override
        public void bindData(GradeListResult data, int position) {
            if(position==0){
                bindTitleData(data.getTitle());
            }else if(position>1){//大于等于2
                bindGradeListData(data.getGradeLists().get(position-2));
            }
        }
    }
}