package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.StudentConnectTeacherVo;
import com.education.hjj.bz.formBean.StudentConnectTeacherForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学员教员关系表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-10-12 0:00:01
 */
@Mapper
public interface StudentConnectTeacherMapper {

	StudentConnectTeacherVo load(Long id);

	void insert(StudentConnectTeacherForm studentConnectTeacher);
	
	void updateNotNull(StudentConnectTeacherForm studentConnectTeacher);

	void delete(Long id);

    int getCount(StudentConnectTeacherForm form);

    List<StudentConnectTeacherVo> list(StudentConnectTeacherForm form);
}
