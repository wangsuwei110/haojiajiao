package com.education.hjj.bz.common;

import com.education.hjj.bz.entity.LoginPo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by leo on 16/8/5.
 * <p>
 * 缓存当前登录用户信息 避免每次都从redis中读取
 */
public class LoginCache {

    public static Long getUid() {
        Subject subject = SecurityUtils.getSubject();
        LoginPo userInfoPo = (LoginPo) subject.getPrincipal();
        // TODO: 测试用
        if (userInfoPo == null) {
            return 1L;
        }
        return userInfoPo.getSid().longValue();
    }

    public static Integer getWxUserId(){
        Subject subject = SecurityUtils.getSubject();
        LoginPo userInfoPo = (LoginPo) subject.getPrincipal();
        return userInfoPo == null ? null : userInfoPo.getSid();
    }

    public static String getWxUserOpenId(){
        Subject subject = SecurityUtils.getSubject();
        LoginPo userInfoPo = (LoginPo) subject.getPrincipal();
        return userInfoPo == null ? null : userInfoPo.getTelephone();
    }
}
