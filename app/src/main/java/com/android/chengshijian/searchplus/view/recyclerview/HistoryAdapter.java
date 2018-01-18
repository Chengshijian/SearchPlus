package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.model.History;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.view.IHolderView;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ChengShiJian on 2018/1/15.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> mHistories;

    public HistoryAdapter(List<History> histories) {
        mHistories = histories;
    }

    public void delete(int position){
        DataSupport.deleteAll(History.class,"mTime=?",mHistories.get(position).getTime());
        mHistories.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_history,parent,false));
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.bindData(mHistories.get(position),position);
    }


    @Override
    public int getItemCount() {
        return mHistories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements IHolderView<History>{

        private TextView mTimeTv;
        private TextView mOperateTv;
        private TextView mResultTv;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        @Override
        public void initView() {
            mTimeTv=(TextView) findViewById(R.id.time);
            mOperateTv=(TextView) findViewById(R.id.operate);
            mResultTv=(TextView) findViewById(R.id.result);
        }

        @Override
        public void bindData(History data, int position) {
            mTimeTv.setText(data.getTime());
            mOperateTv.setText(data.getOperate());
            mResultTv.setText(DataUtil.subString(data.getResult()));
        }

        private View findViewById(int id){
            return itemView.findViewById(id);
        }
    }
}
