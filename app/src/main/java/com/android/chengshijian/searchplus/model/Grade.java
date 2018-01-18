package com.android.chengshijian.searchplus.model;

/**
 *
 * 成绩类
 *
 * Created by chengshijian on 2017/5/21.
 */

public class Grade {
    private String mProperty;//课程性质
    private String mNum;//课程号
    private String mName;//课程名称
    private String mType;//考试类型
    private String mTime;//学时
    private String mCredit;//学分
    private String GradeType;//成绩类型
    private String mNormalGrade;//平时成绩
    private String mEndOfTermGrade;//期末成绩
    private String mTotalGrade;//总评成绩

    public String getProperty() {
        return mProperty;
    }

    public void setProperty(String property) {
        mProperty = property;
    }

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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getCredit() {
        return mCredit;
    }

    public void setCredit(String credit) {
        mCredit = credit;
    }

    public String getGradeType() {
        return GradeType;
    }

    public void setGradeType(String gradeType) {
        GradeType = gradeType;
    }

    public String getNormalGrade() {
        return mNormalGrade;
    }

    public void setNormalGrade(String normalGrade) {
        mNormalGrade = normalGrade;
    }

    public String getEndOfTermGrade() {
        return mEndOfTermGrade;
    }

    public void setEndOfTermGrade(String endOfTermGrade) {
        mEndOfTermGrade = endOfTermGrade;
    }

    public String getTotalGrade() {
        return mTotalGrade;
    }

    public void setTotalGrade(String totalGrade) {
        mTotalGrade = totalGrade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "mProperty='" + mProperty + '\'' +
                ", mNum='" + mNum + '\'' +
                ", mName='" + mName + '\'' +
                ", mType='" + mType + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mCredit='" + mCredit + '\'' +
                ", GradeType='" + GradeType + '\'' +
                ", mNormalGrade='" + mNormalGrade + '\'' +
                ", mEndOfTermGrade='" + mEndOfTermGrade + '\'' +
                ", mTotalGrade='" + mTotalGrade + '\'' +
                '}';
    }
}
