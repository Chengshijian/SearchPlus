package com.android.chengshijian.searchplus.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.listener.OnQueryPersonalInfoListener;
import com.android.chengshijian.searchplus.model.Personal;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.PersonalInfoQueryUtil;
import com.android.chengshijian.searchplus.util.ProgressDialogUtil;
import com.android.chengshijian.searchplus.view.recyclerview.BasePersonalInfoAdapter;
import com.android.chengshijian.searchplus.view.recyclerview.MorePersonalInfoAdapter;
import com.android.volley.VolleyError;

/**
 * 查询个人信息类
 *
 * Created by ChengShiJian on 2018/1/13.
 */

public class QueryPersonalInfoActivity extends BaseActivity implements OnQueryPersonalInfoListener, DialogInterface.OnClickListener {

    private ProgressDialog mProgressDialog;
    private RecyclerView mBaseInfoRv;
    private RecyclerView mMoreInfoRv;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_personal_info;
    }

    @Override
    public void initView() {
        mBaseInfoRv=findViewById(R.id.base_info_rv);
        mMoreInfoRv=findViewById(R.id.more_info_rv);
    }

    @Override
    public void initData() {
        initActionBar();
        transparentNavigationBar();
        initProgressDialog();
        initRecyclerView();
        requestPersonalInfo();
    }

    private void initActionBar() {
        setHasToolbar(true, null);
        setDisplayHomeAsUpEnabled(true);
        setActionBarTitle("个人信息");
    }

    private void initProgressDialog() {
        mProgressDialog = ProgressDialogUtil.getRequestDataDialog(this);
    }

    private void initRecyclerView() {
        mBaseInfoRv.setLayoutManager(new LinearLayoutManager(this));
        mMoreInfoRv.setLayoutManager(new LinearLayoutManager(this));
        mBaseInfoRv.setNestedScrollingEnabled(false);
        mMoreInfoRv.setNestedScrollingEnabled(false);
    }

    private void requestPersonalInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PersonalInfoQueryUtil.getPersonalInfo(QueryPersonalInfoActivity.this);
            }
        }).start();

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onStartQuery() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.show();
            }
        });
    }

    @Override
    public void onErrorResponse(final VolleyError volleyError) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                new AlertDialog.Builder(QueryPersonalInfoActivity.this)
                        .setTitle(R.string.hint)
                        .setCancelable(false)
                        .setMessage("请求个人信息时出错！\n错误信息：" + DataUtil.volleyErrorToString(volleyError))
                        .setPositiveButton(R.string.ok, QueryPersonalInfoActivity.this);
            }
        });

        DataUtil.addQueryPersonalInfoToHistory("请求个人信息时出错！\n错误信息：" + DataUtil.volleyErrorToString(volleyError));
    }

    @Override
    public void onSuccess(Personal result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        });
        DataUtil.addQueryPersonalInfoToHistory(result.toString());
        updateRecyclerViewDapter(result);
    }

    private void updateRecyclerViewDapter(Personal result) {
        mBaseInfoRv.setAdapter(new BasePersonalInfoAdapter(result));
        mMoreInfoRv.setAdapter(new MorePersonalInfoAdapter(result));
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        finish();
    }
}
