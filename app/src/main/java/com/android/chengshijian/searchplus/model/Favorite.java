package com.android.chengshijian.searchplus.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 收藏类
 *
 * Created by ChengShiJian on 2018/1/15.
 */

public class Favorite extends DataSupport {
    @Column(unique = true, defaultValue = "unknown")
    private String mTime;
    private String mOperate;
    private String mResult;
    private String mFavoriteTime;

    private History mHistory;

    public History getHistory() {
        return mHistory;
    }

    public void setHistory(History history) {
        mHistory = history;
        mTime=history.getTime();
        mOperate=history.getOperate();
        mResult=mHistory.getResult();
    }

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

    public String getFavoriteTime() {
        return mFavoriteTime;
    }

    public void setFavoriteTime(String favoriteTime) {
        mFavoriteTime = favoriteTime;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "mTime='" + mTime + '\'' +
                ", mOperate='" + mOperate + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mFavoriteTime='" + mFavoriteTime + '\'' +
                ", mHistory=" + mHistory +
                '}';
    }
}
