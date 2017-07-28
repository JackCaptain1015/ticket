package com.tqmall.ticket.common;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 时间转化类
 * Created by wurenzhi
 */
@Slf4j
public class DateUtil {
    public static final String yyMMdd = "yyMMdd";
    /**
     * yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMAT = "yyyyMMdd";
    /**
     * HH:mm:ss
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy年MM月dd日
     */
    public static final String CN_DATE_FORMAT = "yyyy年MM月dd日";

    private static final long hourMsec = 60 * 60 * 1000;

    public static String dateToStr(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String date2Ymd(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String date2YmdChinese(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月dd日");
        return simpleDateFormat.format(date);
    }

    public static String date2YYmdChinese(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CN_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String date2Md(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(date);
    }
    public static String date2Ym(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(date);
    }

    public static String date2Y(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }

    public static String date2Hms(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleHms(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    public static String date2Hm(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String date2HmChinese(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月dd日 HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleHm(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleYmd(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleYmdHms(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleHmsSSS(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmssSSS");
        return simpleDateFormat.format(date);
    }

    public static String date2YMd(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleDhm(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd日HH:mm");
        return simpleDateFormat.format(date);
    }

    public static Date string2Date(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date string2Dates(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date string2DateSimpleYmdHms(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date string2DateTime(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取某年某月的最后一天的最后时间；如 2017年6月30日：month=6,year=2;2014年12月：month＝12，year=-1
    public static Date getYearOfMonthLastTime(Integer month, Integer year) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            String str = df.format(calendar.getTime()) + " 23:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toToday() {
        //bugfix by huang:调用地方是获取当前的记录修改时间或者创建时间,但是这个接口实际实现只获取了当天的开始时间的00:00:00;
        //此处修改为获取当前时间,要获取当天的开始时间用下面的接口:getTodayStartTime()
        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_YEAR, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date oneMinuteLater() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }

    public static boolean between(Date start, Date end) {
        if (start.after(end)) return false;
        Date today = new Date();
        return start.before(today) && end.after(today);
    }

    /**
     * start和end之间的日期，包含start和end
     * 例如：
     * start:2017/03/02 12:00:00
     *  end :2017/03/04 13:00:00
     * 返回的list为03/02、03/03、03/04
     * @param start
     * @param end
     * @return
     */
    public static List<String> betweenDays(Date start,Date end){
        if (start.after(end)) return Lists.newArrayList();

        List<String> returnBetweentDaysList = Lists.newArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CN_DATE_FORMAT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);

        returnBetweentDaysList.add( simpleDateFormat.format(start) );

        while(true){
            calendar.add(Calendar.DAY_OF_MONTH,1);
            if ( calendar.getTime().compareTo(end) == -1 || calendar.getTime().compareTo(end) == 0){
                returnBetweentDaysList.add( simpleDateFormat.format(calendar.getTime()) );
            }else{
                break;
            }
        }

        return returnBetweentDaysList;
    }

    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return (day2 - day1);
    }

    public static Date strToDate(String dateStr, String formatStr) {
        DateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (Exception e) {
            log.error("字符串转化时间出错:", e);
        }
        return date;
    }

    /**
     * 获取当天时间的前d天（-d）或者后d天的时间
     *
     * @param d       加减天数
     * @param pattern 格式化的日期格式
     * @return 满足格式的日期String
     */
    public static String getFewDaysAgoOrAfterDate(Integer d, String pattern) {
        return DateUtil.getFewDaysAgoOrAfterDate(Calendar.getInstance(), d, pattern);
    }

    /**
     * 获取当天时间的前d天（-d）或者后d天的时间
     *
     * @param cal     基准时间
     * @param d       加减天数
     * @param pattern 格式化的日期格式
     * @return 满足格式的日期String
     */
    public static String getFewDaysAgoOrAfterDate(Calendar cal, Integer d, String pattern) {
        Calendar calendar = (Calendar) cal.clone();
        calendar.add(Calendar.DATE, d);
        Date date = new Date(calendar.getTimeInMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    //获取今天是本周的第几天，注：1为星期日为第一天
    public static int getDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    //    //获取今天凌晨的时间戳
    public static Calendar getTodayBeginTimeStamp() {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return cal;
    }

    //获取明天凌晨的时间戳
    public static Integer getTomorrowBeginTimeStamp() {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE) + 1;
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static Integer getTimeStampByStr(String time) {
        return getTimeStampByStr(time, DEFAULT_DATE_TIME_FORMAT);
    }

    public static Integer getTimeStampByStr(String time, String format) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat(format).parse(time));
        } catch (Exception e) {
            log.error("转化日期出错", e);
        }
        return (int) (c.getTimeInMillis() / 1000);
    }

    public static Date long2Date(long millis) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static Integer getNowTimeStamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static Integer now() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static long timeRangeHour(Date beforeDate, Date afterDate) {

        long timeRange = afterDate.getTime() - beforeDate.getTime();

        return timeRange / hourMsec;
    }

    // 获得本周日0点时间  本周的开始时间
    public static Integer getTimesWeekStartTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.SUNDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return (int) (currentDate.getTimeInMillis() / 1000);
    }

    //获取当前时间的前后d/-d天所在周的一周的开始时间 周日0
    public static Integer getDaysOfWeekStartTime(Integer d) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, d);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //获取当前时间的前后d/-d天所在周的一周的结束时间 周六24
    public static Integer getDaysOfWeekEndTime(Integer d) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, d);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //当前日期加N天处理
    public static String addDay(Date date, int n) {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        c.setTime(date);
        c.add(Calendar.DATE, n);
        Date d2 = c.getTime();
        String s = df.format(d2);
        return s;
    }

    public static Date dateAddDay(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, n);
        return c.getTime();
    }

    public static Date dateAddMonth(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, n);
        return c.getTime();
    }

    public static Date dateAddYear(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, n);
        return c.getTime();
    }

    // 获取本周开始时间，以周日为第一天
    public static String getDateWeekStartTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.SUNDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return DateUtil.date2Ymd(currentDate.getTime());
    }

    //获得本月第一天0点时间
    public static String getTimesMonthStartTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.SUNDAY);
        currentDate.set(Calendar.DATE, 1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return DateUtil.date2Ymd(currentDate.getTime());
    }

    public static Date getMonthStartDate() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.SUNDAY);
        currentDate.set(Calendar.DATE, 1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime();
    }

    public static String getDay(long time) {
        return DateUtil.date2Ymd(new Date(time));
    }

    public static String getTime(long time) {
        return DateUtil.date2SimpleHm(new Date(time));
    }

    public static Integer getNextMonthTimeStamp() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static Integer addMonthTimeStamp(Integer addValue) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, addValue);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //判断是否为上半年， true 上半年（7月1号0点前）， false：下半年
    public static boolean isFirstHalfYear() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date limitTime = cal.getTime();
        return limitTime.compareTo(new Date()) > 0;
    }

