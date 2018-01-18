package com.android.chengshijian.searchplus.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 封装讲座学期结果的类
 *
 * Created by ChengShiJian on 2018/1/13.
 */

public class LectureTermResult {
    private List<String> mTermValues;
    private List<String> mTermKeys;
    private LinkedHashMap<String, String> mTermsMap;

    public LectureTermResult(LinkedHashMap<String, String> termsMap) {
        mTermsMap = termsMap;
        mTermKeys = new ArrayList<>();
        mTermValues = new ArrayList<>();

        for (Map.Entry<String, String> entry : termsMap.entrySet()) {
            mTermKeys.add(entry.getKey());
            mTermValues.add(entry.getValue());
        }
    }

    public List<String> getTermKeys() {
        return mTermKeys;
    }

    public void setTermKeys(List<String> termKeys) {
        mTermKeys = termKeys;
    }

    public List<String> getTermValues() {
        return mTermValues;
    }

    public void setTermValues(List<String> termValues) {
        mTermValues = termValues;
    }

    public LinkedHashMap<String, String> getTermsMap() {
        return mTermsMap;
    }

    public void setTermsMap(LinkedHashMap<String, String> termsMap) {
        mTermsMap = termsMap;
    }

    @Override
    public String toString() {
        return "LectureTermResult{" +
                "mTermValues=" + mTermValues +
                ", mTermKeys=" + mTermKeys +
                ", mTermsMap=" + mTermsMap +
                '}';
    }
}
