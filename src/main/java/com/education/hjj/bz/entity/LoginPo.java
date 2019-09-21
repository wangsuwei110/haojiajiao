package com.education.hjj.bz.entity;

import java.io.Serializable;

/**
 * Copyright: Copyright (c) 2019
 *
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: my
 * @date: 2019/1/30 15:12
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2019/1/30     my           v1.0.0               修改原因
 */
public class LoginPo implements Serializable {
    private Integer sid;
    /**
     * 登录名
     * 此为主题字段，不能修改
     */
    private String telephone;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

   
}
