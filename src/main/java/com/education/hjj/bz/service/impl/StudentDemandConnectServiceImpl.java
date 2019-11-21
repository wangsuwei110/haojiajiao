package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.vo.StudentDemandConnectVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.mapper.StudentDemandConnectMapper;
import com.education.hjj.bz.service.StudentDemandConnectService;

@Service
public class StudentDemandConnectServiceImpl implements StudentDemandConnectService{
	
	@Autowired
	private StudentDemandConnectMapper studentDemandConnectMapper;

	@Override
	public int queryServiceForStudentSuccess(Integer teacherId) {
		int num = studentDemandConnectMapper.queryServiceForStudentSuccess(teacherId);
		return num;
	}

	@Override
	public List<StudentDemandConnectVo> queryStudentAppraiseForTeacher(Integer teacherId) {
		List<StudentDemandConnectVo> list = studentDemandConnectMapper.queryStudentAppraiseForTeacher(teacherId);
		return list;
	}

	@Override
	public List<StudentDemandVo> queryServiceForStudentByTeacherId(Integer teacherId) {
		List<StudentDemandVo> list = studentDemandConnectMapper.queryServiceForStudentByTeacherId(teacherId);
		return list;
	}

}
