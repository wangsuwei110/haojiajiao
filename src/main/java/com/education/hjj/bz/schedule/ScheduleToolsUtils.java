package com.education.hjj.bz.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.mapper.UserInfoMapper;

@Component
public class ScheduleToolsUtils {

	private Logger logger = LoggerFactory.getLogger(ScheduleToolsUtils.class);

	@Autowired
	private UserInfoMapper userInfoMapper;


//    //每天3：05执行，计算续课率
//    @Scheduled(cron = "0 05 03 ? * *")
//    public void testTasks() {
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
//    }

	// 每隔1小时执行一次,计算聘用率
	// @Scheduled(fixedRate = 1000*60*60)
//	@Scheduled(initialDelay=1000*20,fixedRate = 1000*60*1)
//	@Transactional
	public void calcueEmployRate() {
		
		List<TeacherVo> list = userInfoMapper.queryAllTeacherInfos();
		
		List<TeacherPo> teacherList = new ArrayList<TeacherPo>();

		for (TeacherVo t : list) {
			TeacherPo tPo = new TeacherPo();
			tPo.setTeacherId(Integer.valueOf(t.getTeacherId()));
			
			//续课率大于等于60%,并且个人积分大于等于100，小于等于300时
			if (t.getResumptionRate().doubleValue() >= 0.60 && Integer.valueOf(t.getTeacherPoints()) >= 100
					&& Integer.valueOf(t.getTeacherPoints()) <= 300) {
				tPo.setTeacherLevel("T1");
				tPo.setChargesStandard("120.00元/每课时");
			}
			
			//续课率大于等于60%,并且个人积分大于等于100，小于等于300时
			if (t.getResumptionRate().doubleValue() >= 0.70 && Integer.valueOf(t.getTeacherPoints()) >= 301
					&& Integer.valueOf(t.getTeacherPoints()) <= 600) {
				tPo.setTeacherLevel("T2");
				tPo.setChargesStandard("160.00元/每课时");
			}
			
			//续课率大于等于60%,并且个人积分大于等于100，小于等于300时
			if (t.getResumptionRate().doubleValue() >= 0.80 && Integer.valueOf(t.getTeacherPoints()) >= 601) {
				tPo.setTeacherLevel("T3");
				tPo.setChargesStandard("200.00元/每课时");
			}
			
			//续课率大于等于60%,并且个人积分大于等于100，小于等于300时
			if (t.getResumptionRate().doubleValue() < 0.60 && Integer.valueOf(t.getTeacherPoints()) < 100) {
				tPo.setTeacherLevel("T0");
				tPo.setChargesStandard("100.00元/每课时");
			}
			tPo.setUpdateTime(new Date());
			tPo.setUpdateUser("admin");
			teacherList.add(tPo);
		}
		
		int i = userInfoMapper.updateTeachers(teacherList);
		if(i > 0) {
			logger.info("教员等级，收费标准更新成功！");
		}
	}

}
