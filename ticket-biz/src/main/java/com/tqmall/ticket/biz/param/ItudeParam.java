package com.tqmall.ticket.biz.param;

/**
 * 纬度,经度
 * Created by wurenzhi
 */


public class ItudeParam {
    //纬度
    private double latItude;
    //经度
    private double longItude;

    public ItudeParam() {
    }

    public ItudeParam(double latItude, double longItude) {
        this.latItude = latItude;
        this.longItude = longItude;
    }

    public double getLongItude() {
        return longItude;
    }

    public void setLongItude(double longItude) {
        this.longItude = longItude;
    }

    public double getLatItude() {
        return latItude;
    }

    public void setLatItude(double latItude) {
        this.latItude = latItude;
    }


}