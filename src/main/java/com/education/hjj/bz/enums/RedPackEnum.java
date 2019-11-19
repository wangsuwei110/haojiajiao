package com.education.hjj.bz.enums;

public enum RedPackEnum {

	PRODUCT_1(1,"PRODUCT_1"),
	PRODUCT_2(2,"PRODUCT_2"),
	PRODUCT_3(3,"PRODUCT_3"),
	PRODUCT_4(4,"PRODUCT_4"),
	PRODUCT_5(5,"PRODUCT_5"),
	PRODUCT_6(6,"PRODUCT_6"),
	PRODUCT_7(7,"PRODUCT_7"),
	PRODUCT_8(8,"PRODUCT_8");
	
 
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;
	
	private Integer type;
	
	
	
	RedPackEnum(Integer type , String value) {
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
	 * 红包场景
	 * @return
	 */
	public String value() {
		return value;
	}

}
