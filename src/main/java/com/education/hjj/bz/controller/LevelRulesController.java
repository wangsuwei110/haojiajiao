package com.education.hjj.bz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.TeacherLevelRuleVo;
import com.education.hjj.bz.service.LevelRulesService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "教员等级规则" })
@RestController
@RequestMapping(value = "/levelRules")
public class LevelRulesController {

	@Autowired
	private LevelRulesService levelRulesService;

	@ApiOperation("教员等级规则查询")
	@RequestMapping(value = "/queryAllLevelRules", method = RequestMethod.GET)
	public ApiResponse queryAllLevelRules() {

		PageVo<List<TeacherLevelRuleVo>> list = levelRulesService.queryAllLevelRules();

		return ApiResponse.success(list);
	}

}
