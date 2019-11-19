package com.education.hjj.bz.util.weixinUtil.vo;

import java.io.Serializable;

public class RedpackResponsePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7492018716319700108L;
	
	//商户订单号
	private String mchBillno;
	//商户号
	private String mchId;
	//公众账号appid
	private String wxappid;
	//用户openid
	private String reOpenid;
	//付款金额
	private String totalAmount;
	//微信单号
	private String sendListid;

	
	public String getMchBillno() {
		return mchBillno;
	}
	public void setMchBillno(String mchBillno) {
		this.mchBillno = mchBillno;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getWxappid() {
		return wxappid;
	}
	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}
	public String getReOpenid() {
		return reOpenid;
	}
	public void setReOpenid(String reOpenid) {
		this.reOpenid = reOpenid;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSendListid() {
		return sendListid;
	}
	public void setSendListid(String sendListid) {
		this.sendListid = sendListid;
	}
	
	
	
}
