package com.education.hjj.bz.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.formBean.TeacherInfoForm;
import com.education.hjj.bz.formBean.UserInfoForm;

public interface UserInfoService {

	int insertTeacherInfo(LoginForm LoginForm);	

	int updateUserInfo(UserInfoForm userInfoForm);
	
	int updateUserInfoByParameter(TeacherInfoForm teacherInfoForm);
	
	Map<String, Object> queryTeacherInfoByTelephone(String telephone);
	
	TeacherVo queryTeacherInfosByTelephone(String telephone);
	
	Map<String,Object> queryTeacherAccount(String teacherId);
	
	TeacherAccountOperateLogVo queryUserAccountOperateLog(String teacherId , Integer type);
	
	TeacherVo queryTeacherHomeInfos(String teacherId);
	
	int auditUserIdCardInfo(TeacherInfoForm teacherInfoForm , MultipartFile files);
	
	int auditTeacherInfo(TeacherInfoForm teacherInfoForm);
	
	Map<String ,Object> queryTeacherInfoByType(PicturePo picturePo);
}
