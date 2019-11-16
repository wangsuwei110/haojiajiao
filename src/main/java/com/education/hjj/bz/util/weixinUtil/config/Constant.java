package com.education.hjj.bz.util.weixinUtil.config;

public class Constant {
	
	public static final String DOMAIN = "https://www.laijiajiaosh.com";//配置自己的域名

    public static final String APP_ID = "wx4e6192d8e025df02";

    public static final String APP_SECRET = "f6d14a0e01657fbe97632a9eb4b2a482";

    public static final String APP_KEY = "39b19568f1724e3eb6f05fb0026421e2";

    public static final String MCH_ID = "1557492101";  //商户号

    public static final String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String URL_NOTIFY = Constant.DOMAIN + "/wxpay/wxNotify";

    public static final String TIME_FORMAT = "yyyyMMddHHmmss";

    public static final int TIME_EXPIRE = 2;  //单位是day
    
    public static final String  SIGN_TYPE= "MD5";
}

