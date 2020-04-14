package com.education.hjj.bz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.vo.ComplaintSuggestionVo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.formBean.ComplaintSuggestionForm;
import com.education.hjj.bz.service.ComplaintSuggestionService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "建议与投诉" })
@RestController
@RequestMapping(value = "/ComplaintSuggestion")
public class ComplaintSuggestionController {
	
	private static Logger logger = LoggerFactory.getLogger(ComplaintSuggestionController.class);

	@Autowired
	private ComplaintSuggestionService complaintSuggestionService;

	@ApiOperation("新增建议与投诉")
	@RequestMapping(value = "/addComplaintSuggestion", method = RequestMethod.POST)
	public ApiResponse addStudentDemandByTeacher(@RequestBody ComplaintSuggestionForm complaintSuggestionForm) {
		
		complaintSuggestionForm.setPersonType(1);
		
		int i = complaintSuggestionService.addComplaintSuggestion(complaintSuggestionForm);
		
		if(i > 0) {
			return ApiResponse.success("操作成功！");
		}

		return ApiResponse.success("操作失败！");

	}
	
	@ApiOperation("回复建议与投诉")
	@RequestMapping(value = "/updateComplaintSuggestion", method = RequestMethod.POST)
	public ApiResponse updateComplaintSuggestion(@RequestBody ComplaintSuggestionForm complaintSuggestionForm) {
		
		int i = complaintSuggestionService.updateComplaintSuggestion(complaintSuggestionForm);
		
		if(i > 0) {
			return ApiResponse.success("操作成功！");
		}

		return ApiResponse.success("操作失败！");
	}
	
	@ApiOperation("按ID查找建议与投诉")
	@RequestMapping(value = "/queryComplaintSuggestionById", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryComplaintSuggestionById(@RequestBody ComplaintSuggestionForm complaintSuggestionForm) {
		
		int id = complaintSuggestionForm.getComplaintSuggestionId();
		
		ComplaintSuggestionVo  ComplaintSuggestionVo = complaintSuggestionService.queryComplaintSuggestionById(id);
		
		if(ComplaintSuggestionVo != null) {
			
			return ApiResponse.success("操作成功！" , JSON.toJSON(ComplaintSuggestionVo));
		}

		return ApiResponse.success("暂无数据！");
	}
	
	@ApiOperation("查找所有的建议与投诉")
	@RequestMapping(value = "/queryAllComplaintSuggestion", method = RequestMethod.GET)
	public ApiResponse queryAllComplaintSuggestion() {
		
		List<ComplaintSuggestionVo>  list = complaintSuggestionService.queryAllComplaintSuggestion();
		
		if(list.size() > 0) {
			return ApiResponse.success("操作成功！" , JSON.toJSON(list));
		}

		return ApiResponse.success("暂无数据！");
	}
	
	@ApiOperation("教务端查找所有的建议与投诉")
	@RequestMapping(value = "/queryAllComplaintSuggestionByEducational", method = RequestMethod.POST)
	@RequiresPermissions(logical = Logical.AND, value = {"admin:view","audit:view"})
	public ApiResponse queryAllComplaintSuggestionByEducational(@RequestBody ComplaintSuggestionForm complaintSuggestionForm) {
		
		List<ComplaintSuggestionVo>  list = complaintSuggestionService.queryAllComplaintSuggestionByEducational(complaintSuggestionForm);
		
//		complaintSuggestionForm.setPageSize(Integer.MAX_VALUE);
		
		int  listCount = complaintSuggestionService.queryCountsComplaintSuggestionByEducational();
		
		PageVo pageVo = new PageVo();
		
		pageVo.setDataList(list);
		pageVo.setTotal(list.size());
		
		Map<String , Object> map = new HashMap<>();
		map.put("dataInfo", pageVo);
		map.put("count", listCount);
				
		if(list.size() > 0) {
			return ApiResponse.success("操作成功！" , JSON.toJSON(map));
		}

		return ApiResponse.success("暂无数据！");
	}
	
	@ApiOperation("教务端更新建议与投诉")
	@RequestMapping(value = "/updateComplaintSuggestionByEducational", method = RequestMethod.POST)
	@RequiresPermissions(logical = Logical.AND, value = {"admin:edit","audit:edit"})
	public ApiResponse updateComplaintSuggestionByEducational(@RequestBody ComplaintSuggestionForm complaintSuggestionForm) {
		
		int i = complaintSuggestionService.updateComplaintSuggestionByEducational(complaintSuggestionForm);
		
		if(i > 0) {
			return ApiResponse.success("操作成功！");
		}

		return ApiResponse.error("操作失败！");
	}

}
