package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class TeachLevelVo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5723349394143687936L;

	private Integer teachLevelId;
	
	private String teachLevelName;
	
	//是否被选中
	private boolean flag ;

	public Integer getTeachLevelId() {
		return teachLevelId;
	}

	public void setTeachLevelId(Integer teachLevelId) {
		this.teachLevelId = teachLevelId;
	}

	public String getTeachLevelName() {
		return teachLevelName;
	}

	public void setTeachLevelName(String teachLevelName) {
		this.teachLevelName = teachLevelName;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