    //获取当前时间指定n天前(-)活n天后(+)的凌晨0点时间
    public static String getStrDayTimeStart(Integer n, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(getDateDayTimeStart(n));
    }

    //获取当前时间指定n天前(-)活n天后(+)的凌晨0点时间
    public static Date getDateDayTimeStart(Integer n) {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        cal.set(y, m, d + n, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return cal.getTime();
    }


    /**
     * 凌晨
     * @param date
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     * @return
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
        }
        return cal.getTime();
    }

    /**
     * 将日期格式化为字符串
     *
     * @param date    日期
     * @param pattern 格式
     * @return 格式化后的日期串
     */
    public static String date2Str(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        if (null == pattern) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 尝试把一个String按照指定的多个pattern进行转换,转换成功返回结果,失败返回null,如果值为空直接返回null
     * @param value 需要转换为日期的字符串
     * @param patterns 日期pattern,多个以[,]分割
     * @return
     */
    public static Date tryStr2Date(String value, String patterns) {
        return tryStr2Date(value, StringUtils.split(patterns, ","));
    }

    /**
     * 尝试把一个String按照指定的多个pattern进行转换,转换成功返回结果,失败返回null,如果值为空直接返回null
     * @param value 需要转换为日期的字符串
     * @param patterns 日期pattern数组
     * @return
     */
    public static Date tryStr2Date(String value, String [] patterns) {
        Validate.notEmpty(patterns,"patterns 不能为空");
        Date d = null;
        if (StringUtils.isNotEmpty(value)) {
            for (String p : patterns) {
                try {
                    d = DateUtil.str2Date(value, p);
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return d;
    }

    /**
     * 按指定格式将字符串转换为日期
     * @param dateStr 日期串
     * @param pattern 格式
     * @return 日期
     */
    public static Date str2Date(String dateStr, String pattern){
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (null == pattern) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 为Date在字段上增加时间
     * @param time
     * @param field
     * @param value
     * @return
     */
    public static Date dateAdd(Date time,Integer field,Integer value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(field,value);
        return calendar.getTime();
    }

    /**
     * 多久之后
     * @param column 需要延迟的字段
     * @param value 需要延迟多久
     * @return
     */
    public static Date afterTime(Integer column,Integer value){
        Calendar calendar = Calendar.getInstance();
        calendar.add(column,value);
        return calendar.getTime();
    }

    /**
     * 时间差
     * @return
     */
    public static String diffTime(Date begin,Date end){
        Long diffMill = begin.getTime() - end.getTime();
        Integer day = diffMill.intValue() / (24*60*60*1000);
        Integer hour = diffMill.intValue() / (60*60*1000) - day*24;
        Integer min =( diffMill.intValue() / (60*1000) ) - day * 24*60 - hour*60;
        Integer sec = diffMill.intValue() / 1000 - day * 24*60*60 - hour*60*60 - min*60;

        String dayStr = SimpleUtil.supplementNum(day,2);
        String hourStr = SimpleUtil.supplementNum(hour,2);
        String minStr = SimpleUtil.supplementNum(min,2);
        String secStr = SimpleUtil.supplementNum(sec,2);

        StringBuffer sb = new StringBuffer();
        sb = sb.append(dayStr).append("天").append(hourStr).append("小时").
                append(minStr).append("分").append(secStr).append("秒");
        return sb.toString();
    }
}