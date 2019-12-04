package com.education.hjj.bz.util.weixinUtil.config;

public class Constant {
	
	public static final String DOMAIN = "https://www.laijiajiaosh.com";//配置自己的域名

    public static final String APP_ID = "wx4e6192d8e025df02";//小程序的appId
    
    public static final String COMMON_APP_ID = "wxe2b727fd65f6be62";//公众号的appId

    public static final String APP_SECRET = "f6d14a0e01657fbe97632a9eb4b2a482";

    public static final String APP_KEY = "39b19568f1724e3eb6f05fb0026421e2";

    public static final String MCH_ID = "1557492101";  //商户号

    public static final String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String URL_NOTIFY = "https://www.laijiajiaosh.com/wxpay/wxNotify";

    public static final String TIME_FORMAT = "yyyyMMddHHmmss";

    public static final int TIME_EXPIRE = 2;  //单位是day
    
    public static final String  SIGN_TYPE= "MD5";
    
    
    //红包发放接口
    public static final String SEND_RED_PACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendminiprogramhb";
    
    public static final String SEND_CASH_RED_PACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
    
    //红包记录查看接口
    public static final String CHECK_RED_PACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";
    
    //提现到零钱(企业付款到零钱)
    public static final String PAY_CASH = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    //查询企业付款到零钱
    public static final String CHECK_PAY_CASH ="https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
    
    //证书存放位置
//    public static final String CERT_PATH ="D:\\1557492101_20191115_cert\\apiclient_cert.p12";
    
    public static final String CERT_PATH ="/opt/haojiajiao/cert/apiclient_cert.p12";
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
    //微信模板
    public static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
    
    //微信统一服务消息模板
    public static final String SEND_WX_COMMON_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=";
    
    //发送微信订阅消息
    public static final String SEND_WX_SUBSCRIBE_MESSAGE =  "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";
    //组合模板并添加至帐号下的个人模板库
    public static final String ADD_WX_TEMPLATE_SUBSCRIBE_MESSAGE = "https://api.weixin.qq.com/wxaapi/newtmpl/addtemplate?access_token=";
    //组合模板并添加至帐号下的个人模板库
    public static final String DELETE_WX_TEMPLATE_SUBSCRIBE_MESSAGE = "https://api.weixin.qq.com/wxaapi/newtmpl/deltemplate?access_token=";
    //获取当前帐号下的个人模板列表
    public static final String GET_ALL_WX_TEMPLATE_SUBSCRIBE_MESSAGE = "https://api.weixin.qq.com/wxaapi/newtmpl/gettemplate?access_token=";
    
    /**获取access_token*/
    public static final String ACCESS_TOKEN  = "https://api.weixin.qq.com/cgi-bin/token";
    
    
    
    
    //提现申请通知
    public static final String CASH_OUT_MESSAGE = "chWkfUp8KoaOaZdplI_DVfZE1hOkYZh-tYBUuKHnu8k";
    
    
    //提现申请通知(小程序)
    public static final String CASH_OUT_TO_ACCOUNT_MESSAGE = "chWkfUp8KoaOaZdplI_DVfZE1hOkYZh-tYBUuKHnu8k";
    //提现到账通知(公众号)
    public static final String COMMON_CASH_OUT_TO_ACCOUNT_MESSAGE = "EpofyFiObypjbkrWnVtyyxhy8Gw02RrSs-G5fCIWARo";
    
    //支付通知(小程序)
    public static final String PAYMENT_SUCCESS_MESSAGE = "I1-wAFCoM_VPlpIGCagwEvXEv7bCCf--52EoIU39aK0";
    //支付通知(公众号)
    public static final String COMMON_PAYMENT_SUCCESS_MESSAGE = "vBLV5D0zQNq4mZNYIvQ8xo9oqDvnJlwvMYEWEw1atRc";
    
    
    //课程已被购买通知
    public static final String PAY_FOR_CLASS = "hWYWzWwSHDOwVP19CkH-gxpihW4sESgMWf7kwcPvlbE";
    //课程将开始通知
    public static final String BEGIN_CLASS = "NDvFtNBsjv6WloQIybsQ5VmEx8bd1UwB8GprfprxNhM";
    //服务时间确认通知
    public static final String SURE_SERVICE_TIME = "8afIi4QrBF9yCcZ4LUN7n9vGQRMLJ289TjQIU2fEXsQ";
    
    
    /**
     * 微信订阅消息模板
     */
    //上课提醒 "课程名称:{{thing1.DATA}}\n上课时间:{{time5.DATA}}\n上课地点:{{thing6.DATA}}\n上课教员:{{name12.DATA}}\n上课学生:{{name10.DATA}}\n"
    public static final String CLASS_BEGIN_MESSAGE = "tIJJCwfwdGv-mNCU60HetaLFaADvwWX3So0yNeRBOVM";
    
  //提现结果通知  "金额:{{amount1.DATA}}\n收款地址:{{thing3.DATA}}\n到账时间:{{date4.DATA}}\n"
    public static final String CASHOUT_MESSAGE = "UPhBQDD3ckPKFhoDLHuKwDwTRV0YTZkqyZo9ewszwQI";
    
  //授课邀请提醒  "授课内容:{{thing1.DATA}}\n温馨提示:{{thing2.DATA}}\n"
    public static final String CLASS_CONTENT_MESSAGE = "rgndJEZd9POlvDVvBnI2ChxfYYu3xbtwISR7NCVzlNc";
    
  //授课预约成功通知 "授课老师:{{name1.DATA}}\n授课时间:{{date3.DATA}}\n课程内容:{{thing4.DATA}}\n"
    public static final String CLASS_SUBSCRIBE_MESSAGE = "KB10ybjYSdInA9O9sobfhwr6yXxYMIpQ8cBIR0stX2o";
    
  //课表更新通知  "温馨提示:{{thing1.DATA}}\n更新时间:{{time2.DATA}}\n"
    public static final String SCHOOL_TIMETABLE_CHANGE_MESSAGE = "tihkE1aBHVUVz2nkZMHQiBJ-e8LcRKyZhhEpX0VTpuE";
    
  //注册审核提醒 "申请人:{{name1.DATA}}\n申请日期:{{date2.DATA}}\n"
    public static final String AUDIT_REGIST_MESSAGE = "3V-yzSi54QC4_3Wk6Ra106RXJFnV14qlz47ol6bE7tI";
    
  //购买成功通知 "课程名称:{{thing2.DATA}}\n订单金额:{{amount3.DATA}}\n购买时间:{{date4.DATA}}\n"
    public static final String BUY_CLASS_SUCCESS_MESSAGE = "3x11joEYp8Gk7Jl_kEFjLFZ0gg1U7FwFGencGDW_hXY";
    
  //注册审核结果通知 "审核结果:{{thing1.DATA}}\n时间:{{date2.DATA}}\n备注:{{thing3.DATA}}\n"
    public static final String AUDIT_REGIST_RESULT_MESSAGE = "1DIuaSZQTzvHEtIPLgzzSAYqKVZAlJHmuF2h_qORl3c";
}

