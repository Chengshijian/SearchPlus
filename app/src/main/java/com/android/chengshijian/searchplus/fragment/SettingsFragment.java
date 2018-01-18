package com.android.chengshijian.searchplus.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.util.DataUtil;
import com.android.chengshijian.searchplus.util.TimeUtil;
import com.android.chengshijian.searchplus.view.IFragmentView;

/**
 * 设置类
 * <p>
 * Created by ChengShiJian on 2018/1/14.
 */

public class SettingsFragment extends PreferenceFragment implements IFragmentView, Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private SwitchPreference mShowNameSp;
    private SwitchPreference mSaveDataSp;
    private ListPreference mSelectDurationLp;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {
        mShowNameSp = (SwitchPreference) findPreference(getString(R.string.show_user_name));
        mSaveDataSp = (SwitchPreference) findPreference(getString(R.string.save_data));
        mSelectDurationLp = (ListPreference) findPreference(getString(R.string.select_duration));
    }

    @Override
    public void initData() {
        initShowUserNameSp();
        initSaveDataSp();
        initSelectDurationSp();
    }

    private void initSelectDurationSp() {
        if (TimeUtil.isDurationEquals(R.string.per)) {
            mSelectDurationLp.setSummary(R.string.per);
        } else if (TimeUtil.isDurationEquals(R.string.ten_minute)) {
            mSelectDurationLp.setSummary(R.string.ten_minute);
        } else if (TimeUtil.isDurationEquals(R.string.twenty_minute)) {
            mSelectDurationLp.setSummary(R.string.twenty_minute);
        } else if (TimeUtil.isDurationEquals(R.string.thirty_minute)) {
            mSelectDurationLp.setSummary(R.string.thirty_minute);
        } else if (TimeUtil.isDurationEquals(R.string.one_hour)) {
            mSelectDurationLp.setSummary(R.string.one_hour);
        } else if (TimeUtil.isDurationEquals(R.string.two_hour)) {
            mSelectDurationLp.setSummary(R.string.two_hour);
        } else if (TimeUtil.isDurationEquals(R.string.three_hour)) {
            mSelectDurationLp.setSummary(R.string.three_hour);
        } else {
            mSelectDurationLp.setSummary(R.string.thirty_minute);//默认30分钟
        }
    }

    private void initSaveDataSp() {
        if (DataUtil.isSaveData()) {
            mSaveDataSp.setChecked(true);
        } else {
            mSaveDataSp.setChecked(false);
        }
    }

    private void initShowUserNameSp() {
        if (DataUtil.isShowUserName()) {
            mShowNameSp.setChecked(true);
        } else {
            mShowNameSp.setChecked(false);
        }
    }

    @Override
    public void initListener() {
        mShowNameSp.setOnPreferenceClickListener(this);
        mSaveDataSp.setOnPreferenceClickListener(this);
        mSelectDurationLp.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getOrder()) {
            case 0:
                changeShowUserName();
                return true;
            case 1:
                changeSaveData();
                return true;
        }
        return false;
    }

    private void changeSaveData() {
        com.orhanobut.logger.Logger.i("" + mSaveDataSp.isChecked());
        if (mSaveDataSp.isChecked()) {
            DataUtil.setSaveData(true);
        } else {
            DataUtil.setSaveData(false);
        }
    }

    private void changeShowUserName() {
        if (mShowNameSp.isChecked()) {
            DataUtil.setShowUserName(true);
        } else {
            DataUtil.setShowUserName(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == mSelectDurationLp) {
            mSelectDurationLp.setSummary((String) o);
            DataUtil.setSelectDuration((String) o);
            return true;
        }
        return false;
    }
}