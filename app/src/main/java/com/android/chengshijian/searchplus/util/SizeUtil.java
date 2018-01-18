package com.android.chengshijian.searchplus.util;

import java.math.BigDecimal;

/**
 * Created by ChengShiJian on 2018/1/18.
 */

public class SizeUtil {
    public static float B2M(int kb) {
        BigDecimal decimal = new BigDecimal(kb);
        return decimal
                .divide(BigDecimal.valueOf(1024*1024))
                .setScale(2,BigDecimal.ROUND_DOWN)
                .floatValue();
    }
}
