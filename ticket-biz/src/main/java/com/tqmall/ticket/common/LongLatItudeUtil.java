package com.tqmall.ticket.common;

import com.tqmall.ticket.biz.param.ItudeParam;

/**
 * 计算算经,纬度之间的距离
 * Created by wurenzhi
 */
public class LongLatItudeUtil {
    /**
     * 地球半径(单位:km)
     */
    private static double EARTH_RADIUS = 6378.137;

    /**
     * 计算两个用经纬度表示的点之间的距离
     *
     * @param itudeParam1
     * @param itudeParam2
     * @return
     */
    public static double getDistance(ItudeParam itudeParam1, ItudeParam itudeParam2) {
        double radLat1 = rad(itudeParam1.getLatItude());
        double radLat2 = rad(itudeParam2.getLatItude());
        double a = radLat1 - radLat2;
        double b = rad(itudeParam1.getLongItude()) - rad(itudeParam2.getLongItude());
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = (double) Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}

