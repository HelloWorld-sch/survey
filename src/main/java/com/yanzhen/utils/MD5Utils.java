package com.yanzhen.utils;

import org.springframework.util.DigestUtils;

//密码加密
public class MD5Utils {

    //盐
    private static final String salt = "Survey###$$@@";

    public static String getMD5(String string){
        String val = string+salt;
        return DigestUtils.md5DigestAsHex(val.getBytes());
    }


    public static void main(String[] args) {
        System.out.println(getMD5("123456"));
    }


}
