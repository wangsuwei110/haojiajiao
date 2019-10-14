package com.education.hjj.bz.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.hjj.bz.controller.LoginController;
import com.education.hjj.bz.entity.PointsLogPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.PointsLogVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.PointsLogForm;
import com.education.hjj.bz.mapper.PointsLogMapper;
import com.education.hjj.bz.mapper.UserInfoMapper;
import com.education.hjj.bz.service.PointsLogService;

@Service
public class PointsLogServiceImpl implements PointsLogService{
	
	private static Logger logger = LoggerFactory.getLogger(PointsLogServiceImpl.class);
	
	@Autowired
	private PointsLogMapper pointsLogMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public PageVo<List<PointsLogVo>> queryAllPointsLogByTeacherId(PointsLogForm pointsLogForm) {
		
		PageVo pageVo = new PageVo();
		
		List<PointsLogVo> list = pointsLogMapper.queryAllPointsLogByTeacherId(pointsLogForm);
		
		pageVo.setDataList(list);
		pageVo.setTotal(list.size());
		return pageVo;
	}

	@Override
	@Transactional
	public int addTeacherPointsLog(Integer teacherId , PointsLogPo pointsLogPo) {
		
		logger.info("teacherId = {} , getPoints = {} , getPointsDesc = {}" , 
				teacherId , pointsLogPo.getGetPointsCounts() , pointsLogPo.getGetPointsDesc());
		
		int i = pointsLogMapper.addTeacherPointsLog(pointsLogPo);
		
		TeacherVo tv = userInfoMapper.queryTeacherHomeInfos(teacherId);
		
		TeacherPo teacherPo = new TeacherPo();
		teacherPo.setTeacherId(teacherId);
		teacherPo.setTeacherPoints(tv.getTeacherPoints() + pointsLogPo.getGetPointsDesc());
		teacherPo.setUpdateTime(new Date());
		teacherPo.setUpdateUser("admin");
		
		int  j = userInfoMapper.updateUserInfo(teacherPo);
		
		if(i > 0 && j > 0) {
			return 1;
		}
		
		return -1;
	}

}
