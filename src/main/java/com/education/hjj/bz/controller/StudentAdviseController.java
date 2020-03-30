package com.education.hjj.bz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.formBean.ComplaintSuggestionForm;
import com.education.hjj.bz.service.ComplaintSuggestionService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	private ComplaintSuggestionService complaintSuggestionService;

    // 详情页（用于添加跳转）
	@RequestMapping(value ="/add" , method = RequestMethod.POST)
	@ApiOperation("新增校长信箱建议与投诉")
	public ApiResponse add(@RequestBody ComplaintSuggestionForm complaintSuggestionForm) {
		
		complaintSuggestionForm.setPersonType(2);
		
		int i = complaintSuggestionService.addComplaintSuggestion(complaintSuggestionForm);
		
		if(i > 0) {
			return ApiResponse.success("操作成功！");
		}

		return ApiResponse.success("操作失败！");
    }
	
}
