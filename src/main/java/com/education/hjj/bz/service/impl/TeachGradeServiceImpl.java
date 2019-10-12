package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.vo.TeachGradeVo;
import com.education.hjj.bz.mapper.TeachGradeMapper;
import com.education.hjj.bz.service.TeachGradeService;

@Service
public class TeachGradeServiceImpl implements TeachGradeService{
	
	@Autowired
	private TeachGradeMapper teachGradeMapper;

	@Override
	public List<TeachGradeVo> queryAllTeachGrade() {

		List<TeachGradeVo> list = teachGradeMapper.queryAllTeachGrade();
		
		return list;
		
	}

}
