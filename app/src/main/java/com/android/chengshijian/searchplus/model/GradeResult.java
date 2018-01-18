package com.android.chengshijian.searchplus.model;

import java.util.List;

/**
 *
 * 封装成绩结果的类
 *
 * ted by ChengShiJian on 2018/1/12.
 */

public class GradeResult {
    private User mUser;
    private List<Grade> mExamAGrades;//考试课A考
    private List<Grade> mTestAGrades;//考查课A考
    private List<Grade> mExamBGrades;//考试课B考
    private List<Grade> mTestBGrades;//考查课B考
    private List<Grade> mExamRGrades;//考试课重修
    private List<Grade> mTestRGrades;//考查课重修
    private String mAverageGrade;//学业成绩

    public GradeResult() {
    }

    public GradeResult(User user, List<Grade> examAGrades, List<Grade> testAGrades, List<Grade> examBGrades, List<Grade> testBGrades, List<Grade> examRGrades, List<Grade> testRGrades, String averageGrade) {
        mUser = user;
        mExamAGrades = examAGrades;
        mTestAGrades = testAGrades;
        mExamBGrades = examBGrades;
        mTestBGrades = testBGrades;
        mExamRGrades = examRGrades;
        mTestRGrades = testRGrades;
        mAverageGrade = averageGrade;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public List<Grade> getExamAGrades() {
        return mExamAGrades;
    }

    public void setExamAGrades(List<Grade> examAGrades) {
        mExamAGrades = examAGrades;
    }

    public List<Grade> getTestAGrades() {
        return mTestAGrades;
    }

    public void setTestAGrades(List<Grade> testAGrades) {
        mTestAGrades = testAGrades;
    }

    public List<Grade> getExamBGrades() {
        return mExamBGrades;
    }

    public void setExamBGrades(List<Grade> examBGrades) {
        mExamBGrades = examBGrades;
    }

    public List<Grade> getTestBGrades() {
        return mTestBGrades;
    }

    public void setTestBGrades(List<Grade> testBGrades) {
        mTestBGrades = testBGrades;
    }

    public List<Grade> getExamRGrades() {
        return mExamRGrades;
    }

    public void setExamRGrades(List<Grade> examRGrades) {
        mExamRGrades = examRGrades;
    }

    public List<Grade> getTestRGrades() {
        return mTestRGrades;
    }

    public void setTestRGrades(List<Grade> testRGrades) {
        mTestRGrades = testRGrades;
    }

    public String getAverageGrade() {
        return mAverageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        mAverageGrade = averageGrade;
    }


    @Override
    public String toString() {
        return "Result{" +
                "mUser=" + mUser +
                ", mExamAGrades=" + mExamAGrades +
                ", mTestAGrades=" + mTestAGrades +
                ", mExamBGrades=" + mExamBGrades +
                ", mTestBGrades=" + mTestBGrades +
                ", mExamRGrades=" + mExamRGrades +
                ", mTestRGrades=" + mTestRGrades +
                ", mAverageGrade='" + mAverageGrade + '\'' +
                '}';
    }
}
