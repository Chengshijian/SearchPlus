package com.android.chengshijian.searchplus.listener;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by ChengShiJian on 2017/10/9.
 *
 * 注册用户事件监听器
 */

public interface OnRegisterListener {

    /**
     * 当注册操作逻辑完成后，要调用的接口
     * @param user BmobUser 对象，内含账号、密码等信息
     * @param e BmobException 对象 如果e==null,表示注册成功，否则表示注册失败
     *          失败的信息保存在了对象e中，可调用获取
     */
    void onRegister(BmobUser user, BmobException e);
}
