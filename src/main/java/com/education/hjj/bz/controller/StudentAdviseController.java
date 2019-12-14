package com.education.hjj.bz.controller;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.vo.StudentAdviseVo;
import com.education.hjj.bz.formBean.StudentAdviseForm;
import com.education.hjj.bz.service.StudentAdviseService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 校长邮箱(家长建议)表控制类
 *
 * @创建者：sys
 * @创建时间：2019-10-8 22:03:47
 */
@Api(tags = "学员端-校长邮箱")
@RestController
@RequestMapping("studentAdvise")
public class StudentAdviseController {

    @Autowired
    private StudentAdviseService studentAdviseService;

    // 详情页（用于添加跳转）
	@PostMapping("/add")
	@ResponseBody
	public ApiResponse add(@RequestBody StudentAdviseForm form) {
		studentAdviseService.add(form);
		return ApiResponse.success("提交成功");
    }
	
	@PostMapping("/queryAllStudentAdviseByEducational")
	@ResponseBody
	@ApiOperation("教务端查询所有的待处理校长信箱的投诉与建议")
	public ApiResponse queryAllStudentAdviseByEducational(@RequestBody StudentAdviseForm form) {
		List<StudentAdviseVo>  list = studentAdviseService.queryAllStudentAdviseByEducational(form);
		
		if(list != null && list.size() > 0) {
			return ApiResponse.success("查询成功" , JSON.toJSON(list));
		}
		return ApiResponse.success("暂无数据！");
    }
	
	@PostMapping("/updateStudentAdviseByEducational")
	@ResponseBody
	@ApiOperation("教务端处理所有的校长信箱反馈")
	public ApiResponse updateStudentAdviseByEducational(@RequestBody StudentAdviseForm form) {
		int i  = studentAdviseService.updateNotNull(form);
		
		if(i > 0) {
			return ApiResponse.success("操作成功");
		}
		return ApiResponse.error("操作失败！");
    }
}
