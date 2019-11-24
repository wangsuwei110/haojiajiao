package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.vo.CodeVo;
import com.education.hjj.bz.entity.vo.GradeVo;
import com.education.hjj.bz.mapper.GradeMapper;
import com.education.hjj.bz.service.GradeService;
import com.education.hjj.bz.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 年级常量表业务实现
 *
 * @创建者：sys
 * @创建时间：2019-10-6 0:20:11
 */
@Service("GradeService")
public class GradeServiceImpl implements GradeService {

	@Autowired
    private GradeMapper gradeMapper;

 	@Override
    public ApiResponse listAllGrade() {
        List<CodeVo> resultList = new ArrayList<>();
 	    List<GradeVo> list = gradeMapper.listAll();

 	    list.forEach(l -> {
 	        CodeVo vo = new CodeVo(l.getGradeNum(), l.getGradeName());
            resultList.add(vo);
        });

        return ApiResponse.success(resultList);
    }

 }
