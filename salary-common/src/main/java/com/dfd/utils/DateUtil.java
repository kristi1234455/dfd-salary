package com.dfd.utils;

import cn.hutool.core.date.DateUnit;
import com.alibaba.fastjson.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author fjt
 * @Date 2022/10/10 11:49
 * @Description 时间日期工具类
 * @Version 1.0
 **/
public class DateUtil {

    public static final String DEFAULT_FORMAT_DATETIME="yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_FORMAT="yyyyMMddHHmmss";
    public static final String DEFAULT_FORMAT_Hour="yyyy-MM-dd HH";
    public static final String DEFAULT_FORMAT_DATE="yyyy-MM-dd";
    public static final String FORMAT_DATE="yyyyMMdd";
    public static final String DEFAULT_FORMAT_MONTH="yyyy-MM";
    public static final String DEFAULT_FORMAT_TIME="HH:mm:ss";

    /**
     * 获取指定日期的前几天/后几天
     * @param appointDate
     * @param num
     * @param datePatten
     * @return
     */
    public static String getAfterDate(String appointDate, int num, String datePatten){
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(datePatten).parse(appointDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);

        String appointDay = new SimpleDateFormat(datePatten).format(calendar.getTime());
        return appointDay;
    }

    /**
     * 获取指定日期的前一天
     * @param appointDate
     * @return
     */
    public static String getBeforeDate(String appointDate, int num){
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(appointDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - num);

        String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 判断字符串是否是指定格式的日期字符
     * @param dateStr
     * @param regular
     * @return
     */
    public static Boolean isValidDate(String dateStr, String regular){
        boolean convertSuccess=true;
        SimpleDateFormat format = new SimpleDateFormat(regular);
        try {
            format.setLenient(false);
            format.parse(dateStr);
        } catch (ParseException e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }

    /**
     * 将指定格式的字符串形式的时间解析成LocalDateTime
     * @param formatDate
     * @param pattern
     * @return LocalDateTime
     */
    public static LocalDateTime parseFormatDate(String formatDate, String pattern) {
        return LocalDateTime.parse(formatDate, getFormatter(pattern));
    }

    /**
     * 获取指定格式时间
     * @param localDate
     * @param pattern
     * @return 当前时间
     */
    public static String getFormatDate(LocalDateTime localDate, String pattern) {
        return localDate.format(getFormatter(pattern));
    }

    public static String getFormatDate(LocalDate localDate, String pattern) {
        return localDate.format(getFormatter(pattern));
    }

    public static DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * 获取当前时间，yyyy-MM-dd HH:mm:ss
     */
    public static String getYmdHms() {
        DateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_DATETIME);
        return df.format(new Date());
    }

    /**
     * 获取当前时间的年月，yyyy-MM
     */
    public static String getYM() {
        DateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_MONTH);
        return df.format(new Date());
    }

    /**
     * 时间格式化，yyyy-MM-dd HH:mm:ss
     */
    public static String getYmdHms(Date date) {
        DateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_DATETIME);
        return df.format(date);
    }

    /**
     * 时间格式化，yyyy-MM-dd HH:mm:ss
     */
    public static Date getYmdHms(String date) {
        try {
            return new SimpleDateFormat(DEFAULT_FORMAT_DATETIME).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前日期，yyyy-MM-dd
     */
    public static String getYmd() {
        DateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        return df.format(new Date());
    }

    /**
     * 日期格式化，yyyy-MM-dd
     */
    public static String getYmd(Date date) {
        DateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        return df.format(date);
    }

    /**
     * 日期格式化，yyyy-MM-dd
     */
    public static Date getYmd(String date) {
        try {
            return new SimpleDateFormat(DEFAULT_FORMAT_DATE).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本月的天数
     */
    public static int getCurrentMonthDays() {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        return aCalendar.getActualMaximum(Calendar.DATE);

    }

    /**
     * 获取本月第一天，yyyy-MM-dd
     */
    public static String getCurrentMonthFirstDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取本月最后一天，yyyy-MM-dd
     */
    public static String getCurrentMonthLastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar2.getTime());
    }

    /**
     * 获取某个月的第一天，yyyy-MM-dd
     */
    public static String getThisMonthFirstDay(String month) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(DEFAULT_FORMAT_MONTH);
            SimpleDateFormat sdf2 = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf1.parse(month));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return sdf2.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取某个月的最后一天，yyyy-MM-dd
     */
    public static String getThisMOnthLastDay(String month) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(DEFAULT_FORMAT_MONTH);
            SimpleDateFormat sdf2 = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf1.parse(month));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return sdf2.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取该日期往前推多少天的日期，yyyy-MM-dd
     */
    public static String getThisDayBeforeDate(Date date, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -days);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取该日期往后推多少天的日期，yyyy-MM-dd
     */
    public static String getThisDayAfterDate(Date date, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取本周周一，yyyy-MM-dd
     */
    public static String getCurrentWeekMonday() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        return imptimeBegin;
    }

    /**
     * 获取本周周日，yyyy-MM-dd
     */
    public static String getCurrentWeekSunday() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        return imptimeEnd;
    }

    /**
     * 补全时间，开始时间
     */
    public static String completionTimeStart(String date) {
        return date + " 00:00:00";
    }

    /**
     * 补全时间，结束时间
     */
    public static String completionTimeEnd(String date) {
        return date + " 23:59:59";
    }

    /**
     * 获取两个时间的时间差
     */
    public static String getDateTimeDiffer(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        return day + "天" + hour + "时" + min + "分";
    }

    /**
     * 根据生日算年龄
     */
    private static int getAgeByBirthDay(Date birthday) {
        int age;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            //如果传入的时间，在当前时间的后面，返回0岁
            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static List<String> getBetweenDateList(String startDate,String endDate){
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try{
            long between = cn.hutool.core.date.DateUtil.between(sdf.parse(startDate), sdf.parse(endDate), DateUnit.DAY);
            for(int i=0;i<=between;i++){
                String afterDate = getAfterDate(startDate, i, DEFAULT_FORMAT_DATE);
                dateList.add(afterDate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return dateList;

    }

    public static int getCurrentSeconds() {
        return (int)cn.hutool.core.date.DateUtil.currentSeconds();
    }

    public static void main(String[] args) {
//        JSONObject object = JSONObject.parseObject("{\"beginDate\": \"20221020\"}");
        //AdUserRequestLogVo vo = AdUserRequestLogVo.builder().id("12").adId(111l).groupId(2l).actualPrice(new BigDecimal("120.59")).feature(object)
        //        .actionTime(LocalDateTime.now()).actionDate(getFormatDate(LocalDate.now(), FORMAT_DATE)).actionIp("192.168.0.1").deviceNo("258147")
        //        .build();
        //System.out.println(JSONObject.toJSONString(vo));


//        System.out.println(String.format("[%s]", "1666351243991,3"));
        /*
        String beginDate = DateUtil.getFormatDate(LocalDate.now(), DateUtil.DEFAULT_FORMAT_DATE);
        String endDate = DateUtil.getFormatDate(LocalDate.now(), DateUtil.DEFAULT_FORMAT_DATE);
        System.out.println(String.format("广告消耗记录%s至%s", beginDate, endDate));
         */

        System.out.println(getYM());
    }
}


