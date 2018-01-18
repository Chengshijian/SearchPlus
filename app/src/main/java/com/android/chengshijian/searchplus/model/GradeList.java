package com.android.chengshijian.searchplus.model;

/**
 * 成绩单列表类
 *
 * Created by chengshijian on 2017/5/21.
 * Modify by chengshijian on 2018/1/12.
 *
 */

public class GradeList {
    private String mTime;
    private String mCourseNum;
    private String mCourseName;
    private String mCourseModel;
    private String mCourseTime;
    private String mCredit;
    private String mGrade;
    private String mGPA;

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getCourseNum() {
        return mCourseNum;
    }

    public void setCourseNum(String courseNum) {
        mCourseNum = courseNum;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public String getCourseModel() {
        return mCourseModel;
    }

    public void setCourseModel(String courseModel) {
        mCourseModel = courseModel;
    }

    public String getCourseTime() {
        return mCourseTime;
    }

    public void setCourseTime(String courseTime) {
        mCourseTime = courseTime;
    }

    public String getCredit() {
        return mCredit;
    }

    public void setCredit(String credit) {
        mCredit = credit;
    }

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String grade) {
        mGrade = grade;
    }

    public String getGPA() {
        return mGPA;
    }

    public void setGPA(String GPA) {
        mGPA = GPA;
    }

    @Override
    public String toString() {
        return "mTime="+mTime+","
                +"mCourseNum="+mCourseNum+","
                +"mCourseName="+mCourseName+","
                +"mCourseModel="+mCourseModel+","
                +"mCourseTime="+mCourseTime+","
                +"mCredit="+mCredit+","
                +"mGrade="+mGrade+","
                +"mGPA="+mGPA;
    }
}
