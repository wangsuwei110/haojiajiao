package com.education.hjj.bz.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.service.QueryParameterService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "参数表" })
@RestController
@RequestMapping(value = "/parameter")
public class QueryParameterController {
	
	@Autowired
	private QueryParameterService queryParameterService;

	@GetMapping(value = "/parameterLists")
	@ApiOperation("查询所有的参数列表")
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view" , "student:view"})
	public ApiResponse getParameterLists(){
		
		List<ParameterVo> list = queryParameterService.queryParameterLists();
		
		return ApiResponse.success(list);
	}
	
	@GetMapping("/parameterListsByType")
	@ApiOperation("查询单个类型的参数列表")
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view" , "student:view"})
	public ApiResponse getParameterListsByType(@RequestParam("parameterType")Integer parameterType){
		
		List<ParameterVo> list = queryParameterService.queryParameterListsByType(parameterType);
		
		return ApiResponse.success(list);
	}
	
}
