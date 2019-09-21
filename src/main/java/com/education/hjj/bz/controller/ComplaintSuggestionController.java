package com.education.hjj.bz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.education.hjj.bz.formBean.ComplaintSuggestionForm;
import com.education.hjj.bz.formBean.PictureForm;
import com.education.hjj.bz.service.ComplaintSuggestionService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "建议与投诉" })
@RestController
@RequestMapping(value = "/ComplaintSuggestion")
public class ComplaintSuggestionController {

	@Autowired
	private ComplaintSuggestionService complaintSuggestionService;

	@ApiOperation("新增建议与投诉")
	@RequestMapping(value = "/addStudentDemandByTeacher", method = RequestMethod.POST)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:edit"})
	public ApiResponse addStudentDemandByTeacher(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		MultipartHttpServletRequest req =(MultipartHttpServletRequest)request; 
		MultipartFile multipartFiles = req.getFile("file");
		
		String telephone=request.getParameter("telephone");//取出form-data中a的值
		String content=request.getParameter("content()");//取出form-data中a的值
		Integer type = Integer.valueOf(request.getParameter("type"));
		String teacherId = request.getParameter("personId");
		
		ComplaintSuggestionForm complaintSuggestionForm = new ComplaintSuggestionForm();
		complaintSuggestionForm.setPersonId(teacherId);
		complaintSuggestionForm.setTelephone(telephone);
		complaintSuggestionForm.setType(String.valueOf(type));
		
		String pictureTitle=request.getParameter("pictureTitle");//取出form-data中a的值
		String pictureDesc=request.getParameter("pictureDesc");//取出form-data中a的值
		
		PictureForm pictureForm = new PictureForm();
		pictureForm.setPictureTitle(pictureTitle);
		pictureForm.setPictureDesc(pictureDesc);
		

		int i = complaintSuggestionService.addComplaintSuggestion(complaintSuggestionForm, multipartFiles , type , pictureForm);

		return ApiResponse.success(i);
	}

}
