package com.education.hjj.bz.util;

public class Constant {

	public static class ErrorCode {
		 
        /**
         * 无效参数
         */
        public static Integer INVALID_PARAM_CODE = -101;
        public static String INVALID_PARAM_MSG = "无效参数";
 
        /**
         * 没有权限
         */
        public static Integer PERMISSION_DENIED_CODE = -102;
        public static String PERMISSION_DENIED_MSG = "没有权限";
 
        /**
         * 通用错误
         */
        public static Integer COMMON_ERROR_CODE = -103;
        public static String COMMON_ERROR_MSG = "服务器繁忙，请稍后再试";
 
        /**
         * 登录失效
         */
        public static Integer INVALID_LOGIN_CODE = -104;
        public static String INVALID_LOGIN_MSG = "登录失效";
 
        /**
         * 数据库操作失败
         */
        public static Integer DATABASE_OPERATION_ERROR_CODE = -105;
        public static String DATABASE_OPERATION_ERROR_MSG = "数据库操作失败";
 
        /**
         * token失效
         */
        public static Integer INVALID_TOKEN_CODE = -106;
        public static String INVALID_TOKEN_MSG = "用户未登录或登录信息已失效";
        
        /**
         * 用户名或密码不正确
         */
        public static Integer INVALID_USERNAME_PASSWORD_CODE = -107;
        public static String INVALID_USERNAME_PASSWORD_MSG = "用户未登录或登录信息已失效";
 
        /**
         * 服务器异常
         */
        public static Integer SERVER_ERROR_CODE = -200;
        public static String SERVER_ERROR_MSG = "服务器异常";
 
    }
	
	
		
	public static final String SMS_LOGIN_IDENTIFY_CODE="smsLoginIdentifyCode";
	
	public static final String SMS_SEND_STATUS_OK="OK";
	
	public static final Integer SUCCESS =200;
	
	public static final Integer FAILED =500;
	
	// 设置token名
    public static final String TOKEN = "Authorization";
    // 设置token状态名
    public static final String TOKEN_STATE = "state";
    // token 过期标识
    public static final String EXT = "ext";
    // 用户唯一标识
    public static final String USERID = "userId";
    public static final String PASSWORD = "password";
    // 登录认证的缓存名
    public static final String authenticationCache = "authenticationCache";

    // 授权认证的缓存名
    public static final String authorizationCache = "authorizationCache";

    //登录认证前缀
    public static final String DEFAULT_CACHE_KEY_PREFIX = "network:cache:";

    /**
     * wx登录认证的缓存名
     * */
    public static final String wxAuthenticationCache = "wxAuthenticationCache";

    // 权限分隔符
    public static final String PERMIT_SPLIT = "/";
    
    /**
     * redis-OK
     */
    public final static String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public final static int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public final static int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public final static int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;
    
    //AES密码加密私钥(Base64加密)
    public static final String ENCRYPTAESKEY="V2FuZzkyNjQ1NGRTQkFQSUpXVA==";
    //JWT认证加密私钥(Base64加密)
    public static final String ENCRYPTJWTKEY="U0JBUElKV1RkV2FuZzkyNjQ1NA==";
    //AccessToken过期时间-30分钟-30*60(秒为单位)
    public static final Integer ACCESSTOKENEXPIRETIME=2592000;
    //RefreshToken过期时间-35分钟-30*60(秒为单位)
    public static final Integer REFRESHTOKENEXPIRETIME=2592000;
    //Shiro缓存过期时间-30分钟-30*60(秒为单位)(一般设置与AccessToken过期时间一致)
    public static final Integer SHIROCACHEEXPIRETIME=2592000;
    
    //学生
    public static final Integer STUDENT_CODE = 1;
    public static final String STUDENT_NAME = "student";
    //教员
    public static final Integer TEACHER_CODE = 2;
    public static final String TEACHER_NAME = "teacher";
    //超级管理员
    public static final Integer ADMIN_CODE = 3;
    public static final String ADMIN_NAME = "admin";
    //审核员
    public static final Integer AUDIT_CODE = 4;
    public static final String AUDIT_NAME = "audit";
    
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/jscode2session";
}
