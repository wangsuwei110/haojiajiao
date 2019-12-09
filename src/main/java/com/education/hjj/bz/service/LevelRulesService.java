package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.TeacherLevelRuleVo;
import com.education.hjj.bz.formBean.TeachLevelForm;

public interface LevelRulesService {
	
	PageVo<List<TeacherLevelRuleVo>> queryAllLevelRules(TeachLevelForm teachLevelForm);

}
