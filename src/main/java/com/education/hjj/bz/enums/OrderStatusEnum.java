package com.education.hjj.bz.enums;

public enum OrderStatusEnum {

	NEWORDER(0,"新需求订单"),
	SIGNUP(1,"已报名，耐心等待报名结果"),
	CHOOSE(2,"已选中，尽快确认试讲时间"),
    TRIAL(3,"已确认试讲时间"),
    TRIALPASS(4,"试讲通过"),
    TRIALLOST(5,"试讲不通过"),
    UNPAY(6,"未支付订单"),
    PAY(7,"已支付订单");
	
	private Integer status;

    private String msg;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	OrderStatusEnum(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
	}
    
    
}
