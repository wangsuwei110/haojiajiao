package com.education.hjj.bz.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.entity.vo.PictureVo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.PictureForm;
import com.education.hjj.bz.formBean.TeacherInfoForm;
import com.education.hjj.bz.formBean.TeacherInfoReplenishForm;
import com.education.hjj.bz.formBean.UserInfoForm;
import com.education.hjj.bz.service.ParameterService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.service.UserPictureInfoService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.UtilTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "用户信息" })
@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController {
	
	private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserPictureInfoService userPictureInfoService;
	
	@Autowired
	private ParameterService parameterService;

	@ApiOperation("用户信息更新(带图片)")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApiResponse updateUserInfos(@RequestBody  UserInfoForm userInfoForm) {
		
		int i = userInfoService.updateUserInfo(userInfoForm);

		if(i > 0) {
			return ApiResponse.success("保存成功!");
		}
		return ApiResponse.error("保存失败!");

	}
	
	@ApiOperation("用户信息更新")
	@RequestMapping(value = "/updateUserInfoByParameter", method = RequestMethod.POST)
	public ApiResponse updateUserInfoByParameter(@RequestBody TeacherInfoForm teacherInfoForm) {

		int i = userInfoService.updateUserInfoByParameter(teacherInfoForm);
		Map<String , Object> map = new HashMap<String, Object>(1);
		
		if(i > 0) {
			
			TeacherVo teacherVo = userInfoService.queryTeacherHomeInfos(teacherInfoForm.getTeacherId());
			
			if(teacherVo != null ) {
				map.put("teacherName", teacherVo.getName());
			}
			
			return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
		}
        
        return ApiResponse.error("操作失败!");

	}
	
	@ApiOperation("用户基本信息补全")
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public ApiResponse updateUserInfos(@RequestBody TeacherInfoReplenishForm teacherInfoReplenishForm) {
		
		int i = userInfoService.updateUserInfos(teacherInfoReplenishForm);
		Map<String , Object> map = new HashMap<String, Object>(1);
		
		if(i > 0) {
			
			TeacherVo teacherVo = userInfoService.queryTeacherHomeInfos(teacherInfoReplenishForm.getTeacherId());
			
			if(teacherVo != null ) {
				map.put("teacherName", teacherVo.getName());
			}
			
			return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
		}
        
        return ApiResponse.error("操作失败!");

	}

	@ApiOperation("通过手机号查找用户信息详情")
	@RequestMapping(value = "/queryTeacherInfoByTelephone", method = RequestMethod.GET)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:edit" , "teacher:view"})
	public ApiResponse queryTeacherInfoByTelephone(String phoneNum) {

		Map<String,Object> map = userInfoService.queryTeacherInfoByTelephone(phoneNum);

		return ApiResponse.success(map);
	}
	
	@ApiOperation("用户收入查询")
	@RequestMapping(value = "/queryTeacherAccountByID", method = RequestMethod.GET)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view"})
	public ApiResponse queryUserAccount(@RequestParam("userId") String userId) {

		Map<String,Object> map = userInfoService.queryTeacherAccount(userId);

		return ApiResponse.success(map);
	}
	
	@ApiOperation("用户钱款支出明细查询")
	@RequestMapping(value = "/queryTeacherAccountOpreateLog", method = RequestMethod.GET)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view" , "student:view"})
	public ApiResponse queryUserAccountOperateLog(@RequestParam("userId") String userId , @RequestParam("type")Integer type) {

		TeacherAccountOperateLogVo teacherAccount = userInfoService.queryUserAccountOperateLog(userId , type);

		return ApiResponse.success(teacherAccount);
	}
	
	@ApiOperation("查询我的简历基本信息")
	@RequestMapping(value = "/queryTeacherInfo", method = RequestMethod.POST)
	public ApiResponse queryTeacherInfo(@RequestBody TeacherInfoForm teacherInfoForm) {
		
		String teacherId = teacherInfoForm.getTeacherId();
		
		TeacherVo teacherVo = null;
		
		if(teacherId != null && StringUtils.isNoneBlank(teacherId)) {
			
			teacherVo = userInfoService.queryTeacherHomeInfos(teacherId);
		}

		Map<String , Object> map = new HashMap<String, Object>(1);
		map.put("teacherName", teacherVo.getName());
		map.put("teacherLevel", teacherVo.getTeacherLevel());
		map.put("telephone", teacherVo.getTelephone().replace(teacherVo.getTelephone().subSequence(3, 7), "****"));
		
		logger.info("telephone = {}" , map.get("telephone"));
		
		map.put("headPicture", teacherVo.getPicture());
		map.put("sex", teacherVo.getSex());
		map.put("auditStatus", teacherVo.getAuditStatus());
		map.put("logonStatus", teacherVo.getLogonStatus());
		map.put("home", teacherVo.getHome());
		map.put("weiChar", teacherVo.getWeiChar());
		map.put("QQ", teacherVo.getQQ());
		map.put("resumeComplete", teacherVo.getResumeComplete());
		map.put("address", teacherVo.getAddress());
		
		map.put("certificate", teacherVo.getTeacherCertificate() == null?"0":teacherVo.getTeacherCertificate());
		map.put("experience", teacherVo.getExperience() == null ? "0":teacherVo.getExperience());
		
		return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
	}
	
	@ApiOperation("查询带图片的用户信息")
	@RequestMapping(value = "/queryTeacherInfoByType", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryTeacherInfoByType(@RequestBody PictureForm pictureForm) {
		
		PicturePo picturePo =new PicturePo();
		
		if(pictureForm != null) {
			
			if(pictureForm.getTeacherId() != null && StringUtils.isNoneBlank(pictureForm.getTeacherId())) {
				picturePo.setTeacherId(Integer.valueOf(pictureForm.getTeacherId()));
			}
			
			if(pictureForm.getPictureType() != null) {
				picturePo.setPictureType(pictureForm.getPictureType());
			}
			
		}
		
		Map<String, Object>  map = userInfoService.queryTeacherInfoByType(picturePo);
		
		if(map != null && map.size()>0) {
			return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
		}
		
		return ApiResponse.error("暂无数据！");
	}
	
	@ApiOperation("查询用户图片信息")
	@RequestMapping(value = "/queryUserPicturesByType", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryUserPicturesByType(@RequestBody PictureForm pictureForm) {
		
		List<PictureVo> list = userPictureInfoService.queryUserPicturesByType(pictureForm);
		
		if(list != null && list.size()>0) {
			return ApiResponse.success("操作成功" , JSONObject.toJSON(list));
		}
		
		return ApiResponse.error("暂无数据！");
	}
	
	@ApiOperation("查询用户图片详情")
	@RequestMapping(value = "/queryUserPicturesDetail", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryUserPicturesDetail(@RequestBody PictureForm pictureForm) {
		
		PictureVo pictureVo = userPictureInfoService.queryUserPicturesDetail(pictureForm);
		
		if(pictureVo != null ) {
			return ApiResponse.success("操作成功" , JSONObject.toJSON(pictureVo));
		}
		
		return ApiResponse.error("暂无数据！");
	}
	
	@ApiOperation("查询用户简历详情")
	@RequestMapping(value = "/queryUserInfosDetail", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryTeacherInfosDetail(@RequestBody PictureForm pictureForm) {
		
		
		
		if(pictureForm != null) {
			Map<String , Object> map = new HashMap<String , Object>();
			
			String teacherId = pictureForm.getTeacherId();
			
			logger.info("teacherId = {}" , teacherId);
			
			TeacherVo  teacherVo  = null;
			if(teacherId != null && StringUtils.isNoneBlank(teacherId)) {
				teacherVo  = userInfoService.queryTeacherHomeInfos(teacherId);
			}
			
			String date = teacherVo.getBeginSchoolTime();
			
			try {
				teacherVo.setBeginSchoolTime(DateUtil.caculDegree(date));
			} catch (ParseException e) {
				logger.info("转换入学日期到年级失败......");
				e.printStackTrace();
			}
			
			teacherVo.setPicture(teacherVo.getPicture().substring(teacherVo.getPicture().lastIndexOf('/')+1));
			
			//基本信息
			map.put("baseInfo", teacherVo);
			
			if(teacherVo != null) {
				String tags = teacherVo.getTeacherTag();
				logger.info("tags = {}" , tags);
				List<ParameterVo>  pVoList = null;
				
				if( tags !=null && StringUtils.isNoneBlank(tags)) {
					pVoList = parameterService.queryParameterListsByTypes(tags);
				}
				//个人标签
				map.put("chooseTags", pVoList);
				
				List<PictureVo> degreePictureList = new ArrayList<PictureVo>();
				List<PictureVo> certificatePictureList = new ArrayList<PictureVo>();
				List<PictureVo> expirencePictureList = new ArrayList<PictureVo>();
				
				if(teacherId != null && StringUtils.isNoneBlank(teacherId)) {
					
					List<PictureVo> pictureList  = userPictureInfoService.queryUserPicturesByteacherId(Integer.valueOf(teacherId));
					
					for(PictureVo p :pictureList) {
						
//						String[] picUrls = p.getPictureUrl().split(",");
//						
//						List<String> urls = new ArrayList<String>();
//						
//						for(String url:picUrls) {
//							
//							String subUrl = url.substring(url.lastIndexOf('/')+1);
//							urls.add(subUrl);
//						}
						
						String urls = p.getPictureUrl().replaceAll("/haojiajiao/share/IMG/", "");
						
						if(p.getPictureType() == 3) {
							p.setPictureUrl(urls);
							degreePictureList.add(p);
						}
						
						if(p.getPictureType() == 4) {
							p.setPictureUrl(urls);
							certificatePictureList.add(p);						
						}
						
						if(p.getPictureType() == 5) {
							p.setPictureUrl(urls);
							expirencePictureList.add(p);
						}
						
					}
				}
				
				//个人学历图片
				map.put("degreePictureList", degreePictureList);
				//个人证书图片
				map.put("certificatePictureList", certificatePictureList);
				//个人经验图片
				map.put("expirencePictureList", expirencePictureList);
				
				
				String parameterIds = teacherVo.getTeachBrance()+","+teacherVo.getTeachBranchSlave();
				
				logger.info("parameterIds = {}" , parameterIds);
				
				List<ParameterVo> parametersList = null;
				if(teacherVo.getTeachBrance() != null && teacherVo.getTeachBranchSlave() != null ) {
					parametersList = parameterService.queryParameterListsByTypes(parameterIds);
				}
				
				//可教科目
				map.put("teachBranchs", parametersList);
				
				String address = teacherVo.getTeachAddress();
				logger.info("address = {}" , address);
				
				List<ParameterVo> addressList = null;
				if(address != null && StringUtils.isNoneBlank(address)) {
					addressList =parameterService.queryParameterListsByTypes(address);
				}
				
				//可教区域
				map.put("teachAddress", addressList);
				
				//教学时间
				map.put("teachTime", teacherVo.getTeachTime());
				
				return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
			} 
				
			return ApiResponse.error("暂无数据！");
		}
		
		return ApiResponse.error("暂无数据！");
	}

	@ApiOperation("查询授课资料详情")
	@RequestMapping(value = "/queryUserteachBranchDetails", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryUserteachBranchDetails(@RequestBody PictureForm pictureForm) {
		
		return ApiResponse.error("暂无数据！");
	}
	
	@ApiOperation("查询所有教员信息")
	@RequestMapping(value = "/queryAllTeacherInfos", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse queryAllTeacherInfos() {
		
		List<TeacherVo> list = userInfoService.queryAllTeacherInfos();
		
		int count = list.size();
		
		Map<String , Object> map = new HashMap<String , Object>(2);
		
		if(count > 0) {
			map.put("count", count);
			map.put("teacherList", list);
			
			return ApiResponse.success("操作成功！", UtilTools.mapToJson(map));
		}
		
		return ApiResponse.error("暂无数据！");
		
	}
	
}
