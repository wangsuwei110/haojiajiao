package com.education.hjj.bz.controller;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.vo.TeachBranchVo;
import com.education.hjj.bz.entity.vo.TeachGradeVo;
import com.education.hjj.bz.service.GradeService;
import com.education.hjj.bz.service.TeachBranchService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.education.hjj.bz.service.TeachGradeService;

/**
 * 年级常量表控制类
 *
 * @创建者：sys
 * @创建时间：2019-10-6 0:20:11
 */
@Api(tags = "学员端-年级信息")
@RestController
@RequestMapping("grade")
public class GradeController {


    @Autowired
    private GradeService gradeService;
    
    @Autowired
    private TeachGradeService teachGradeService;
    
    @Autowired 
    private TeachBranchService teachBranchService;

   // 查询
	@PostMapping("/list")
	@ResponseBody
	public ApiResponse list() {
		return gradeService.listAllGrade();
    }
	
	
	@PostMapping("/queryAllGradelist")
	@ResponseBody
	@ApiOperation("查询所有的教学年级")
	public ApiResponse queryAllGradelist() {
		
		List<TeachGradeVo> list = teachGradeService.queryAllTeachGrade();
		
		return ApiResponse.success("操作成功", JSON.toJSON(list));
    }
	
	@PostMapping("/queryAllBranchlist")
	@ResponseBody
	@ApiOperation("查询所有的教学科目")
	public ApiResponse queryAllBranchlist() {
		
		List<TeachBranchVo> list = teachBranchService.queryAllTeachBranchs();
		
		return ApiResponse.success("操作成功", JSON.toJSON(list));
    }
	

}
