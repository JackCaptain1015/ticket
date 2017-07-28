package com.tqmall.ticket.common;

/**
 * 数字验证码生成器
 * Created by wurenzhi
 */
public class VerifyGenerator {

    public static String genVerify(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<4; i++){
            sb.append((int)(Math.random()*10));
        }
        return sb.toString();
    }

}
