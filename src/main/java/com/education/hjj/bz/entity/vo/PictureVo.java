package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class PictureVo extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7263751961122320838L;
	
	private Integer pictureId;

	private Integer teacherId;
	
	private Integer pictureType;
	
	private String pictureUrl;
	
	private String pictureTitle;
	
	private String pictureDesc;

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
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
	
}
