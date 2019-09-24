package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class StudentDetailForm extends PageForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8694093635600221795L;

	private Long sid;

	private Long parentId;

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
