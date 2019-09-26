package com.education.hjj.bz.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
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
		
		
		
		String teacherId = parameterForm.getTeacherId();
		
		String parent_Ids = parameterForm.getParentId();
		
		if(parent_Ids == null || StringUtils.isBlank(parent_Ids)) {
			return ApiResponse.error("参数错误！");
		}
		
		logger.info("parameters={} , teacherId={}" , parent_Ids , teacherId);
		
		TeacherVo  tv = userInfoService.queryTeacherHomeInfos(teacherId);
		
		
		List<ParameterVo> pVoBranchSlaveList = null;
		
		
		String[] parentIds = parent_Ids.split(",");
		
		List<Map<String , Object>> compareResult = new ArrayList<Map<String , Object>>();
		
		Map<String , Object> map = new HashMap<String, Object>();
		
		for(String parentId:parentIds) {
			
			List<ParameterVo>  pVoList = new ArrayList<ParameterVo>();
			
			if(tv != null && Integer.valueOf(parentId) == 31 ) {
				if(tv.getTeachLevel()!=null && StringUtils.isNoneBlank(tv.getTeachLevel())) {
					pVoList = parameterService.queryParameterListsByTypes(tv.getTeachLevel());
				}
			}
			
			if(tv != null && Integer.valueOf(parentId) == 38 ) {
				if(tv.getTeachGrade() !=null && StringUtils.isNoneBlank(tv.getTeachGrade())) {
					pVoList = parameterService.queryParameterListsByTypes(tv.getTeachGrade());
				}
			}
			
			if(tv != null && Integer.valueOf(parentId) == 62 ) {
				if(tv.getTeacherTag() !=null && StringUtils.isNoneBlank(tv.getTeacherTag())) {
					pVoList = parameterService.queryParameterListsByTypes(tv.getTeacherTag());
				}
			}
			
			if(tv != null && Integer.valueOf(parentId) == 78 ) {
				if(tv.getTeachAddress() !=null && StringUtils.isNoneBlank(tv.getTeachAddress())) {
					pVoList = parameterService.queryParameterListsByTypes(tv.getTeachAddress());
				}
			}
			
			if(tv != null && Integer.valueOf(parentId) == 95 ) {
				if(tv.getTeachBrance() !=null && StringUtils.isNoneBlank(tv.getTeachBrance())) {
					pVoList = parameterService.queryParameterListsByTypes(String.valueOf(tv.getTeachBrance()));
				}
			}
			
			List<ParameterVo>  list = parameterService.queryParameterListsByParentId(parentId);
			
			
			for(int i=0 ; i< list.size();i++) {
				for(int j=0; j<pVoList.size();j++) {
					
					if(list.get(i).getParameterId() == pVoList.get(j).getParameterId()) {
						list.get(i).setFlag(true);
						list.get(i).setBranchType("master");
					}
					
				}
				
			}
			
			
			
			if(Integer.valueOf(parentId) == 31) {
				map.put("teachLevel", list);
			}
			if(Integer.valueOf(parentId) == 38) {
				map.put("teachGrade", list);
			}
			if(Integer.valueOf(parentId) == 62) {
				map.put("teacherTag", list);
			}
			if(Integer.valueOf(parentId) == 78) {
				map.put("teachAddress", list);
			}
			if(Integer.valueOf(parentId) == 95) {
				map.put("teachBrance", list);
			}
			
			
			list = null; 
			
			
			if(tv != null && Integer.valueOf(parentId) == 95 ) {
				if(tv.getTeachBranchSlave() !=null && StringUtils.isNoneBlank(tv.getTeachBranchSlave())) {
					
					pVoList = parameterService.queryParameterListsByTypes(String.valueOf(tv.getTeachBranchSlave()));
				}
				
				
				list = parameterService.queryParameterListsByParentId(parentId);
				
				
				for(int i=0 ; i< list.size();i++) {
					for(int j=0; j<pVoList.size();j++) {
						
						if(list.get(i).getParameterId() == pVoList.get(j).getParameterId()) {
							list.get(i).setFlag(true);
							list.get(i).setBranchType("slave");
						}
						
					}
					
				}
				
				if(Integer.valueOf(parentId) == 95) {
					map.put("teachBranceSlave", list);
				}
			}
			
			
			
			
			
		}
		map.put("teachTime", JSON.toJSON(tv.getTeachTime()));
		compareResult.add(map);
		
		return ApiResponse.success("操作成功" ,JSON.toJSON(compareResult));
	}
}
