package com.education.hjj.bz.util;

import java.util.Random;

public class IdentifyCodeUtil {

	public static String getRandom() {
        int code = new Random().nextInt(899999) + 100000;
        return String.valueOf(code);
    }
}
