package com.tqmall.ticket.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 验证工具类
 * Created by wurenzhi
 */
public class CommonStringUtils {
    public static boolean isStringEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotStringEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            return false;
        }
        return true;
    }

    public static boolean isNull(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static Integer[] getIntItemsFromString(String srcString, String separator) {
        if (isNull(srcString) || isNull(separator)) {
            return new Integer[0];
        }
        String[] items = srcString.split(separator);
        Integer[] itemsArray = new Integer[items.length];
        for (int i = 0; i < items.length; i++) {
            itemsArray[i] = Integer.parseInt(items[i]);
        }
        return itemsArray;
    }

    private static String[] getStringItemsFromString(String srcString, String separator) {
        if (isNull(srcString) || isNull(separator)) {
            return new String[0];
        }
        String[] strings = srcString.split(separator);
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].trim();
        }
        return strings;
    }

    public static List<Integer> getIntListFromString(String srcString, String separator) {
        Integer[] intItems = getIntItemsFromString(srcString, separator);
        return Arrays.asList(intItems);
    }

    public static List<String> getStringListFromString(String srcString, String separator) {
        String[] intItems = getStringItemsFromString(srcString, separator);
        return Arrays.asList(intItems);
    }

    public static String appendList(String srcStr, List<?> list, String separator) {
        if (list == null || list.size() == 0) {
            return srcStr != null ? srcStr : "";
        }
        for (Object item : list) {
            srcStr = appendItem(srcStr, separator, item);
        }
        return srcStr != null ? srcStr : "";
    }

    public static String appendItem(String srcStr, String separator, Object item) {
        if (isNull(srcStr)) {
            srcStr = String.valueOf(item);
        } else {
            srcStr = srcStr + separator + String.valueOf(item);
        }
        return srcStr;
    }

    public static String appendList(String srcStr, List<?> list, String fieldName, String separator)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (list == null || list.size() == 0) {
            return srcStr;
        }
        String first = fieldName.substring(0, 1);
        String getterName = "get" + fieldName.replaceFirst(first, first.toUpperCase());
        Method getter = list.get(0).getClass().getMethod(getterName);
        for (Object item : list) {
            srcStr = appendItem(srcStr, separator, getter.invoke(item, (Object[]) null));
        }
        return srcStr;
    }

    /**
     * 判断手机号码
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        if (!org.apache.commons.lang3.StringUtils.isEmpty(mobile) && mobile.length() == 11) {
            Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
            Matcher m = p.matcher(mobile);
            return m.matches();
        }
        return false;
    }

    /**
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        if (!org.apache.commons.lang3.StringUtils.isEmpty(phone)) {
            Pattern p = Pattern.compile("^0\\d{2,3}-*\\d{7,8}$");
            Matcher m = p.matcher(phone);
            return m.matches();
        }
        return false;
    }


    /**
     * 是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (!org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher matcher = pat.matcher(str);
            return matcher.find();
        }
        return false;
    }

    /**
     * 验证是否含有非法字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isSpecialString(String str) throws PatternSyntaxException {

        if (!org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            String regEx = "^[0-9a-zA-Z-]+$";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.matches();
        }
        return true;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int length(String s) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(s)) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 匹配是否是全数字组成[0-9]
     *
     * @param numberString
     * @return
     */
    public static boolean isNumberOnly(String numberString) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(numberString)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(numberString);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 匹配是否数字
     * 包含正负整数小数
     * @param numberString
     * @return
     */
    public static boolean isNumeric(String numberString) {
        Pattern pattern = Pattern.compile("(^-?[1-9]\\d*$)|(^-?[1-9]\\d*\\.\\d+|-?^0\\.\\d*[1-9]\\d+$)");
        Matcher isNum = pattern.matcher(numberString);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 处理不同格式的数据
     * @param time
     * @return
     */
    public static String formatTime(String time){
        if(time.indexOf("/")>0){
            time = time.replace("/","-");
        }
        if(time.indexOf(".")>0){
            time = time.replace(".","-");
        }
        return time;
    }

}
