package com.education.hjj.bz.util.weixinUtil.vo;

import java.io.Serializable;

public class CheckRedPackPo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6503659483026592173L;
	
	// 商户订单号
	private String mch_billno;

	public String getMch_billno() {
		return mch_billno;
	}

	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}

	
}
