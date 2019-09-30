package com.education.hjj.bz.enums;

public enum ErrorEnum {

	 /**
     * 系统级错误
     */
    SUC_MSG("200", "操作成功"),// 不可更改
    SYS_MSG("0001", "未知错误,请联系管理员"),// 不可更改 : 系统错误
    PARAM_MSG("300", "参数错误"),
    DAO_MSG("0003", "数据异常"),
    SERVICE_MSG("0004", "服务异常"),
    WEB_MSG("0005", "网络异常,请重试"),
    WEB_REPEAT_SUBMIT("0006", "重复提交"),
    INVALID_REQUEST("0007", "非法请求"),
    NET_NOT_STABLE("0008", "网络不稳定，请稍后再试"),
    REQUEST_MSG("0010", "请求头中不包含认证信息token"),
    REDIS_ERR_MSG("0011", "缓存异常，请检查Redis服务"),
    DATA_ERR_MSG("0012", "存在数据不符合"),


    NOT_LOGIN("02001", "对不起，您未登录或已退出登录"),
    USER_NOT_EXIST("02002", "用户不存在"),
    USER_IS_EXIST("02003", "用户已存在"),
    USER_PWD_ERROR("02004", "登录名或密码错误"),
    NO_PERMIT("02009", "该用户无权访问"),
    LOCKED_USER("02010", "用户已被锁定，请%s分钟之后再操作。"),
    USER_UNAUTHORIZED("02011", "暂无权限"),
    USER_LOGOUT("02012", "登出成功"),


    //-------------smsboss----------start
    SMS400("400", "客户端错误: 请求的路径不存在"),
    SMS404("404", "客户端错误: 请求的资源未找到 , 比如设备, 组织等"),
    SMS405("405", "客户端错误: 使用的http方法不支持, 只能用POST"),
    SMS440("440", "客户端错误: 缺乏必须的参数"),
    SMS441("441", "客户端错误: 参数类型不匹配"),
    SMS442("442", "客户端错误: 未设置appid"),
    SMS443("443", "客户端错误: 无效的appid"),
    SMS444("444", "客户端错误: 未设置签名参数"),
    SMS445("445", "客户端错误: 签名不正确"),
    SMS446("446", "客户端错误: 未设置时间戳"),
    SMS447("447", "客户端错误: 时间戳格式不正确, 必须是unix时间戳, 单位为毫秒"),
    SMS448("448", "客户端错误: 时间戳时间和服务器时间差别过大, 请同步客户端的时间"),
    SMS449("449", "客户端错误: 不能传递secret 参数"),
    SMS450("450", "客户端错误: 该ip不在白名单范围内, 禁止访问"),
    SMS451("451", "客户端错误: 访问太频繁, 被系统流控"),
    SMS452("452", "客户端错误: 没有开通相关功能权限"),
    SMS453("453", "客户端错误: 参数值超出限制"),
    SMS454("454", "参数长度过长"),
    SMS510("510", "服务端错误: 未知系统异常"),
    SMS511("511", "服务端错误: 请求超时"),
    SMS512("512", "服务端错误: 卡续费失败"),
    SMS513("513", "运营商接口返回失败"),
    SMS600("600", "余额不足"),
    SMS603("603", "批量查询一次不能超过限制"),
    SMS604("604", "移动卡每月25号之后无法充值"),
    SMS605("605", "不支持的功能"),
    SMS606("606", "重复提交"),
    SMS607("607", "无法给指定的套餐续费"),
    SMS608("608", "数据还未生成好，请稍后重试"),
    SMS609("609", "设备类型不正确"),
    SMS610("610", "重复注册信息不一致"),
    SMS611("611", "参数month错误，month必须是rechargeUnit的整数倍"),
    SMS612("612", "超过套餐的续费数上限"),
    //-------------smsboss----------end
    ;

    private String code;
    private String msg;

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsg(String code) {
        ErrorEnum[] values = ErrorEnum.values();

        for (ErrorEnum errorEnum : values) {
            if (errorEnum.getCode().equals(code)) {
                return errorEnum.getMsg();
            }
        }

        return null;
    }
}
