package com.education.hjj.bz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;
import com.education.hjj.bz.entity.vo.TeacherInfoPicturesVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.entity.vo.UniversityVo;

@Mapper
public interface UserInfoMapper {
	//新增教员，注册时使用
	int insertUserInfo(TeacherPo teacher);
	
	//对教员信息进行补充
	int updateUserInfo(TeacherPo teacher);
	
	//查询教员的所有信息
	TeacherVo queryTeacherInfoByTelephone(String telephone);
	
	//查询教员账户
	TeacherAccountVo queryTeacherAccount(Integer teacherId);
	
	//查询教员账户操作日志
	TeacherAccountOperateLogVo queryUserAccountOperateLog(TeacherAccountOperateLogPo teacherAccountOperateLog);
	
	//查询权限
	List<String> getUserPermitByLoginname(@Param("loginname") String loginname);
	
	//首页教员信息查询
	TeacherVo queryTeacherHomeInfos(Integer teacherId);
	
	//查询所有在职教员的teacherId集合
	List<Integer> queryAllTeacherIds();

	//查询所有教员信息
	List<TeacherVo> queryAllTeacherInfos();
	
	//更新所有教员信息
	int updateTeachers(@Param("list") List<TeacherPo> teacherList);
	
	List<TeacherInfoPicturesVo> queryTeacherInfoByType(PicturePo picturePo);
	
	List<TeacherVo> queryStudentDemandSignUpTeacher(Integer sid);
	
	List<UniversityVo> queryAllSchools();
	
	List<TeacherVo> queryAllTeacherInfosByStudent(TeacherPo teacherPo);
}
