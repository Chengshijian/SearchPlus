package com.android.chengshijian.searchplus.app;

import android.app.Application;
import android.content.Context;

import com.android.chengshijian.searchplus.MainActivity;
import com.android.chengshijian.searchplus.listener.OnCheckUpdateListener;
import com.android.chengshijian.searchplus.model.Version;
import com.android.chengshijian.searchplus.util.CheckUpdateUtil;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.HttpRequestUtil;
import com.android.chengshijian.searchplus.util.SharedPreferencesHelper;
import com.android.chengshijian.searchplus.util.ToastUtil;
import com.android.chengshijian.searchplus.view.IApplication;
import com.orhanobut.logger.Logger;

import org.litepal.LitePal;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import okhttp3.Call;

/**
 * BaseApplication
 * <p>
 * 全局类
 * <p>
 * 做初始化操作
 * <p>
 * Created by ChengShiJian on 2018/1/5.
 */

public class BaseApplication extends Application implements IApplication {

    private static Context mContext;

    public static Context getContextApplication() {
        return mContext.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initLogger();
        initSharedPreferences();
        initHttpRequestUtil();
        initLitePal();
        initUserSettings();
        initToastUtil();
        initBmob();
    }

    @Override
    public void initLitePal() {
        LitePal.initialize(this);
    }

    @Override
    public void initUserSettings() {
        if (!DataUtil.isSaveData()) {
            DataUtil.clearUserData();
        }
    }

    @Override
    public void initBmob() {
//        第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("bbf454d265ab3be7aaa63503ad6c914b")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    @Override
    public void initToastUtil() {
        ToastUtil.init(this);
    }

    @Override
    public void initLogger() {
        Logger
                .init("chengshijian_>>")  // 配置tag
                //.hideThreadInfo()  隐藏Log中的线程信息
                .methodCount(3)  // 配置Log中调用堆栈的函数行数
                //.logLevel(LogLevel.NONE)  // 设置Log的是否输出，LogLevel.NONE即无Log输出,默认是FULL
                .methodOffset(0); // 设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息
    }

    @Override
    public void initSharedPreferences() {
        SharedPreferencesHelper.init(this);
    }

    @Override
    public void initHttpRequestUtil() {
        HttpRequestUtil.init(this);
    }
}
