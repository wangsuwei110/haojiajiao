package com.education.hjj.bz.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.TeacherPointsRuleVo;
import com.education.hjj.bz.service.PointsRuleService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "积分规则" })
@RestController
@RequestMapping(value = "/pointRules")
public class PointRulesController {

	@Autowired
	private PointsRuleService pointsRuleService;

	@ApiOperation("积分规则查询")
	@RequestMapping(value = "/queryAllPointsRules", method = RequestMethod.GET)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view"})
	public ApiResponse queryAllPointsRules() {

		PageVo<List<TeacherPointsRuleVo>> list = pointsRuleService.queryAllPointsRules();

		return ApiResponse.success(list);
	}

}
