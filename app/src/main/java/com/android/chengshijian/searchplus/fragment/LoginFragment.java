package com.android.chengshijian.searchplus.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.presenter.LoginPresenter;
import com.android.chengshijian.searchplus.presenter.LoginPresenterImpl;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.view.LoginView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * 登录Fragment
 *
 * 采用MVP设计模式设计
 *
 * Created by ChengShiJian on 2017/10/9.
 */

public class LoginFragment extends BaseFragment implements LoginView {

    private AutoCompleteTextView mCompleteTextView;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private TextView mRegisterTextView;
    private LoginPresenter mLoginPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public boolean isHaveToolbar() {
        return false;
    }

    @Override
    public String getEmail() {
        return mCompleteTextView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    @Override
    public void showLoginMsg(BmobUser user, BmobException e) {
        if (e == null) {
            mLoginPresenter.save();
            getActivity().finish();
            //做登录成功的逻辑处理
        }else{
            ToastUtil.showShortToast("登陆失败！（"+e.getMessage()+"），错误代码："+e.getErrorCode());
            //做登录失败的逻辑处理
        }
    }

    @Override
    public AutoCompleteTextView getEmailAutoCompleteTextView() {
        return mCompleteTextView;
    }

    @Override
    public void initView() {
        mCompleteTextView=(AutoCompleteTextView)findViewById(R.id.email);
        mPasswordEditText=(EditText)findViewById(R.id.password);
        mLoginButton=(Button)findViewById(R.id.login);
        mRegisterTextView=(TextView)findViewById(R.id.register);
    }

    @Override
    public void initData() {
        mLoginPresenter=new LoginPresenterImpl(getActivity(),this);
        setToolbarTitle("App登录");
        transparentNavigationBar();
        setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.login();
            }
        });

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.beginRegister();
            }
        });

        mCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mLoginPresenter.autoCompleteEmail();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
