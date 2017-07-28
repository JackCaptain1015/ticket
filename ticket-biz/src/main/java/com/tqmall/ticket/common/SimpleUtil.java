package com.tqmall.ticket.common;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wurenzhi
 */
public class SimpleUtil {

    /**
     * 根据长度totalLen，补充位数
     * @param seq
     * @param totalLen
     * @return
     */
    public static String supplementNum(int seq, int totalLen){
        String str = seq+"";
        int len = str.length();
        if(len>=totalLen){
            return str;
        }
        int n = totalLen - len;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++){
            sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 获取随机数字符串
     * @param len
     * @return
     */
    public static String getRandomNum(int len){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<len; i++){
            sb.append((int)(Math.random()*10));
        }
        return sb.toString();
    }

    /**
     * 验证手机号
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Pattern pattern = Pattern.compile("^(13|14|15|17|18)\\d{9}$");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

}
