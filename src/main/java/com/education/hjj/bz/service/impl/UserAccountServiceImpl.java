package com.education.hjj.bz.service.impl;

import java.text.ParseException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.TeacherAccountPo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;
import com.education.hjj.bz.mapper.UserAccountMapper;
import com.education.hjj.bz.service.UserAccountService;
import com.education.hjj.bz.util.DateUtil;

@Service
public class UserAccountServiceImpl implements UserAccountService{
	
	private static Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);
	
	@Autowired
	private UserAccountMapper UserAccountMapper;

	@Override
	public TeacherAccountVo queryTeacherAccount(Integer teacherId) {
		
		TeacherAccountVo teacherAccountVo = UserAccountMapper.queryTeacherAccount(teacherId);
		
		if(teacherAccountVo != null) {
			try {
				String stayTime = DateUtil.calStayTimeByDate(teacherAccountVo.getCreateTime());
				
				teacherAccountVo.setStayTime(stayTime);
				
			} catch (ParseException e) {
				logger.error("日期转换失败！请检查数据的日期值是否正确");
				e.printStackTrace();
			}
		}
		
		return teacherAccountVo;
	}

	@Override
	@Transactional
	public int updateTeacherAccount(TeacherAccountPo teacherAccountPo) {
		
		int i = UserAccountMapper.updateTeacherAccount(teacherAccountPo);
		
		return i;
	}

}
