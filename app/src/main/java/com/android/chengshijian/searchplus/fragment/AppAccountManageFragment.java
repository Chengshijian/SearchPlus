package com.android.chengshijian.searchplus.fragment;

import android.support.v7.widget.RecyclerView;

import com.android.chengshijian.searchplus.activity.LoginActivity;
import com.android.chengshijian.searchplus.model.App;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.SharedPreferencesHelper;
import com.android.chengshijian.searchplus.view.recyclerview.AppAccountManageAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * App账号管理Fragment
 * <p>
 * 继承自BaseManageFragment
 * <p>
 * Created by ChengShiJian on 2018/1/17.
 */

public class AppAccountManageFragment extends BaseManageFragment<App> {
    @Override
    public void onSaveAccountToSharedPreferences(int pos) {
        DataUtil.saveAppAccountToSharedPreferences(mList.get(pos).getAccount(), mList.get(pos).getPassword());
    }

    @Override
    public void onAddToHistory(int pos) {
        addToHistory(mList.get(pos).getAccount());
    }

    @Override
    public int onGetPosition() {
        return getPositionByAccount(mList, SharedPreferencesHelper.getStringValue(Constant.APP_ACCOUNT));
    }

    @Override
    public RecyclerView.Adapter onGetAdapter(int clickedPos) {
        return new AppAccountManageAdapter(mList, clickedPos);
    }

    @Override
    public int onGetSelectedPos() {
        return ((AppAccountManageAdapter) mAdapter).getSelectPosition();
    }

    @Override
    public void onDelete(int adapterPosition) {
        ((AppAccountManageAdapter) mAdapter).delete(adapterPosition);
    }

    @Override
    public void onClickedUpdateUI(int pos, int selectPos) {
        ((AppAccountManageAdapter) mAdapter).notifyItemChanged(pos, selectPos);//更新现在的视图
    }

    @Override
    public void onAddAccount() {
        startActivity(LoginActivity.class);
    }

    @Override
    public List<App> getList() {
        return DataSupport.findAll(App.class);
    }

    //通过账号获得此账号在集合中的位置
    private int getPositionByAccount(List<App> apps, String account) {
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getAccount().equals(account)) {
                return i;
            }
        }
        return -1;
    }
}
