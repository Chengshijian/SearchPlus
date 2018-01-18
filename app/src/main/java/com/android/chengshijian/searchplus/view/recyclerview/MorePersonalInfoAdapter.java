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
 * Created by ChengShiJian on 2018/1/14.
 */

public class MorePersonalInfoAdapter extends RecyclerView.Adapter<MorePersonalInfoAdapter.MorePersonalInfoViewHolder> {

    private Personal mPersonal;

    public MorePersonalInfoAdapter(Personal personal) {
        mPersonal = personal;
    }

    @Override
    public MorePersonalInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MorePersonalInfoViewHolder(LayoutInflater.from(BaseApplication.getContextApplication()).inflate(R.layout.card_personal_info,parent,false));
    }

    @Override
    public void onBindViewHolder(MorePersonalInfoViewHolder holder, int position) {
        holder.bindData(mPersonal,position);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MorePersonalInfoViewHolder extends PersonalInfoViewHolder {

        public MorePersonalInfoViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Personal data, int position) {
            super.bindData(data, position);
            switch (position){
                case 0:
                    setData(R.mipmap.ic_launcher_round,"学号",data.getAccount());
                    break;
                case 1:
                    setData(R.mipmap.ic_launcher_round,"培养层次",data.getDevelopLevel());
                    break;
                case 2:
                    setData(R.mipmap.ic_launcher_round,"专业",data.getMajor());
                    break;
                case 3:
                    setData(R.mipmap.ic_launcher_round,"年级",data.getClassN());
                    break;
                case 4:
                    setData(R.mipmap.ic_launcher_round,"班级",data.getClassB());
                    break;
                case 5:
                    setData(R.mipmap.ic_launcher_round,"院系",data.getDepart());
                    break;
                case 6:
                    setData(R.mipmap.ic_launcher_round,"入学时间",data.getEnterTime());
                    break;
                case 7:
                    setData(R.mipmap.ic_launcher_round,"休学年限",data.getLeaveSchoolLimit());
                    break;
            }
        }
    }
}
