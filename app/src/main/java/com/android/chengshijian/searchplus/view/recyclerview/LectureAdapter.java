package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.listener.OnItemClickListener;
import com.android.chengshijian.searchplus.model.Lecture;
import com.android.chengshijian.searchplus.util.ColorUtil;
import com.android.chengshijian.searchplus.view.IHolderView;

import java.util.List;

/**
 * Created by chengshijian on 2017/5/18.
 */

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.LectureViewHolder> {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_INFO = 1;
    private List<Lecture> mLectures;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public LectureAdapter(List<Lecture> mLectures, OnItemClickListener listener) {
        this.mLectures = mLectures;
        mListener = listener;
    }

    @Override
    public LectureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                return new LectureViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.lecture_title_item, parent, false));
            case TYPE_INFO:
                return new LectureViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.lecture_info_item, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final LectureViewHolder holder, int position) {
        if (position > 0) {
            holder.bindData(mLectures.get(position - 1),position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, holder.getAdapterPosition()-1);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_TITLE;
            default:
                return TYPE_INFO;
        }
    }

    @Override
    public int getItemCount() {
        return mLectures.size() + 1;
    }

    public class LectureViewHolder extends RecyclerView.ViewHolder implements IHolderView<Lecture> {

        private TextView mNameTextView;
        private TextView mTimeTextView;
        private TextView mAddressTextView;
        private ImageView mView;

        public LectureViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        @Override
        public void initView() {
            mNameTextView = itemView.findViewById(R.id.name);
            mTimeTextView = itemView.findViewById(R.id.time);
            mAddressTextView = itemView.findViewById(R.id.address);
            mView = itemView.findViewById(R.id.view);
        }

        @Override
        public void bindData(Lecture data, int position) {
            mNameTextView.setText(data.getName());
            mTimeTextView.setText(data.getTime());
            mAddressTextView.setText(data.getAddress());
            mView.setBackgroundColor(ColorUtil.nextColor());
        }
    }
}