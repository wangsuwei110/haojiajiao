package com.education.hjj.bz.enums;

public enum PonitsLog {

	OPEN_SYSTEM(1,"打开系统"),
	SURE_BEGIN_CLASS(10,"确定开讲时间"),
	SIGN_IN(5,"打卡"),
	TEACHER_CONCLUSION(5,"结课"),
	TEACHER_PROBLME_REFUND(-50,"教员问题导致的退款"),
	NEGATIVE_EVALUATE(-30,"差评"),
	COMMONLY_EVALUATE(-20,"中评");
	
 
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;
	
	private Integer type;
	
	
	
	PonitsLog(Integer type , String value) {
		this.value = value;
		this.type = type;
	}
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 获取配置文件参数名
	 * @return
	 */
	public String value() {
		return value;
	}

}
