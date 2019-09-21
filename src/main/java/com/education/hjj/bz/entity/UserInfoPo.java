package com.education.hjj.bz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by my on 2018/11/6.
 */
public class UserInfoPo extends BasePo implements Serializable {
    // 登录名
    private String loginname;

    // 联系号码
    private String phone;

    // 是否有效 0有效，1无效
    //private Integer status;
    // 0超级管理员，1其他人员
    private Integer loginType;
    private Integer status;

    // 角色
    private Set<String> roles;
    // 权限
    private Set<String> permissions;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
