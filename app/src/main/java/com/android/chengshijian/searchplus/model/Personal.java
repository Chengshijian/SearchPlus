package com.android.chengshijian.searchplus.model;

/**
 *
 * 个人信息类
 *
 * Created by ChengShiJian on 2018/1/13.
 */

public class Personal {
    private String mAccount;//学号
    private String mName;//姓名
    private String mSex;//性别
    private String mId;//身份证
    private String mBirth;//生日
    private String mEnterTime;//入学年
    private String mDevelopLevel;//培养层次
    private String mDepart;//院系
    private String mMajor;//专业
    private String mClassB;//班级
    private String mClassN;//年级
    private String mLeaveSchoolLimit;//休学年限

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getBirth() {
        return mBirth;
    }

    public void setBirth(String birth) {
        mBirth = birth;
    }

    public String getEnterTime() {
        return mEnterTime;
    }

    public void setEnterTime(String enterTime) {
        mEnterTime = enterTime;
    }

    public String getDevelopLevel() {
        return mDevelopLevel;
    }

    public void setDevelopLevel(String developLevel) {
        mDevelopLevel = developLevel;
    }

    public String getDepart() {
        return mDepart;
    }

    public void setDepart(String depart) {
        mDepart = depart;
    }

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String major) {
        mMajor = major;
    }

    public String getClassB() {
        return mClassB;
    }

    public void setClassB(String classB) {
        mClassB = classB;
    }

    public String getClassN() {
        return mClassN;
    }

    public void setClassN(String classN) {
        mClassN = classN;
    }

    public String getLeaveSchoolLimit() {
        return mLeaveSchoolLimit;
    }

    public void setLeaveSchoolLimit(String leaveSchoolLimit) {
        mLeaveSchoolLimit = leaveSchoolLimit;
    }

    @Override
    public String toString() {
        return "Personal{" +
                "mAccount='" + mAccount + '\'' +
                ", mName='" + mName + '\'' +
                ", mSex='" + mSex + '\'' +
                ", mId='" + mId + '\'' +
                ", mBirth='" + mBirth + '\'' +
                ", mEnterTime='" + mEnterTime + '\'' +
                ", mDevelopLevel='" + mDevelopLevel + '\'' +
                ", mDepart='" + mDepart + '\'' +
                ", mMajor='" + mMajor + '\'' +
                ", mClassB='" + mClassB + '\'' +
                ", mClassN='" + mClassN + '\'' +
                ", mLeaveSchoolLimit='" + mLeaveSchoolLimit + '\'' +
                '}';
    }
}
