package com.android.chengshijian.searchplus.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;
import com.android.chengshijian.searchplus.model.Personal;
import com.android.chengshijian.searchplus.view.recyclerview.holder.PersonalInfoViewHolder;

/**
 * Created by ChengShiJian on 2018/1/13.
 */

public class BasePersonalInfoAdapter extends RecyclerView.Adapter<BasePersonalInfoAdapter.BasePersonalInfoViewHolder>{


    private Personal mPersonal;

    public BasePersonalInfoAdapter(Personal personal) {
        mPersonal = personal;
    }

    @Override
    public BasePersonalInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BasePersonalInfoViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_personal_info,parent,false));
    }

    @Override
    public void onBindViewHolder(BasePersonalInfoViewHolder holder, int position) {
        holder.bindData(mPersonal,position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class BasePersonalInfoViewHolder extends PersonalInfoViewHolder {

        public BasePersonalInfoViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Personal data, int position) {
            super.bindData(data, position);
            switch (position){
                case 0:
                    setData(R.mipmap.ic_launcher_round,"姓名",data.getName());
                    break;
                case 1:
                    setData(R.mipmap.ic_launcher_round,"性别",data.getSex());
                    break;
                case 2:
                    setData(R.mipmap.ic_launcher_round,"身份证",data.getId());
                    break;
                case 3:
                    setData(R.mipmap.ic_launcher_round,"生日",data.getBirth());
                    break;
            }
        }
    }
}
