package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.model.Favorite;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.view.IHolderView;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ChengShiJian on 2018/1/15.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> mFavorites;

    public FavoriteAdapter(List<Favorite> favorites) {
        mFavorites = favorites;
    }

    public void delete(int pos) {
        DataSupport.deleteAll(Favorite.class,"mTime=?",mFavorites.get(pos).getTime());
        mFavorites.remove(pos);
        notifyItemRemoved(pos);

    }


    //设置数据
    public FavoriteAdapter setData(List<Favorite> favorites) {
        this.mFavorites = favorites;
        notifyDataSetChanged();
        return this;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {

        holder.bindData(mFavorites.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mFavorites.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder implements IHolderView<Favorite> {

        private TextView mTimeTv;
        private TextView mOperateTv;
        private TextView mResultTv;
        private TextView mFavoriteTimeTv;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        @Override
        public void initView() {
            mTimeTv=(TextView) findViewById(R.id.time);
            mOperateTv=(TextView) findViewById(R.id.operate);
            mResultTv=(TextView) findViewById(R.id.result);
            mFavoriteTimeTv=(TextView) findViewById(R.id.favorite_time);
        }

        @Override
        public void bindData(Favorite data, int position) {
            mTimeTv.setText(data.getTime());
            mOperateTv.setText(data.getOperate());
            mResultTv.setText(DataUtil.randomSubString(data.getResult()));
            mFavoriteTimeTv.setText("收藏于："+data.getFavoriteTime());
        }

        private View findViewById(int id){
            return itemView.findViewById(id);
        }
    }
}
