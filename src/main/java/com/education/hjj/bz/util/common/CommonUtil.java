package com.education.hjj.bz.util.common;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.vo.WeekTimeVo;
import com.education.hjj.bz.mapper.TeachGradeMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * String工具
 * @author dolyw.com
 * @date 2018/9/4 14:48
 */
public class CommonUtil {

    @Autowired
    private static TeachGradeMapper teachGradeMapper;

    private CommonUtil(TeachGradeMapper teachGradeMapper) {
        CommonUtil.teachGradeMapper = teachGradeMapper;
    }


    public static String getGrade(Integer gradeId) {
        return teachGradeMapper.getGrade(gradeId);
    }


    enum Week {
        Monday(1, "周一"),
        Tuesday(2, "周二"),
        Wednesday(3, "周三"),
        Thursday(4, "周四"),
        Friday(5, "周五"),
        Saturday(6, "周六"),
        Sunday(7, "周日");

        private Integer code;
        private String value;

        private Week(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public static String getValue(Integer code) {
           Optional<Week> op = Arrays.stream(Week.values()).filter(f -> f.code == code).findFirst();
           if (op.isPresent()) {
               return op.get().value;
           }
           return "";
        }
    }

    enum Time {
        MORNING(1, "早上"),
        AFTERNOON(2, "下午"),
        NIGHT(3, "晚上");

        private Integer code;
        private String value;

        private Time(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public static String getValue(Integer code) {
            Optional<Time> op = Arrays.stream(Time.values()).filter(f -> f.code == code).findFirst();
            if (op.isPresent()) {
                return op.get().value;
            }
            return "";
        }
    }


    /**
     * 获取中文的上课时间段
     * @param weekTime
     */
    public static String getWeekDesc(String weekTime) {
        List<WeekTimeVo> weeks = JSON.parseArray(weekTime, WeekTimeVo.class);

        List<String> week = new ArrayList<>();
        weeks.stream().forEach(f -> {
            week.add(Week.getValue(f.getWeek()) + Time.getValue(f.getTime()) + "、");
        });

        weekTime = week.stream().reduce("", (a, b) -> a + b);
        return weekTime.substring(0, weekTime.length() -1);
    }


    public static void main(String[] args) {
        String st = "[{week:1,time:3},{week:2,time:3}]";

        List<WeekTimeVo> weeks = JSON.parseArray(st, WeekTimeVo.class);

        List<String> week = new ArrayList<>();
        weeks.stream().forEach(f -> {
            week.add(Week.getValue(f.getWeek()) + Time.getValue(f.getTime()) + "、");
        });

        st = week.stream().reduce("", (a, b) -> a + b);
        System.out.println(st.substring(0, st.length() -1));
    }
}
