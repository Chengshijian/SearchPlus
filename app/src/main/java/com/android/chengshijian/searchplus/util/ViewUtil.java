package com.android.chengshijian.searchplus.util;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.chengshijian.searchplus.app.BaseApplication;

/**
 *
 * View工具类
 *
 * Created by ChengShiJian on 2017/10/10.
 */

public class ViewUtil {
    private static StringBuffer mBuffer = new StringBuffer();
    public static void autoCompleteEmail(AutoCompleteTextView autoCompleteTextView) {

        mBuffer.delete(0, mBuffer.length());
        String text = autoCompleteTextView.getText().toString();

        if (text.indexOf("@") != -1) {
            text = text.substring(0, text.indexOf("@"));
        }

        mBuffer.append(text + "@qq.com-");
        mBuffer.append(text + "@126.com-");
        mBuffer.append(text + "@163.com-");
        mBuffer.append(text + "@115.com-");
        mBuffer.append(text + "@vip.qq.com-");
        mBuffer.append(text + "@gmail.com-");
        mBuffer.append(text + "@sina.com-");
        mBuffer.append(text + "@vip.sina.com-");
        mBuffer.append(text + "@hotmail.com-");
        mBuffer.append(text + "@yahoo.cn-");
        mBuffer.append(text + "@sohu.com-");
        mBuffer.append(text + "@foxmail.com-");
        mBuffer.append(text + "@139.com-");
        mBuffer.append(text + "@yeah.net-");
        mBuffer.append(text + "@sina.com");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BaseApplication
                .getContextApplication(), android.R.layout.simple_list_item_1, mBuffer
                .toString()
                .split("-"));

        autoCompleteTextView.setAdapter(arrayAdapter);
    }
}
