package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class PictureForm extends PageForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8249936292057329431L;

	private String teacherId;
	
	private Integer pictureId;

	private Integer pictureType;

	private String pictureUrl;

	private String pictureTitle;

	private String pictureDesc;

	private Integer studentId;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getPictureType() {
		return pictureType;
	}

	public void setPictureType(Integer pictureType) {
		this.pictureType = pictureType;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getPictureTitle() {
		return pictureTitle;
	}

	public void setPictureTitle(String pictureTitle) {
		this.pictureTitle = pictureTitle;
	}

	public String getPictureDesc() {
		return pictureDesc;
	}

	public void setPictureDesc(String pictureDesc) {
		this.pictureDesc = pictureDesc;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

}
