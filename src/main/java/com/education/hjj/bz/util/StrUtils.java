package com.education.hjj.bz.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

public class StrUtils {

    public static final String UTF_8 = "UTF-8";
    
    /**
     * 去掉小数字符串后面无用的零
     */
    public static String replaceTheTailZero(String value){
        if(StringUtils.isEmpty(value)){
            return value;
        }        
        if(value.indexOf(".") > 0){
            value =value.replaceAll("0+?$", "");//去掉后面无用的零
            value =value.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return value;
    }
    
    /**
     * UTF-8字符长度换算
     */
    public static int getStrByteLen(String value) throws UnsupportedEncodingException{
        if(StringUtils.isEmpty(value)){
            return 0;
        }
        return value.getBytes(UTF_8).length;
    }

    /**
     * 验证是否为空
     */
    public static boolean isEmpty(String value) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        if (StringUtils.trim(value).length() == 0) {
            return true;
        }
        return false;
    }
}
