package com.education.hjj.bz.mapper;

import com.education.hjj.bz.model.vo.SubjectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {

	List<SubjectVo> listSubject();

	Integer countSubject();
}
