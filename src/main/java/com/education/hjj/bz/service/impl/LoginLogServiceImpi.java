package com.education.hjj.bz.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.LoginLogPo;
import com.education.hjj.bz.mapper.LoginLogMapper;
import com.education.hjj.bz.service.LoginLogService;

@Service
public class LoginLogServiceImpi implements LoginLogService{
	
	@Autowired
	private LoginLogMapper loginLogMapper;

	@Override
	public int addLoginLog(Integer loginType , String loginName , String loginPhone) {
		
		LoginLogPo loginLogPo= new LoginLogPo();
		
		loginLogPo.setPhoneNum(loginPhone);
		
		if(loginType == 1) {
			
		}
		
		if(loginType == 2) {
			
			loginLogPo.setTeacherName(loginName);
		}
		loginLogPo.setStatus(1);
		loginLogPo.setCreateTime(new Date());
		loginLogPo.setCreateUser(loginName);
		loginLogPo.setUpdateTime(new Date());
		loginLogPo.setUpdateUser(loginName);
		
		int i = loginLogMapper.addLoginLog(loginLogPo);
		return i;
	}

}
