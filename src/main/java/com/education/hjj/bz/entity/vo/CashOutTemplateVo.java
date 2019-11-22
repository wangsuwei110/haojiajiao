package com.education.hjj.bz.entity.vo;

public class CashOutTemplateVo {

	// 字段值例如：keyword1：订单类型，keyword2：下单金额，keyword3：配送地址，keyword4：取件地址，keyword5备注

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CashOutTemplateVo(String value) {
		this.value = value;
	}

	public CashOutTemplateVo() {

	}
}
