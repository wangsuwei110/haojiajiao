package com.education.hjj.bz.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.service.UserPictureInfoService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.UploadFile;
import com.education.hjj.bz.util.UtilTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "文件上传" })
@RestController
@RequestMapping(value = "/upload")
public class UploadFileUtil {
	
	@Value("${picture_url}")
	private String picture_url;
	

	@ApiOperation("单图片上传")
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST , produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional
	public ApiResponse uploadFile(HttpServletRequest request, HttpServletResponse response) {
		
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		MultipartHttpServletRequest req =(MultipartHttpServletRequest)request; 
		MultipartFile multipartFiles = req.getFile("file");
		
		String dataPath = UploadFile.uploadIMG(multipartFiles, picture_url);
		
		String path = dataPath.substring(dataPath.lastIndexOf(File.separator)+1);
		
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("url", path);
		
		return ApiResponse.success("上传成功！", UtilTools.mapToJson(map));
	}
}
