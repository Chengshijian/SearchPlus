package com.android.chengshijian.searchplus.listener;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by ChengShiJian on 2017/10/9.
 *
 * 登录事件监听接口
 */

public interface OnLoginListener {

    /**
     * 当登录操作逻辑完成后，要调用的接口
     * @param user BmobUser 对象，内含账号、密码等信息
     * @param e BmobException 对象 如果e==null,表示登录成功，否则表示登录失败
     *          失败的信息保存在对象e中，可调用获取
     */
    void onLogin(BmobUser user, BmobException e);
}
