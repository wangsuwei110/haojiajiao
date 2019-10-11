package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.vo.CodeVo;
import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.entity.vo.TeacherDisplayVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.TeacherScreenForm;
import com.education.hjj.bz.mapper.ParameterMapper;
import com.education.hjj.bz.mapper.TeacherMapper;
import com.education.hjj.bz.service.TeacherService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.common.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 教师表业务实现
 *
 * @创建者：sys
 * @创建时间：2019-10-9 21:50:41
 */
@Service("teacherBiz")
public class TeacherServiceImpl implements TeacherService {

	@Autowired
    private TeacherMapper teacherMapper;

	@Autowired
    private ParameterMapper parameterMapper;

 	@Override
    public TeacherVo findById(Long id) {
        return teacherMapper.load(id);
    }


    @Override
    public ApiResponse listPage(TeacherScreenForm form) {

 	    // 检索教员信息列表
        List<TeacherVo> list = teacherMapper.listTeacher(form);

        if (CollectionUtils.isEmpty(list)) {
            return ApiResponse.success("教员信息列表为空");
        }

        List<TeacherDisplayVo> result = new ArrayList<>();

        // 检索参数信息
        List<ParameterVo> paramList = parameterMapper.queryParameterLists();

        final Supplier<Stream<ParameterVo>> supplier = () -> paramList.stream();
        // 编辑检索的字段
        // 1.教学范围
        list.forEach(f -> {
            TeacherDisplayVo displayVo = new TeacherDisplayVo();
            displayVo.setTeacherId(f.getTeacherId());
            displayVo.setTeacherName(f.getName());
            displayVo.setSchoolName(f.getSchool());
            displayVo.setTeacherLevel(f.getTeacherLevel());
            displayVo.setChargesStandard(f.getChargesStandard());
            displayVo.setSex(f.getSex());
            displayVo.setPicture(f.getPicture());

            // 教学科目
            List<String> branchSlave = new ArrayList<>();
            if (StringUtils.isNotEmpty(f.getTeachBranchSlave())) {
                List<String> slaves = new ArrayList<>(Arrays.asList(f.getTeachBranchSlave().split(",")));
                slaves.forEach(n -> {
                    Optional<ParameterVo> op = supplier.get().filter(s ->
                            s.getParameterId() != null && s.getParameterId().toString().equals(n)).findFirst();
                    if (op.isPresent()) {
                        branchSlave.add(op.get().getName());
                    }
                });
            }
            displayVo.setTeachBranchSlave(branchSlave);

            // 个人标签
            List<String> teacherTags = new ArrayList<>();
            if (StringUtils.isNotEmpty(f.getTeacherTag())) {
                List<String> teacherTagList = new ArrayList<>(Arrays.asList(f.getTeacherTag().split(",")));
                teacherTagList.forEach(t -> {
                    Optional<ParameterVo> op = supplier.get().filter(s ->
                            s.getParameterId() != null && s.getParameterId().toString().equals(t)).findFirst();
                    if (op.isPresent()) {
                        teacherTags.add(op.get().getName());
                    }
                });
            }
            displayVo.setTeacherTag(teacherTags);
            if (StringUtils.isNotEmpty(f.getBeginSchoolTime())) {
                try {
                    displayVo.setCurrentStatus(DateUtil.caculDegree(f.getBeginSchoolTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            result.add(displayVo);
        });


        return ApiResponse.success(result);
    }


    @Override
    public ApiResponse selectList() {
 	    // 筛选下拉框集合
        Map<String ,List<CodeVo>> resultMap = new HashMap<>();
        // 检索参数信息
        List<ParameterVo> paramList = parameterMapper.queryParameterLists();
        Supplier<Stream<ParameterVo>> supplier = () -> paramList.stream();

        // 科目列表
        resultMap.put("subjects", supplier.get().filter(f -> f.getParentId() != null && f.getParentId() == 31)
                .map(m -> new CodeVo(m.getParameterId(), m.getName())).collect(Collectors.toList()));

        // 区域列表
        resultMap.put("address", supplier.get().filter(f -> f.getParentId() != null && f.getParentId() == 78)
                .map(m -> new CodeVo(m.getParameterId(), m.getName())).collect(Collectors.toList()));

        return ApiResponse.success(resultMap);
    }

 }
