package com.dfd.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成UUID字符串
 *
 * @author yy
 * @date 2023年06月28日 0028 11:27:15
 *
 */
public final class UUIDUtil {
    private UUIDUtil() {

    }

    /**
     * 生成32位UUID 即：不包含字符-的UUID
     *
     * @return
     * @param
     * @return String
     */
    public static String getUUID32Bits() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成36位UUID 即：包含字符-的UUID
     *
     * @return
     * @param
     * @return String
     */
    public static String getUUID36Bits() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成指定数目的32位uuid
     *
     * @param count
     * @return String[]
     */
    public static String[] getUUID32Bits(Integer count) {
        return getUUID(count, 32);
    }

    /**
     * 生成指定数目的36位uuid
     *
     * @param count 指定数目
     * @return String[]
     */
    public static String[] getUUID36Bits(Integer count) {
        return getUUID(count, 36);
    }

    /**
     * 生成12位uuid的一部分
     *
     * @return String[]
     */
    public static String getUUID12Bits() {
        String str = getUUID32Bits();
        return str.substring(20);
    }

    /**
     * 生成指定数目的指定位数的uuid
     *
     * @param count 指定数目
     * @param bits
     * @return String[]
     */
    private static String[] getUUID(Integer count, int bits) {
        String[] uuids = new String[count];
        for (int i = 0; i < count; i++) {
            if (bits == 32) {
                uuids[i] = getUUID32Bits();
            } else if (bits == 36) {
                uuids[i] = getUUID36Bits();
            }
        }
        return uuids;
    }

    public static String getCDToken() {
        Pattern pattern = Pattern.compile("1|a|l|v|o"); // 去掉空格符和换行符
        Matcher matcher = pattern.matcher(getUUID32Bits());
        return matcher.replaceAll("");

    }

    public static boolean checkCdToken(String token) {
        if (StringUtils.isEmpty(token))
            return false;
        Pattern pattern = Pattern.compile("1|a|l|v|o");
        return !pattern.matcher(token).find();
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(checkCdToken("ss"));
//        }
//        System.out.println(getCDToken());
        for (int i = 0; i < 100; i++) {
            System.out.println(getUUID32Bits().toUpperCase());
        }
    }
}

