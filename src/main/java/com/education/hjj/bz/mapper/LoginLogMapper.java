package com.education.hjj.bz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.LoginLogPo;

@Mapper
public interface LoginLogMapper {

	int addLoginLog(LoginLogPo loginLogPo);
}
