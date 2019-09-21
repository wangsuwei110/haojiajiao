package com.education.hjj.bz.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.education.hjj.bz.model.valid.group.UserEditValidGroup;
import com.education.hjj.bz.model.valid.group.UserLoginValidGroup;

/**
 *
 * @author dolyw.com
 * @date 2018/8/30 10:34
 */
public class UserDto implements Serializable{
    /**
     * 登录时间
     */
    private Date loginTime;
    
    private static final long serialVersionUID = 3342723124953988435L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 帐号
     */
    @NotNull(message = "帐号不能为空", groups = { UserLoginValidGroup.class, UserEditValidGroup.class })
    private String account;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空", groups = { UserLoginValidGroup.class, UserEditValidGroup.class })
    private String password;
    
    /**
     * 昵称
     */
    @NotNull(message = "用户名不能为空", groups = { UserEditValidGroup.class })
    private String username;

    /**
     * 注册时间
     */
    private Date regTime;
    
    /**
     * 是否是在职人员
     */
    private Integer status;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取帐号
     *
     * @return account - 帐号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置帐号
     *
     * @param account 帐号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 获取昵称
     *
     * @return username - 昵称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置昵称
     *
     * @param username 昵称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取注册时间
     *
     * @return reg_time - 注册时间
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * 设置注册时间
     *
     * @param regTime 注册时间
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
}
