package com.education.hjj.bz.enums;

public enum RedPackErrorEnum {

	NO_AUTH("NO_AUTH","用户账号异常，请联系客服,使用常用的活跃的微信号可避免这种情况"),
	SENDNUM_LIMIT("SENDNUM_LIMIT","今日领取已达上限，请明天再试,该用户今日领取红包个数超过你在微信支付商户平台配置的上限"),
	ILLEGAL_APPID("ILLEGAL_APPID","传入了非法app的appid，请确认是否为公众号的appid，不能为APP的appid"),
	MONEY_LIMIT("MONEY_LIMIT","发送红包金额不再限制范围内,每个红包金额必须在默认额度内（默认大于1元，小于200元，可在产品设置中自行申请调整额度）"),
	SEND_FAILED("SEND_FAILED","该红包已经发放失败,如果需要重新发放，请更换单号再发放"),
	FATAL_ERROR("FATAL_ERROR","更换了openid，但商户单号未更新,或更换了金额，但商户单号未更新"),
	CA_ERROR("CA_ERROR","请求携带的证书出错,CA证书出错，请登录微信支付商户平台下载证书,然后请求带上证书后重试"),
	SIGN_ERROR("SIGN_ERROR","签名错误,1. 到商户平台重新设置新的密钥后重试\r\n" + 
			"2. 检查请求参数把空格去掉重试\r\n" + 
			"3. 中文不需要进行encode，使用CDATA\r\n" + 
			"4. 按文档要求生成签名后再重试\r\n" + 
			"在线签名验证工具：http://mch.weixin.qq.com/wiki/tools/signverify/"),
	SYSTEMERROR("SYSTEMERROR","请求已受理，请稍后使用原单号查询发放结果"),
	XML_ERROR("XML_ERROR","请求的xml格式错误，或者post的数据为空"),
	FREQ_LIMIT("FREQ_LIMIT","超过频率限制,请稍后再试"),
	API_METHOD_CLOSED("API_METHOD_CLOSED","商户API发放方式处于关闭状态，请联系管理员在商户平台开启"),
	NOTENOUGH("NOTENOUGH","账户余额不足,请到商户平台充值后再重试"),
	OPENID_ERROR("OPENID_ERROR","openid和appid不匹配"),
	MSGAPPID_ERROR("MSGAPPID_ERROR","msgappid与主、子商户号的绑定关系校验失败,检查下msgappid是否填写错误"),
	ACCEPTMODE_ERROR("ACCEPTMODE_ERROR","服务商模式下主商户号与子商户号关系校验失败"),
	PROCESSING("PROCESSING","请求已受理，请稍后使用原单号查询发放结果"),
	PARAM_ERROR("PARAM_ERROR","参数输入有误，请检查！"),
	SENDAMOUNT_LIMIT("SENDAMOUNT_LIMIT","商户今日发放的总金额超过您在微信支付商户平台配置的上限,如有需要，请联系管理员在商户平台上调整单日发送金额上限。"),
	RCVDAMOUNT_LIMIT("RCVDAMOUNT_LIMIT","该用户今日领取红包总金额超过您在微信支付商户平台配置的上限");
	
 
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;
	
	private String type;
	
	
	
	RedPackErrorEnum(String type , String value) {
		this.value = value;
		this.type = type;
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 红包场景
	 * @return
	 */
	public String value() {
		return value;
	}

}
