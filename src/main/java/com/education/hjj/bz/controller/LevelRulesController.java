package com.education.hjj.bz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.TeacherLevelRuleVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.TeachLevelForm;
import com.education.hjj.bz.service.LevelRulesService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "教员等级规则" })
@RestController
@RequestMapping(value = "/levelRules")
public class LevelRulesController {
	
	private static Logger logger = LoggerFactory.getLogger(LevelRulesController.class);

	@Autowired
	private LevelRulesService levelRulesService;
	
	@Autowired
	private UserInfoService userInfoService;

	@ApiOperation("教员等级规则查询")
	@RequestMapping(value = "/queryAllLevelRules", method = RequestMethod.POST)
	public ApiResponse queryAllLevelRules(@RequestBody TeachLevelForm teachLevelForm) {
		
		Map<String , Object> map = new HashMap<String , Object>();

		PageVo<List<TeacherLevelRuleVo>> list = levelRulesService.queryAllLevelRules();
		
		map.put("levelRules", JSON.toJSON(list));
		
		Integer teacherId = teachLevelForm.getTeacherId();
		logger.info("查询等级规则的教员的id:{}", teacherId);
		
		TeacherVo teacherVo = userInfoService.queryTeacherHomeInfos(String.valueOf(teacherId));
		
		map.put("teacherLevel", JSON.toJSON(teacherVo.getTeacherLevel()));
		map.put("teacherPoints", JSON.toJSON(teacherVo.getTeacherPoints()));

		return ApiResponse.success(JSON.toJSON(map));
	}

}
