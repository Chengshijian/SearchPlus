package com.android.chengshijian.searchplus.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 *
 * 历史记录类
 *
 * Created by ChengShiJian on 2018/1/15.
 */

public class History extends DataSupport{
    @Column(unique = true, defaultValue = "unknown")
    private String mTime;
    private String mOperate;
    private String mResult;

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getOperate() {
        return mOperate;
    }

    public void setOperate(String operate) {
        mOperate = operate;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }
}
