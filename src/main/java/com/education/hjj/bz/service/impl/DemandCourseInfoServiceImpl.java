package com.education.hjj.bz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.mapper.DemandCourseInfoMapper;
import com.education.hjj.bz.service.DemandCourseInfoService;

@Service
public class DemandCourseInfoServiceImpl implements DemandCourseInfoService{
	
	@Autowired
	private DemandCourseInfoMapper demandCourseInfoMapper;

	@Override
	public int queryServiceForHours(Integer teacherId) {
		int num = demandCourseInfoMapper.queryServiceForHours(teacherId);
		return num;
	}

}
