package com.android.chengshijian.searchplus.util;

import com.android.chengshijian.searchplus.listener.OnSelectListener;

import java.util.Calendar;

/**
 * Created by ChengShiJian on 2018/1/13.
 */

public class SelectUtil {

    public static void select(OnSelectListener listener){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month >= 1 && month <= 5 || month == 12) {
            listener.onNextTerm();
        } else {
            listener.onFirstTerm();
        }
    }
}
