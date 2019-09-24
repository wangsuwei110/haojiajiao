package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.StudentDetailVo;
import com.education.hjj.bz.formBean.StudentDetailForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentDetailMapper {

	List<StudentDetailVo> listStudentDetail(StudentDetailForm form);

	Integer countStudent(StudentDetailForm form);
}
