package com.android.chengshijian.searchplus.model;

import java.util.List;

/**
 *
 * 封装成绩单列表的类
 *
 * Created by ChengShiJian on 2018/1/12.
 */

public class GradeListResult {
    private List<GradeList> mGradeLists;
    private GradeListTitle mTitle;

    public GradeListResult(List<GradeList> gradeLists, GradeListTitle title) {
        mGradeLists = gradeLists;
        mTitle = title;
    }

    public GradeListResult() {
    }

    public List<GradeList> getGradeLists() {
        return mGradeLists;
    }

    public void setGradeLists(List<GradeList> gradeLists) {
        mGradeLists = gradeLists;
    }

    public GradeListTitle getTitle() {
        return mTitle;
    }

    public void setTitle(GradeListTitle title) {
        mTitle = title;
    }
}
