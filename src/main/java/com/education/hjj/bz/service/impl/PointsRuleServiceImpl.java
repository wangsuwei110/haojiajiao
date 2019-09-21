package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.TeacherPointsRuleVo;
import com.education.hjj.bz.mapper.PointsRuleMapper;
import com.education.hjj.bz.service.PointsRuleService;

@Service
public class PointsRuleServiceImpl implements PointsRuleService{
	
	@Autowired
	private PointsRuleMapper pointsRuleMapper;

	@Override
	public PageVo<List<TeacherPointsRuleVo>> queryAllPointsRules() {
		PageVo pageVo = new PageVo();
		
		List<TeacherPointsRuleVo>  list = pointsRuleMapper.queryAllPointsRules();
		
		pageVo.setDataList(list);
		
		return pageVo;
	}

	
}
