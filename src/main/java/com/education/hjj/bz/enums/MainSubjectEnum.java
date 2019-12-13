package com.education.hjj.bz.enums;

public enum MainSubjectEnum {

	CHINESE(0,"语文"),
	MATH(1,"数学"),
	ENGLISH(2,"英语"),
	PHYSICS(3,"物理"),
	CHEMISTRY(4,"化学"),
	BIOLOGY(5,"生物"),
	OLYMPIAD(6,"奥数"),
	GEOGRAPHY(7,"地理"),
	HISTORY(8,"历史"),
	POLITICS(9,"政治"),
	USERIDENTITY(10,"高数"),
	;

	private Integer code;

    private String value;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	MainSubjectEnum(Integer code, String value) {
		this.value = value;
		this.code = code;
	}


}
