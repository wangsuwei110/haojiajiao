package com.education.hjj.bz.controller;

import com.education.hjj.bz.formBean.StudentAdviseForm;
import com.education.hjj.bz.service.StudentAdviseService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;
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
}
