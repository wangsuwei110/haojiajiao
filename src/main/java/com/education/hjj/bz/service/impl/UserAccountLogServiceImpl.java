package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.mapper.UserAccountLogMapper;
import com.education.hjj.bz.service.UserAccountLogService;

@Service
public class UserAccountLogServiceImpl implements UserAccountLogService{
	
	@Autowired
	private UserAccountLogMapper userAccountLogMapper;

	@Override
	public int insertUserAccountLog(TeacherAccountOperateLogPo teacherAccountOperateLogPo) {
		int  i =userAccountLogMapper.insertUserAccountLog(teacherAccountOperateLogPo);
		return i;
	}

	@Override
	public List<TeacherAccountOperateLogVo> queryUserAccountLogList(TeacherAccountOperateLogPo teacherAccountOperateLogPo) {
		List<TeacherAccountOperateLogVo> teacherAccountOperateLogVo = userAccountLogMapper.queryUserAccountLogList(teacherAccountOperateLogPo);
		return teacherAccountOperateLogVo;
	}

	@Override
	public TeacherAccountOperateLogVo queryUserAccountLogDetail(Integer paymentId) {
		TeacherAccountOperateLogVo teacherAccountOperateLogVo = userAccountLogMapper.queryUserAccountLogDetail(paymentId);
		return teacherAccountOperateLogVo;
	}

}
