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
    
    
    //红包发放接口
    public static final String SEND_RED_PACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendminiprogramhb";
    
    public static final String SEND_CASH_RED_PACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
    
    //红包记录查看接口
    public static final String CHECK_RED_PACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";
    
    //证书存放位置
//    public static final String CERT_PATH ="D:\\1557492101_20191115_cert\\apiclient_cert.p12";
    
    public static final String CERT_PATH ="/opt/haojiajiao/cert/";
    //活动描述
    public static final String ACT_NAME = "来家教提现红包";
    //通知用户形式，小程序固定用 MINI_PROGRAM_JSAPI
    public static final String NOTIFY_WAY = "MINI_PROGRAM_JSAPI";
    //发红包的商户名称
    public static final String SEND_NAME ="上海序才教育";
    //红包祝福语
    public static final String WISHING = "感谢您使用来家教,祝你生活愉快!";
    //订单类型 , MCHT:通过商户订单号获取红包信息
    public static final String BILL_TYPE = "MCHT";
}

