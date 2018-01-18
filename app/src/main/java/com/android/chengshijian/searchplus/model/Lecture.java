package com.android.chengshijian.searchplus.model;

/**
 * 讲座类
 *
 * Created by chengshijian on 2017/5/18.
 */

public class Lecture {

    private String mNum;
    private String mName;
    private String mUser;
    private String mTime;
    private String mAddress;
    private String mDepart;
    private String mLimitPersonNum;
    private String mPublishedPlan;
    private String mNumOfTerm;

    public String getNum() {
        return mNum;
    }

    public void setNum(String num) {
        mNum = num;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getDepart() {
        return mDepart;
    }

    public void setDepart(String depart) {
        mDepart = depart;
    }

    public String getLimitPersonNum() {
        return mLimitPersonNum;
    }

    public void setLimitPersonNum(String limitPersonNum) {
        mLimitPersonNum = limitPersonNum;
    }

    public String getPublishedPlan() {
        return mPublishedPlan;
    }

    public void setPublishedPlan(String publishedPlan) {
        mPublishedPlan = publishedPlan;
    }

    public String getNumOfTerm() {
        return mNumOfTerm;
    }

    public void setNumOfTerm(String numOfTerm) {
        mNumOfTerm = numOfTerm;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "mNum='" + mNum + '\'' +
                ", mName='" + mName + '\'' +
                ", mUser='" + mUser + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mDepart='" + mDepart + '\'' +
                ", mLimitPersonNum='" + mLimitPersonNum + '\'' +
                ", mPublishedPlan='" + mPublishedPlan + '\'' +
                ", mNumOfTerm='" + mNumOfTerm + '\'' +
                '}';
    }
}
