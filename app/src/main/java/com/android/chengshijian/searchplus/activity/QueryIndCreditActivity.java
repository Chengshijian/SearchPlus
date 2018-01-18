package com.android.chengshijian.searchplus.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.listener.OnQueryIndCreditListener;
import com.android.chengshijian.searchplus.model.IndCreditResult;
import com.android.chengshijian.searchplus.util.CreditQueryUtil;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.ProgressDialogUtil;
import com.android.chengshijian.searchplus.view.recyclerview.IndCreditAdapter;
import com.android.volley.VolleyError;

/**
 *
 * 查询自主学分类
 *
 * Created by ChengShiJian on 2018/1/12.
 *
 */

public class QueryIndCreditActivity extends BaseActivity implements OnQueryIndCreditListener {
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_ind_credit;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.independent_credit_rv);
    }

    @Override
    public void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressDialog = ProgressDialogUtil.getRequestDataDialog(this);
        setActionBarTitle("自主学分");
        setDisplayHomeAsUpEnabled(true);
        transparentNavigationBar();
        queryCredits();
    }

    private void queryCredits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CreditQueryUtil.getIndCredits(QueryIndCreditActivity.this);
            }
        }).start();

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onSuccess(Object result) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        });

        IndCreditResult indCreditResult=(IndCreditResult)result;
        DataUtil.addIndCreditInfoToHistory(indCreditResult.toString());
        mRecyclerView.setAdapter(new IndCreditAdapter(indCreditResult));
    }

    @Override
    public void onNotHaveCredit() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                new AlertDialog.Builder(QueryIndCreditActivity.this)
                        .setTitle(R.string.hint)
                        .setMessage(R.string.not_get_your_any_credit)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).show();
            }
        });
        DataUtil.addIndCreditInfoToHistory(getString(R.string.not_get_your_any_credit));
    }

    @Override
    public void onErrorResponse(final VolleyError volleyError) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mProgressDialog.dismiss();
                new AlertDialog.Builder(QueryIndCreditActivity.this)
                        .setTitle(R.string.hint)
                        .setMessage("查询时出错！\n错误原因："+DataUtil.volleyErrorToString(volleyError))
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).show();
            }
        });

        DataUtil.addIndCreditInfoToHistory("查询时出错！\n错误原因："+DataUtil.volleyErrorToString(volleyError));
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
    public void onRecordIs0() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                new AlertDialog.Builder(QueryIndCreditActivity.this)
                        .setTitle(R.string.hint)
                        .setMessage(R.string.not_get_your_independent_credit)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).show();
            }
        });
        DataUtil.addIndCreditInfoToHistory(getString(R.string.not_get_your_independent_credit));
    }
}
