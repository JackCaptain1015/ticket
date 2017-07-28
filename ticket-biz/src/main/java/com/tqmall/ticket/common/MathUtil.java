package com.tqmall.ticket.common;


import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 自定义math工具类
 * Created by wurenzhi
 */
@Slf4j
public class MathUtil {
    /**
     * 四舍五入
     *
     * @param object
     * @param scale
     * @return
     */
    public static String round(Object object, int scale) {

        if (object instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) object;
            return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }

        if (object instanceof String) {
            try {
                String str = (String) object;
                BigDecimal bigDecimal = new BigDecimal(str);
                return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
        }

        if (object instanceof Double) {
            Double num = (Double) object;
            BigDecimal bigDecimal = new BigDecimal(num);
            return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }
        return null;
    }
}
