package com.android.chengshijian.searchplus.util;

import android.graphics.Color;

import com.android.chengshijian.searchplus.R;
import com.android.chengshijian.searchplus.app.BaseApplication;

/**
 *
 * 颜色工具类
 *
 * Created by chengshijian on 2017/3/24.
 */

public class ColorUtil {
    /**
     * 颜色模板类
     *
     * 一共21种颜色
     */
    private static int[] COLORS = {
            Color.parseColor("#33B5E5"),
            Color.parseColor("#AA66CC"),
            Color.parseColor("#99CC00"),
            Color.parseColor("#FFBB33"),
            Color.parseColor("#FF4444"),
            Color.parseColor("#CD00CD"),
            Color.rgb(207, 248, 246),
            Color.rgb(255, 140, 157),
            Color.rgb(148, 212, 212),
            Color.rgb(140, 234, 255),
            Color.rgb(136, 180, 187),
            Color.rgb(255, 208, 140),
            Color.rgb(118, 174, 175),
            Color.rgb(42, 109, 130),
            Color.rgb(255, 247, 140),
            Color.rgb(192, 255, 140),
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31),
            Color.rgb(179, 100, 53),

    };
    private static int COLOR_INDEX = 0;
    public static int colorPrimary= BaseApplication.getContextApplication().getResources().getColor(R.color.colorPrimary);
    public static int colorPrimaryDark= BaseApplication.getContextApplication().getResources().getColor(R.color.colorPrimaryDark);

    public static int getColorPrimary() {
        return colorPrimary;
    }

    public static void setColorPrimary(int colorPrimary) {
        ColorUtil.colorPrimary = colorPrimary;
    }

    public static int getColorPrimaryDark() {
        return colorPrimaryDark;
    }

    public static void setColorPrimaryDark(int colorPrimaryDark) {
        ColorUtil.colorPrimaryDark = colorPrimaryDark;
    }

    public static void initColorIndex() {
        COLOR_INDEX = 0;
    }

    public static int getColor(int index) {
        if (COLOR_INDEX >= COLORS.length) {
            COLOR_INDEX=0;
        }
        if (index >= COLORS.length) {
            return COLORS[COLOR_INDEX++];
        }
        return COLORS[index];
    }

    public static int nextColor() {
        if (COLOR_INDEX >= COLORS.length) {
            COLOR_INDEX = 0;
        }
        return COLORS[COLOR_INDEX++];
    }

    public static int getColorByResId(int id){
        return BaseApplication.getContextApplication().getResources().getColor(id);
    }
}
