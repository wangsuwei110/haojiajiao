package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.vo.*;
import com.education.hjj.bz.formBean.StudentConnectTeacherForm;
import com.education.hjj.bz.formBean.TeachScreenForm;
import com.education.hjj.bz.formBean.TeachUniversityForm;
import com.education.hjj.bz.formBean.TeacherScreenForm;
import com.education.hjj.bz.mapper.ParameterMapper;
import com.education.hjj.bz.mapper.StudentConnectTeacherMapper;
import com.education.hjj.bz.mapper.TeachLevelMapper;
import com.education.hjj.bz.mapper.TeacherMapper;
import com.education.hjj.bz.service.TeacherService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.common.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Service("TeacherService")
public class TeacherServiceImpl implements TeacherService {

	@Autowired
    private TeacherMapper teacherMapper;

	@Autowired
    private ParameterMapper parameterMapper;

	@Autowired
    private StudentConnectTeacherMapper connectMapper;

	@Autowired
    private TeachLevelMapper teachLevelMapper;

 	@Override
    public TeacherVo findById(Integer id) {
        TeacherVo teacherVo = teacherMapper.load(id);
        teacherVo.setUnitPrice(Double.valueOf(teacherVo.getChargesStandard().split("元")[0]));

        return teacherVo;
    }


    @Override
    public ApiResponse listPage(TeacherScreenForm form) {

 	    // 检索教员信息列表
        List<TeacherVo> list = teacherMapper.listTeacher(form);

        if (CollectionUtils.isEmpty(list)) {
            return ApiResponse.success("教员信息列表为空");
        }

        List<TeacherDisplayVo> result = new ArrayList<>();

        // 检索该学员的收藏教员集合
        List<Integer> teachers = connectMapper.listConnectTeachers(form.getStudentId());
        Supplier<Stream<Integer>> supplierTeacher = () -> teachers.stream();

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

            // 是否收藏该教员
            displayVo.setCollectFlag(supplierTeacher.get().anyMatch(m -> m != null && m == f.getTeacherId()));

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
        List<TeachLevelVo> teachLevelVos = teachLevelMapper.list(null);
        resultMap.put("subjects", teachLevelVos.stream().map(l ->
                new CodeVo(l.getTeachLevelId(), l.getTeachLevelName())).collect(Collectors.toList()));

        // 区域列表
        resultMap.put("address", supplier.get().filter(f -> f.getParentId() != null && f.getParentId() == 78)
                .map(m -> new CodeVo(m.getParameterId(), m.getName())).collect(Collectors.toList()));

        return ApiResponse.success(resultMap);
    }

    @Override
    public ApiResponse listSubject(TeachScreenForm form) {
        List<CodeVo> list = teachLevelMapper.listSubject(form);

        /*list.forEach(f -> {
            if (StringUtil.isNotBlank(f.getValue()) && f.getValue().length() > 2) {
                f.setValue(f.getValue().replace("小学", ""));
                f.setValue(f.getValue().replace("中学", ""));
                f.setValue(f.getValue().replace("高中", ""));
            }
        });*/

        Collections.sort(list, new Comparator<CodeVo>() {
            @Override
            public int compare(CodeVo o1, CodeVo o2) {
                return replaceNum(o1.getValue()) - replaceNum(o2.getValue());
            }
        });
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse findAllSubject(TeachScreenForm form) {
        List<CodeVo>  list = teachLevelMapper.findAllSubject(form);

        if (StringUtils.isNotEmpty(form.getTeachLevel())) {
            Collections.sort(list, new Comparator<CodeVo>() {
                @Override
                public int compare(CodeVo o1, CodeVo o2) {
                    return replaceNumOther(o1.getValue()) - replaceNumOther(o2.getValue());
                }
            });
        }
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse findSubject(TeachScreenForm form) {
        return ApiResponse.success(teachLevelMapper.findSubject(form));
    }

    @Override
    public ApiResponse listUniversity(TeachUniversityForm form) {
        return ApiResponse.success(teachLevelMapper.listUniversity(form));
    }


    @Override
    @Transactional
    public ApiResponse connect(StudentConnectTeacherForm form) {
 	    form.setDeleteStatus(0);
 	    form.setCreateTime(new Date());
        form.setCreateUser(form.getStudentId());

        connectMapper.insert(form);

        return ApiResponse.success("教员收藏成功");
    }

    @Override
    public ApiResponse connectList(StudentConnectTeacherForm form) {
        List<TeacherVo> teachers = teacherMapper.findConnectTeachers(form.getStudentId());

        if (CollectionUtils.isEmpty(teachers)) {
            return ApiResponse.success("还没有收藏教员");
        }

        List<TeacherDisplayVo> resultList = new ArrayList<>();
        teachers.forEach(f -> {
            TeacherDisplayVo displayVo = new TeacherDisplayVo();
            // sid 为收藏表的数据sid
            displayVo.setTeacherId(f.getTeacherId());
            displayVo.setTeacherName(f.getName());
            displayVo.setSchoolName(f.getSchool());
            displayVo.setTeacherLevel(f.getTeacherLevel());
            displayVo.setSex(f.getSex());
            try {
                displayVo.setCurrentStatus(DateUtil.caculDegree(f.getBeginSchoolTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                displayVo.setCurrentStatus("暂无");
            }

            displayVo.setPicture(f.getPicture());
            resultList.add(displayVo);
        });
        return ApiResponse.success(resultList);
    }

    @Override
    public ApiResponse cancelConnect(StudentConnectTeacherForm form) {
        connectMapper.cancelConnect(form);

        return ApiResponse.success("已取消收藏");
    }

    private int replaceNum(String value) {
        switch (value.substring(0, 1)) {
            case "一" :
                return 1;
            case "二" :
                return 2;
            case "三" :
                return 3;
            case "四" :
                return 4;
            case "五" :
                return 5;
            case "六" :
                return 6;
            default:
                return 7;
        }
    }

    private int replaceNumOther(String value) {
 	    value = value.replace("小学", "").replace("初中", "").replace("高中", "");
        switch (value.substring(0, 1)) {
            case "一" :
                return 1;
            case "二" :
                return 2;
            case "三" :
                return 3;
            case "四" :
                return 4;
            case "五" :
                return 5;
            case "六" :
                return 6;
            default:
                return 7;
        }
    }
 }
