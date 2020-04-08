package com.education.hjj.bz.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.formBean.TeacherAccountLogForm;
import com.education.hjj.bz.mapper.UserAccountLogMapper;
import com.education.hjj.bz.service.UserAccountLogService;

@Service
public class UserAccountLogServiceImpl implements UserAccountLogService{
	
	@Autowired
	private UserAccountLogMapper userAccountLogMapper;

	@Override
	@Transactional
	public int insertUserAccountLog(TeacherAccountOperateLogPo teacherAccountOperateLogPo) {
		int  i =userAccountLogMapper.insertUserAccountLog(teacherAccountOperateLogPo);
		return i;
	}

	@Override
	public List<TeacherAccountOperateLogVo> queryUserAccountLogList(Map<String, Object> map) {
		List<TeacherAccountOperateLogVo> teacherAccountOperateLogVo = userAccountLogMapper.queryUserAccountLogList(map);
		return teacherAccountOperateLogVo;
	}

	@Override
	public TeacherAccountOperateLogVo queryUserAccountLogDetail(Integer paymentId) {
		TeacherAccountOperateLogVo teacherAccountOperateLogVo = userAccountLogMapper.queryUserAccountLogDetail(paymentId);
		return teacherAccountOperateLogVo;
	}

	@Override
	public List<TeacherAccountOperateLogVo> queryUserAccountLogListByEducational(
			TeacherAccountLogForm teacherAccountLogForm) {
		
		List<TeacherAccountOperateLogVo> list = userAccountLogMapper.queryUserAccountLogListByEducational(teacherAccountLogForm);
		
		return list;
	}

	@Override
	public int queryCountsUserAccountLogListByEducational() {
		int i = userAccountLogMapper.queryCountsUserAccountLogListByEducational();
		return i;
	}

}
