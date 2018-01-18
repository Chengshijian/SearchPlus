package com.android.chengshijian.searchplus.model;

import java.util.List;

/**
 * Created by ChengShiJian on 2018/1/12.
 */

public class IndCreditResult {

    private List<IndCredit> mCredits;
    private String mTotalCredit;

    public IndCreditResult() {
    }

    public IndCreditResult(List<IndCredit> credits, String totalCredit) {
        mCredits = credits;
        mTotalCredit = totalCredit;
    }

    public List<IndCredit> getCredits() {
        return mCredits;
    }

    public void setCredits(List<IndCredit> credits) {
        mCredits = credits;
    }

    public String getTotalCredit() {
        return mTotalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        mTotalCredit = totalCredit;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (IndCredit credit : mCredits) {
            builder.append(credit.toString());
        }
        return "IndCreditResult{" +
                "mCredits=" + builder.toString() +
                ", mTotalCredit='" + mTotalCredit + '\'' +
                '}';
    }
}
