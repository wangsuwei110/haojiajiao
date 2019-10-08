package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.TeacherLevelRuleVo;
import com.education.hjj.bz.mapper.LevelRulesMapper;
import com.education.hjj.bz.service.LevelRulesService;

@Service
public class LevelRulesServiceImpl implements LevelRulesService{
	
	@Autowired
	private LevelRulesMapper levelRulesMapper;

	@Override
	public PageVo<List<TeacherLevelRuleVo>> queryAllLevelRules() {
		
		List<TeacherLevelRuleVo> list = levelRulesMapper.queryAllLevelRules();
		
		PageVo page = new PageVo();
		page.setTotal(list.size());
		page.setDataList(list);

		return page;
	}

	
}
