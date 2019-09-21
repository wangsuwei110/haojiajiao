package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class PointsLogForm extends PageForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -483032926664294757L;

	private Integer teacherId;

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

}
