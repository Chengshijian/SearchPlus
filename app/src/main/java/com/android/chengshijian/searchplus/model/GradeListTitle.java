package com.android.chengshijian.searchplus.model;

/**
 * 成绩单标题类
 *
 * Created by chengshijian on 2017/5/21.
 * Modify by chengshijian on 2018/1/12.
 *
 */

public class GradeListTitle {

    private String mAccount;//学号
    private String mName;//姓名
    private String mEnterTime;//入学时间
    private String mDevelopLevel;//培养等级
    private String mDepart;//院系
    private String mMajor;//专业
    private String mClassB;//班级
    private String mTotalGPA;//总绩点
    private String mAverageGPA;//平均绩点

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

    public String getTotalGPA() {
        return mTotalGPA;
    }

    public void setTotalGPA(String totalGPA) {
        mTotalGPA = totalGPA;
    }

    public String getAverageGPA() {
        return mAverageGPA;
    }

    public void setAverageGPA(String averageGPA) {
        mAverageGPA = averageGPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeListTitle info = (GradeListTitle) o;

        if (mAccount != null ? !mAccount.equals(info.mAccount) : info.mAccount != null) return false;
        if (mName != null ? !mName.equals(info.mName) : info.mName != null) return false;
        if (mEnterTime != null ? !mEnterTime.equals(info.mEnterTime) : info.mEnterTime != null)
            return false;
        if (mDevelopLevel != null ? !mDevelopLevel.equals(info.mDevelopLevel) : info.mDevelopLevel != null)
            return false;
        if (mDepart != null ? !mDepart.equals(info.mDepart) : info.mDepart != null) return false;
        if (mMajor != null ? !mMajor.equals(info.mMajor) : info.mMajor != null) return false;
        if (mClassB != null ? !mClassB.equals(info.mClassB) : info.mClassB != null) return false;
        if (mTotalGPA != null ? !mTotalGPA.equals(info.mTotalGPA) : info.mTotalGPA != null)
            return false;
        return mAverageGPA != null ? mAverageGPA.equals(info.mAverageGPA) : info.mAverageGPA == null;
    }

    @Override
    public int hashCode() {
        int result = mAccount != null ? mAccount.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mEnterTime != null ? mEnterTime.hashCode() : 0);
        result = 31 * result + (mDevelopLevel != null ? mDevelopLevel.hashCode() : 0);
        result = 31 * result + (mDepart != null ? mDepart.hashCode() : 0);
        result = 31 * result + (mMajor != null ? mMajor.hashCode() : 0);
        result = 31 * result + (mClassB != null ? mClassB.hashCode() : 0);
        result = 31 * result + (mTotalGPA != null ? mTotalGPA.hashCode() : 0);
        result = 31 * result + (mAverageGPA != null ? mAverageGPA.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Info{" +
                "mAccount='" + mAccount + '\'' +
                ", mName='" + mName + '\'' +
                ", mEnterTime='" + mEnterTime + '\'' +
                ", mDevelopLevel='" + mDevelopLevel + '\'' +
                ", mDepart='" + mDepart + '\'' +
                ", mMajor='" + mMajor + '\'' +
                ", mClassB='" + mClassB + '\'' +
                ", mTotalGPA='" + mTotalGPA + '\'' +
                ", mAverageGPA='" + mAverageGPA + '\'' +
                '}';
    }
}
