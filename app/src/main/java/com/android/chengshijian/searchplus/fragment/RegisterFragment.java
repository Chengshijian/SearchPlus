package com.android.chengshijian.searchplus.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.presenter.RegisterPresenter;
import com.android.chengshijian.searchplus.presenter.RegisterPresenterImpl;
import com.android.chengshijian.searchplus.view.RegisterView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * 注册类
 * <p>
 * Mvp设计模式
 * <p>
 * Created by ChengShiJian on 2017/10/9.
 */

public class RegisterFragment extends BaseFragment implements RegisterView {
    private AutoCompleteTextView mCompleteTextView;
    private EditText mPasswordEditText1;
    private EditText mPasswordEditText2;
    private Button mRegisterButton;
    private RegisterPresenter mRegisterPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    public boolean isHaveToolbar() {
        return false;
    }

    @Override
    public void initView() {
        mCompleteTextView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordEditText1 = (EditText) findViewById(R.id.password1);
        mPasswordEditText2 = (EditText) findViewById(R.id.password2);
        mRegisterButton = (Button) findViewById(R.id.register);
    }

    @Override
    public void initData() {
        mRegisterPresenter = new RegisterPresenterImpl(this);
        setBackArrowEnabled(true);
        setToolbarTitle("注册");
        transparentNavigationBar();
        setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterPresenter.register();
            }
        });

        mCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRegisterPresenter.autoCompleteEmail();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public String getEmail() {
        return mCompleteTextView.getText().toString();
    }

    @Override
    public String getPasswordBefore() {
        return mPasswordEditText1.getText().toString();
    }

    @Override
    public String getPasswordAfter() {
        return mPasswordEditText2.getText().toString();
    }

    @Override
    public void showRegisterMsg(String msgType, BmobUser user, BmobException e) {
        switch (msgType) {
            case RegisterPresenterImpl.REGIDTER:
                if (e == null) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("提示")
                            .setMessage("注册成功！")
                            .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    getActivity().finish();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(getActivity(), "注册失败！（" + e.getMessage() + "）,错误代码：" + e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
                break;
            case RegisterPresenterImpl.TWO_PASSWORD_NOT_SAME:
                Toast.makeText(getActivity(), "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public AutoCompleteTextView getEmailAutoCompleteTextView() {
        return mCompleteTextView;
    }
}
