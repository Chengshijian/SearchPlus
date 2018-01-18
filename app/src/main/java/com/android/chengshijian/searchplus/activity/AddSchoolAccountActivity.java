package com.android.chengshijian.searchplus.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.listener.OnLoadIdentifyCodeListener;
import com.android.chengshijian.searchplus.listener.OnLoginSchoolListener;
import com.android.chengshijian.searchplus.presenter.IInputAccountInfo;
import com.android.chengshijian.searchplus.presenter.InputAccountInfoPresenter;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.SharedPreferencesHelper;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.volley.VolleyError;

import java.util.Calendar;

/**
 *
 * 添加学号类
 *
 * Created by ChengShiJian on 2018/1/8.
 */

public class AddSchoolAccountActivity extends BaseActivity implements IInputAccountInfo, View.OnClickListener, OnLoginSchoolListener, OnLoadIdentifyCodeListener {

    private EditText mAccountEtv;
    private EditText mPasswordEtv;
    private Button mAddButton;
    private CheckBox mLookOverPswCbx;
    private ProgressDialog mProgressDialog;

    private InputAccountInfoPresenter mPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_input_account;
    }

    @Override
    public void initView() {
        mAccountEtv = findViewById(R.id.account);
        mPasswordEtv = findViewById(R.id.password);
        mAddButton = findViewById(R.id.add);
        mLookOverPswCbx = findViewById(R.id.look_over_password);
    }

    @Override
    public void initData() {
        mPresenter = new InputAccountInfoPresenter(this, this, this);
        initProgressDialog();
        requestIdentifyCode();
        setActionBarTitle("添加学号");
        setDisplayHomeAsUpEnabled(true);
        transparentNavigationBar();
    }

    @Override
    public void initListener() {
        mAddButton.setOnClickListener(this);
        mLookOverPswCbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mPasswordEtv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mPasswordEtv.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getResources().getString(R.string.on_checking_account));
    }

    private void requestIdentifyCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadIdentifyCode();
            }
        }).start();

    }

    @Override
    public String getAccount() {
        return mAccountEtv.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEtv.getText().toString();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                mPresenter.check();
                break;
        }
    }

    @Override
    public void onStartLogin() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.show();
            }
        });

    }

    @Override
    public void onAccountLowerThan10() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast(R.string.account_lower_than_10);
            }
        });

    }

    @Override
    public void onAddressClosed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                new AlertDialog.Builder(AddSchoolAccountActivity.this)
                        .setMessage(R.string.address_closed)
                        .setTitle(R.string.hint)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        });
        mPresenter.saveInfo();
        finish();
    }

    @Override
    public void onLoginFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                new AlertDialog.Builder(AddSchoolAccountActivity.this)
                        .setTitle(R.string.hint)
                        .setMessage(R.string.input_correct_account_and_password)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestIdentifyCode();
                            }
                        }).show();
            }
        });
    }

    @Override
    public void onLoadIdentifyCodeSuccess(final String code, Bitmap bitmap) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToast("验证码识别成功：" + code);
            }
        });
    }

    @Override
    public void onErrorResponse(int type, final VolleyError volleyError) {
        if (type == Constant.LOGIN) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.dismiss();
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showLongToast(DataUtil.volleyErrorToString(volleyError));
            }
        });

    }

    @Override
    public void onGetCookie(String cookie) {
        SharedPreferencesHelper
                .putStringValue(
                        Constant.COOKIE_GET_TIME,
                        String.valueOf(
                                Calendar
                                        .getInstance()
                                        .getTimeInMillis()
                        )
                );
    }
}
