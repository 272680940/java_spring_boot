package com.hqyj.java_spring_boot.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

public class MD5Utils {
    private static final String SALT = "&%5123***&&%%$$#@";

    public static String getMD5(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String base = str + "/" + SALT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
