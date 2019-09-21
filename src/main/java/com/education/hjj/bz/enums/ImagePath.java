package com.education.hjj.bz.enums;

public enum ImagePath {

	HEAD_PICTURE(1,"D:\\haojiajiao\\share\\IMG\\headPicture"),
	IDCARD_PICTURE(2,"D:\\haojiajiao\\share\\IMG\\IDCardPicture"),
	DEGREE_PICTURE(3,"D:\\haojiajiao\\share\\IMG\\degreePicture"),
	CERTIFICATE_PICTURE(4,"D:\\haojiajiao\\share\\IMG\\certificatePicture"),
	EXPERIENCE_PICTURE(5,"D:\\haojiajiao\\share\\IMG\\experiencePicture"),
	COMPLAINT_SUGGESTION(6,"D:\\haojiajiao\\share\\IMG\\complaintSuggestion");
	public String getValue() {
		return value;
	}
 
	public void setValue(String value) {
		this.value = value;
	}
 
	private String value;
	
	private Integer type;
	
	
	
	ImagePath(Integer type , String value) {
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
