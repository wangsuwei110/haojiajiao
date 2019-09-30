package com.education.hjj.bz.mapper;


import com.education.hjj.bz.formBean.StudentDemandForm;
import org.apache.ibatis.annotations.Mapper;


import com.education.hjj.bz.entity.vo.TeacherVo;

@Mapper
public interface StudentDemandMapper {

//	List<StudentDemandVo> queryStudentDemands(StudentDemandPo studentDemandPo);
//
//	StudentDemandVo queryStudentDemandDetail(Integer demandId);
	
	TeacherVo queryTeacherStudentDemands(Integer demandId);
	
	Long addStudentDemandByTeacher(StudentDemandForm form);
}
