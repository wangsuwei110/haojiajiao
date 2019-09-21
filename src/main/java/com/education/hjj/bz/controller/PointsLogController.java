package com.education.hjj.bz.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.PointsLogVo;
import com.education.hjj.bz.service.PointsLogService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "用户积分" })
@RestController
@RequestMapping(value = "/order")
public class PointsLogController {

	@Autowired
	private PointsLogService pointsLogService;


	@ApiOperation("用户积分查询")
	@RequestMapping(value = "/queryAllPointsLogByTeacherId", method = RequestMethod.POST)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view"})
	public ApiResponse queryAllPointsLogByTeacherId(@RequestParam("teacherId") Integer teacherId) {

		PageVo<List<PointsLogVo>> list = pointsLogService.queryAllPointsLogByTeacherId(teacherId);

		return ApiResponse.success(list);
	}
	
}
