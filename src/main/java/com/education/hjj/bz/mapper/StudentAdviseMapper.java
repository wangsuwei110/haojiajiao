package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.StudentAdviseVo;
import com.education.hjj.bz.formBean.StudentAdviseForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 校长邮箱(家长建议)表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-10-8 22:03:47
 */
@Mapper
public interface StudentAdviseMapper {

	void insert(StudentAdviseForm studentAdvise);

    List<StudentAdviseVo> list(StudentAdviseForm form);
    
    List<StudentAdviseVo> queryAllStudentAdviseByEducational(StudentAdviseForm studentAdviseForm);
    
    int updateNotNull(StudentAdviseForm studentAdviseForm);
}
