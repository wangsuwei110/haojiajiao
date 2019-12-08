package com.education.hjj.bz.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.hjj.bz.entity.PointsLogPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.PointsLogVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.PointsLogForm;
import com.education.hjj.bz.mapper.PointsLogMapper;
import com.education.hjj.bz.mapper.UserInfoMapper;
import com.education.hjj.bz.service.PointsLogService;
import com.education.hjj.bz.util.DateUtil;

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
		
		
		PointsLogForm pointsLogForm = new PointsLogForm();
		pointsLogForm.setTeacherId(teacherId);
		pointsLogForm.setGetPointsType(0);
		pointsLogForm.setCreateTime(DateUtil.getStandardDay(new Date()));
		
		List<PointsLogVo> list = pointsLogMapper.queryAllPointsLogByTeacherId(pointsLogForm);
		
		int i = 0;
		
		if(list != null && list.size() > 0) {
			logger.info("当天已经登陆过，不再重复记录积分！");
		}else {
			i = pointsLogMapper.addTeacherPointsLog(pointsLogPo);
		}
		
		TeacherVo tv = userInfoMapper.queryTeacherHomeInfos(teacherId);
		
		logger.info("teacherId = {} , existTeacherPoints = {} " , teacherId , tv.getTeacherPoints());
		
		TeacherPo teacherPo = new TeacherPo();
		teacherPo.setTeacherId(teacherId);
		teacherPo.setTeacherPoints(tv.getTeacherPoints() + pointsLogPo.getGetPointsCounts());
		teacherPo.setUpdateTime(new Date());
		teacherPo.setUpdateUser("admin");
		
		int  j = userInfoMapper.updateUserInfo(teacherPo);
		
		if(i > 0 && j > 0) {
			return 1;
		}
		
		return -1;
	}

}
