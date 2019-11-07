package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.StudentLogPo;
import com.education.hjj.bz.entity.vo.StudentLogVo;
import com.education.hjj.bz.service.StudentLogService;
import com.education.hjj.bz.mapper.StudentLogMapper;

@Service
public class StudentLogServiceImpl implements StudentLogService {
	
	
	@Autowired
	private StudentLogMapper StudentLogMapper;

	@Override
	public List<StudentLogVo> queryStudentLogByHome10() {
	
		List<StudentLogVo> list = StudentLogMapper.queryStudentLogByHome10();
		
		return list;
	}

	@Override
	public int addStudentLog(StudentLogPo studentLogPo) {
		
		int i = StudentLogMapper.addStudentLog(studentLogPo);
		// TODO Auto-generated method stub
		return i;
	}

}
