package com.android.chengshijian.searchplus.util;

import android.graphics.Bitmap;

import com.android.chengshijian.searchplus.listener.OnCheckAccountValidateListener;
import com.android.chengshijian.searchplus.listener.OnImageRequestListener;
import com.android.chengshijian.searchplus.listener.OnLoadIdentifyCodeListener;
import com.android.chengshijian.searchplus.listener.OnLoginSchoolListener;
import com.android.chengshijian.searchplus.listener.OnStringRequestListener;
import com.android.chengshijian.searchplus.model.Constant;
import com.android.chengshijian.searchplus.model.User;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 登录工具类
 *
 * Created by ChengShiJian on 2018/1/8.
 */

public class LoginUtil {

    public static final int GRADE=0;
    public static final int GRADE_LIST=1;
    public static final int ACCOUNT_MANAGE=2;
    public static final int IND_CREDIT=3;
    public static final int UPDATE_UI=4;
    public static final int LECTURE=5;
    public static final int PERSONAL_INFO=6;

    private static String mIdentifyingCode;

    private static boolean isAccountValidate(String account) {
        return account.length() == 10;
    }

    public static void tryLogin(String url, final User user, final OnLoginSchoolListener listener) {
        /**
         *测试完，记得加上下面代码
         */
        if (!isAccountValidate(user.getAccount())) {
            listener.onAccountLowerThan10();
            return;
        }

        /**
         * 在这里实现登录的逻辑代码
         */

        listener.onStartLogin();
        HttpRequestUtil.stringRequestByPost(url, new OnStringRequestListener() {

            @Override
            public void onResponse(String s) {
                if (s.contains(Constant.LOGIN_SUCCESS)) {
                    listener.onLoginSuccess();
                } else if (s.contains(Constant.ADDRESS_CLOSED)) {
                    listener.onAddressClosed();
                } else {
                    listener.onLoginFailed();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onErrorResponse(Constant.LOGIN, volleyError);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return DataUtil.getGradeCookieMap();
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("WebUserNO", user.getAccount());
                map.put("Password", user.getPassword());
                map.put("Agnomen", mIdentifyingCode);
                return map;
            }

            @Override
            public void parseNetworkResponse(NetworkResponse response) {

            }
        });
    }

    public static void loadIdentifyingCode(String url, final OnLoadIdentifyCodeListener listener) {
        HttpRequestUtil.imageRequest(url, new OnImageRequestListener() {
            @Override
            public void onResponse(Bitmap bitmap) {
                try {
                    mIdentifyingCode = RecognizeCodeUtil.recognize(bitmap);
                    listener.onLoadIdentifyCodeSuccess(mIdentifyingCode, bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void parseNetworkResponse(NetworkResponse response) {
                Map<String, String> headers = response.headers;
                String cookies = headers.get(Constant.SET_COOKIE).replace("; path=/", "");
                //这里没有把下面这一行这段代码放在OnLoadIdentifyCodeListener的回调里去调用，是因为Cookie的使用相当频繁
                //保存时间的代码放到回调里面了
                SharedPreferencesHelper.putStringValue(Constant.COOKIE, cookies);
                listener.onGetCookie(cookies);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onErrorResponse(Constant.ID_CODE, volleyError);
            }
        });
    }


    public static void isValidateAccount(final int type, final OnCheckAccountValidateListener listener) {
        listener.onStartCheck();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginUtil.loadIdentifyingCode(Constant.IDENTIFYING_CODE_REQUEST_URL, new OnLoadIdentifyCodeListener() {

                    @Override
                    public void onLoadIdentifyCodeSuccess(String code, Bitmap bitmap) {

                        LoginUtil.tryLogin(Constant.LOGIN_URL, DataUtil.getCurrentSchoolUser(), new OnLoginSchoolListener() {
                            @Override
                            public void onStartLogin() {

                            }

                            @Override
                            public void onAccountLowerThan10() {

                            }

                            @Override
                            public void onAddressClosed() {
                                listener.onAddressClosed();


                            }

                            @Override
                            public void onLoginSuccess() {
                                listener.onCheckSuccess(type);
                            }

                            @Override
                            public void onLoginFailed() {
                                listener.onLoginFailed();

                            }

                            @Override
                            public void onErrorResponse(int type, VolleyError volleyError) {
                                listener.onErrorResponse(Constant.LOGIN, volleyError);
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(int type, VolleyError volleyError) {
                        listener.onErrorResponse(Constant.ID_CODE, volleyError);
                    }

                    @Override
                    public void onGetCookie(String cookie) {
                        listener.onGetCookie(cookie);

                    }
                });
            }
        }).start();

    }
}
