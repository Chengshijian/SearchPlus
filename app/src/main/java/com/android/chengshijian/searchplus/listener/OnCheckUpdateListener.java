package com.android.chengshijian.searchplus.listener;

import com.android.chengshijian.searchplus.model.Version;

import okhttp3.Call;

/**
 * Created by ChengShiJian on 2018/1/18.
 */

public interface OnCheckUpdateListener extends OnSuccessListener<Version>{
    void onError(Call call, Exception e, int id);

}
