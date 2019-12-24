package com.education.hjj.bz.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.vo.StudentDemandConnectVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.mapper.StudentDemandConnectMapper;
import com.education.hjj.bz.service.StudentDemandConnectService;
import com.education.hjj.bz.util.RegUtils;

@Service
public class StudentDemandConnectServiceImpl implements StudentDemandConnectService{
	
	private static Logger logger = LoggerFactory.getLogger(StudentDemandConnectServiceImpl.class);
	
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

	@Override
	public String queryAppraiseCountByTeacherId(String teacherId ) {
		
		StudentDemandConnectForm form = new StudentDemandConnectForm();
		form.setTeacherId(Integer.valueOf(teacherId));
		
		int count = studentDemandConnectMapper.queryAppraiseCountByTeacherId(form);
		
		form.setAppraiseLevel(1);
		
		double goodCount = studentDemandConnectMapper.queryAppraiseCountByTeacherId(form);
		
		String appraiseRate = "";
		
		if(count > 0) {
			
			double newRate = (goodCount) / count;
			
			logger.info(" allAppraiseCount={} , goodAppraiseCount={} , newRate={}", count,
					goodCount, newRate);
			
			BigDecimal bg = new BigDecimal(newRate).setScale(4, RoundingMode.UP);
			
			logger.info("appraiseRate = {}", RegUtils.doubleToPersent().format(bg));
			
			appraiseRate = RegUtils.doubleToPersent().format(bg);
			
		}else {
			appraiseRate = 0 + "%";
		}
		
		return appraiseRate;
	}

}
