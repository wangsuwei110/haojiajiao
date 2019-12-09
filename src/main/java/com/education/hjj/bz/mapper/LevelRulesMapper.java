package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.vo.TeacherLevelRuleVo;
import com.education.hjj.bz.formBean.TeachLevelForm;

@Mapper
public interface LevelRulesMapper {

	List<TeacherLevelRuleVo> queryAllLevelRules(TeachLevelForm teachLevelForm);
}
