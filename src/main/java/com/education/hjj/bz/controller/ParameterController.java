package com.education.hjj.bz.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.ParameterForm;
import com.education.hjj.bz.service.ParameterService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "参数表查询" })
@RestController
@RequestMapping(value = "/parameter")
public class ParameterController {
	
	private static Logger logger = LoggerFactory.getLogger(ParameterController.class);

	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	
	@ApiOperation("通过父ID查询所有参数")
	@RequestMapping(value = "/queryParameters", method = RequestMethod.POST)
	@Transactional
	public ApiResponse queryParametersByParentId(@RequestBody ParameterForm parameterForm) {
		
		String parent_Ids = parameterForm.getParentId();
		
		String teacherId = parameterForm.getTeacherId();
		
		logger.info("parameters={} , teacherId={}" , parent_Ids , teacherId);
		
		if(parent_Ids == null || StringUtils.isBlank(parent_Ids)) {
			return ApiResponse.error("参数错误！");
		}
		
		
		TeacherVo  tv = userInfoService.queryTeacherHomeInfos(teacherId);
		List<ParameterVo>  pVoList = null;
		if(tv != null && tv.getTeacherTag() !=null && StringUtils.isNoneBlank(tv.getTeacherTag())) {
			pVoList = parameterService.queryParameterListsByTypes(tv.getTeacherTag());
		}
		
		Map<String, List<ParameterVo>>  map = parameterService.queryParameterListsByParentId(parent_Ids);
		map.put("chooseTags", pVoList);
		
		return ApiResponse.success(map);
	}
}
