package com.android.chengshijian.searchplus.model;

/**
 *
 * 评教类
 *
 * 实体类
 *
 * Created by 程世健 on 2017/6/30.
 */

public class Assess {

    private String mSeriesID;
    private String mPaperID;
    private String mTaskID;
    private String mTeacherNO;
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSeriesID() {
        return mSeriesID;
    }

    public void setSeriesID(String mSeriesID) {
        this.mSeriesID = mSeriesID;
    }

    public String getPaperID() {
        return mPaperID;
    }

    public void setPaperID(String mPaperID) {
        this.mPaperID = mPaperID;
    }

    public String getTaskID() {
        return mTaskID;
    }

    public void setTaskID(String mTaskID) {
        this.mTaskID = mTaskID;
    }

    public String getTeacherNO() {
        return mTeacherNO;
    }

    public void setTeacherNO(String mTeacherNO) {
        this.mTeacherNO = mTeacherNO;
    }

    @Override
    public String toString() {
        return "Assess{" +
                "mSeriesID='" + mSeriesID + '\'' +
                ", mPaperID='" + mPaperID + '\'' +
                ", mTaskID='" + mTaskID + '\'' +
                ", mTeacherNO='" + mTeacherNO + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
