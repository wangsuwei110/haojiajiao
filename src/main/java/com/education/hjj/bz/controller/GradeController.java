package com.education.hjj.bz.controller;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.vo.TeachGradeVo;
import com.education.hjj.bz.service.GradeService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;

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

   // 查询
	@PostMapping("/list")
	@ResponseBody
	public ApiResponse list() {
		return gradeService.listAllGrade();
    }
	
	
	@PostMapping("/queryAllGradelist")
	@ResponseBody
	public ApiResponse queryAllGradelist() {
		
		List<TeachGradeVo> list = teachGradeService.queryAllTeachGrade();
		
		return ApiResponse.success("操作成功", JSON.toJSON(list));
    }
	

}
