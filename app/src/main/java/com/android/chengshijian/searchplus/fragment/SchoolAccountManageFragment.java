package com.android.chengshijian.searchplus.fragment;

import android.support.v7.widget.RecyclerView;

import com.android.chengshijian.searchplus.activity.AddSchoolAccountActivity;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.User;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.SharedPreferencesHelper;
import com.android.chengshijian.searchplus.view.recyclerview.AccountManageAdapter;
import com.android.chengshijian.searchplus.view.recyclerview.SchoolAccountManageAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 学号管理类
 * <p>
 * 继承自@link BaseManageFragment
 * <p>
 * Created by ChengShiJian on 2018/1/17.
 */

public class SchoolAccountManageFragment extends BaseManageFragment<User> {

    @Override
    public void onSaveAccountToSharedPreferences(int pos) {
        DataUtil.saveSchoolAccountToSharedPreferences(mList.get(pos).getAccount(), mList.get(pos).getPassword());
    }

    @Override
    public void onAddToHistory(int pos) {
        addToHistory(mList.get(pos).getAccount());
    }

    @Override
    public int onGetPosition() {
        return getPositionByAccount(mList, SharedPreferencesHelper.getStringValue(Constant.SCHOOL_ACCOUNT));
    }

    @Override
    public RecyclerView.Adapter onGetAdapter(int clickedPos) {
        return new SchoolAccountManageAdapter(mList, clickedPos);

    }

    @Override
    public int onGetSelectedPos() {
        return ((AccountManageAdapter) mAdapter).getSelectPosition();
    }

    @Override
    public void onDelete(int adapterPosition) {
        ((SchoolAccountManageAdapter) mAdapter).delete(adapterPosition);
    }

    @Override
    public void onClickedUpdateUI(int pos, int selectPos) {
        ((SchoolAccountManageAdapter) mAdapter).notifyItemChanged(pos, selectPos);//更新现在的视图
    }

    @Override
    public void onAddAccount() {
        startActivity(AddSchoolAccountActivity.class);
    }

    @Override
    public List<User> getList() {
        return DataSupport.findAll(User.class);
    }

    //通过账号获得此账号在集合中的位置
    private int getPositionByAccount(List<User> users, String account) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getAccount().equals(account)) {
                return i;
            }
        }
        return -1;
    }
}
