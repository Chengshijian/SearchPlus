package com.android.chengshijian.searchplus.model;

/**
 * 自主学分类
 * <p>
 * Created by chengshijian on 2017/4/13.
 * Modify by chengshijian on 2018/1/12.
 */

public class IndCredit {
    private String mName;
    private String mIdTime;
    private String mCreditType;
    private String mCredit;
    private String id;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getIdTime() {
        return mIdTime;
    }

    public void setIdTime(String idTime) {
        mIdTime = idTime;
    }

    public String getCreditType() {
        return mCreditType;
    }

    public void setCreditType(String creditType) {
        mCreditType = creditType;
    }

    public String getCredit() {
        return mCredit;
    }

    public void setCredit(String credit) {
        mCredit = credit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IndCredit{" +
                "mName='" + mName + '\'' +
                ", mIdTime='" + mIdTime + '\'' +
                ", mCreditType='" + mCreditType + '\'' +
                ", mCredit='" + mCredit + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
