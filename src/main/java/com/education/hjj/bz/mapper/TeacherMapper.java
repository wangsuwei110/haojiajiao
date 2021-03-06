package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.TeacherScreenForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教师表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-10-9 21:50:41
 */
@Mapper
public interface TeacherMapper {

	TeacherVo load(@Param("teacherId") Integer teacherId);

    int getCount(TeacherScreenForm form);

    TeacherVo findByOpenId(@Param("openId") String openId);

    List<TeacherVo> list(TeacherScreenForm form);

    List<TeacherVo> listTeacher(TeacherScreenForm form);

    List<TeacherVo> findConnectTeachers(@Param("studentId") Integer studentId);

    List<TeacherVo> listTeacherByStudentId(@Param("demandId") Integer demandId);

    void updateInfoByTeacherId(TeacherVo vo);

}
