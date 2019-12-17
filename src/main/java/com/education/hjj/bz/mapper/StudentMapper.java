package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.StudentVo;
import com.education.hjj.bz.formBean.StudentForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学员表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-9-29 0:30:25
 */
@Mapper
public interface StudentMapper {

	StudentVo load(Long id);

	StudentVo findByPhone(@Param("phone") String phone);

	Long insert(StudentForm student);

	Long findMaxSid();

	void updateNotNull(StudentForm student);

	void delete(Long id);

    int getCount(StudentForm form);

    List<StudentVo> loadAll(Long id);

	StudentVo findByOpenId(@Param("openId") String openId);
}
