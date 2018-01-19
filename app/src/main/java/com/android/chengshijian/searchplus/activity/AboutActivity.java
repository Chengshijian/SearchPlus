package com.android.chengshijian.searchplus.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.android.chengshijian.searchplus.BuildConfig;
import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.listener.OnCheckUpdateListener;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.Version;
import com.android.chengshijian.searchplus.util.CheckUpdateUtil;
import com.android.chengshijian.searchplus.util.LoginUtil;
import com.android.chengshijian.searchplus.util.SizeUtil;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.util.WebUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import okhttp3.Call;
import rx.functions.Action1;

/**
 * Created by ChengShiJian on 2018/1/17.
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener,OnCheckUpdateListener{

    private CardView mUpdateCv;
    private CardView mProjectAddressCv;
    private TextView mVersionTv;

    @Override
    public void initView() {
        mUpdateCv =findViewById(R.id.update);
        mProjectAddressCv =findViewById(R.id.project_address);
        mVersionTv=findViewById(R.id.version);
    }

    @Override
    public void initData() {
        setDisplayHomeAsUpEnabled(true);
        setActionBarTitle("关于" + getString(R.string.app_name));
        transparentNavigationBar();
        mVersionTv.setText(BuildConfig.VERSION_NAME);

        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){
                            LoginUtil.getValidSchoolAccount();
                        }
                    }
                });

    }

    @Override
    public void initListener() {
        mUpdateCv.setOnClickListener(this);
        mProjectAddressCv.setOnClickListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update:
                update();
                break;
            case R.id.project_address:
                openProjectAddress();
                break;
        }
    }

    private void update(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                CheckUpdateUtil.checkUpdate(AboutActivity.this);
            }
        }).start();
    }

    private void openProjectAddress(){
        WebUtil.openExternal(this, Constant.PROJECT_URL);
    }


    @Override
    public void onSuccess(final Version result) {
        if (result.getVersionShort().compareTo(BuildConfig.VERSION_NAME) > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(AboutActivity.this)
                            .setTitle("发现新版本")
                            .setCancelable(true)
                            .setMessage(
                                    "发现新版本" + result.getVersionShort() + "是否更新?\n"
                                            + result.getChangelog()
                                            + "\n软件大小："
                                            + SizeUtil.B2M(
                                            result.getBinary()
                                                    .getFsize()
                                    )+"M"
                            ).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            WebUtil.openExternal(AboutActivity.this,Constant.APP_URL);
                        }
                    }).setNegativeButton(R.string.cancel,null)
                            .show();
                }
            });
        }else {
            ToastUtil.showShortToast("已是最新版本!");
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        ToastUtil.showShortToast("检查更新失败！\n"+call.toString());
    }
}
