package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.TeacherAndStudentDemandPo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;

@Mapper
public interface StudentDemandMapper {

	List<StudentDemandVo> queryStudentDemands(StudentDemandPo studentDemandPo);
	
	StudentDemandVo queryStudentDemandDetail(Integer demandId);
	
	TeacherVo queryTeacherStudentDemands(Integer demandId);
	
	int addStudentDemandByTeacher(TeacherAndStudentDemandPo teacherAndStudentDemand);
}
