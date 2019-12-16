package com.education.hjj.bz.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.mapper.StudentDemandConnectMapper;
import com.education.hjj.bz.mapper.StudentDemandMapper;
import com.education.hjj.bz.mapper.UserInfoMapper;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.SendWXMessageUtils;
import com.education.hjj.bz.util.weixinUtil.config.Constant;

@Component
public class ScheduleToolsUtils {

	private Logger logger = LoggerFactory.getLogger(ScheduleToolsUtils.class);

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private StudentDemandMapper studentDemandMapper;

	@Autowired
	private StudentDemandConnectMapper connectMapper;

//    //每天3：05执行，计算续课率
//    @Scheduled(cron = "0 05 03 ? * *")
//    public void testTasks() {
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
//    }

	// 每隔1小时执行一次,计算聘用率
	// @Scheduled(fixedRate = 1000*60*60)
	@Scheduled(initialDelay=1000*20,fixedRate = 1000*60*1)
	@Transactional
	public void calcueEmployRate() {
		
		List<TeacherVo> list = userInfoMapper.queryAllTeacherInfos();
		
		List<TeacherPo> teacherList = new ArrayList<TeacherPo>();

		for (TeacherVo t : list) {
			TeacherPo tPo = new TeacherPo();
			tPo.setTeacherId(Integer.valueOf(t.getTeacherId()));
			
			String resumptionRate = t.getResumptionRate().split("%")[0];
			
			//续课率大于等于60%,并且个人积分大于等于100，小于等于300时
			if (Double.valueOf(resumptionRate) >= 60 && Integer.valueOf(t.getTeacherPoints()) >= 100
					&& Integer.valueOf(t.getTeacherPoints()) <= 300) {
				tPo.setTeacherLevel("T1");
				tPo.setChargesStandard("120.00元/每课时");
			}
			
			//续课率大于等于60%,并且个人积分大于等于100，小于等于300时
			if (Double.valueOf(resumptionRate) >= 70 && Integer.valueOf(t.getTeacherPoints()) >= 301
					&& Integer.valueOf(t.getTeacherPoints()) <= 600) {
				tPo.setTeacherLevel("T2");
				tPo.setChargesStandard("160.00元/每课时");
			}
			
			//续课率大于等于60%,并且个人积分大于等于100，小于等于300时
			if (Double.valueOf(resumptionRate) >= 80 && Integer.valueOf(t.getTeacherPoints()) >= 601) {
				tPo.setTeacherLevel("T3");
				tPo.setChargesStandard("200.00元/每课时");
			}
			
			//续课率大于等于60%,并且个人积分大于等于100，小于等于300时
			if (Double.valueOf(resumptionRate) < 60 || Integer.valueOf(t.getTeacherPoints()) < 100) {
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
	
	
	/**
	 * 查询所有的第二天待试讲订单(快速请家教和指定教员的)，发出上课提醒
	 */
	@Scheduled(cron = "0 00 18 ? * *")
	@Transactional
	public void sendTrailStudentDemandMessageToTeacher() {
		
		StudentDemandPo sdcf = new StudentDemandPo();
		
		String orderTeachTime = DateUtil.getStandardDay(DateUtil.addDay(new Date() , 1));
		
		sdcf.setOrderStartDate(orderTeachTime+ " 00:00:00");
		sdcf.setOrderEndDate(orderTeachTime+" 23:59:59");
		sdcf.setStatus(2);
		
		List<StudentDemandVo> list = connectMapper.queryAllTrailDemandOrderListNotBegin(sdcf);
		
		if(list != null && list.size() > 0) {
			
			for(StudentDemandVo sdv:list) {
				
				String teachBranchName = sdv.getTeachBranchName();
				Date TeachTime = sdv.getOrderTeachTime();
				String classAddress = sdv.getDemandAddress();
				String teacherName = sdv.getTeachName();
				String studentName = sdv.getStudentName();
				String openId = sdv.getOpenId();
				
				
				JSONObject data = new JSONObject();
				
				Map<String, Object> keyMap1 = new HashMap<String, Object>();
				keyMap1.put("value", teachBranchName);
				// 课程名称
				data.put("thing1", keyMap1);
				
				Map<String, Object> keyMap2 = new HashMap<String, Object>();
				keyMap2.put("value", TeachTime);
				// 上课时间
				data.put("time5", keyMap2);
				
				Map<String, Object> keyMap3 = new HashMap<String, Object>();
				keyMap3.put("value", classAddress);
				// 上课地点
				data.put("thing6", keyMap3);
				
				Map<String, Object> keyMap4 = new HashMap<String, Object>();
				keyMap4.put("value", teacherName);
				// 上课教员
				data.put("name12", keyMap4);
				
				Map<String, Object> keyMap5 = new HashMap<String, Object>();
				keyMap5.put("value", studentName);
				// 上课学生
				data.put("name10", keyMap5);
				
				JSONObject sendRedPackRsult = SendWXMessageUtils.sendSubscribeMessage(openId, Constant.CLASS_BEGIN_MESSAGE, data);
				
				logger.info("发出试讲上课提醒的结果： " + sendRedPackRsult.getString("errcode") + " "
						+ sendRedPackRsult.getString("errmsg"));
			}
		}
		
	}
	
	/**
	 * 查询所有的正式订单，发出上课提醒
	 */
	@Scheduled(cron = "0 05 18 ? * *")
	@Transactional
	public void sendCommonStudentDemandMessageToTeacher() {
		
		StudentDemandPo sdcf = new StudentDemandPo();
		
		String orderTeachTime = DateUtil.getStandardDay(DateUtil.addDay(new Date() , 1));
		
		sdcf.setOrderStartDate(orderTeachTime+ " 00:00:00");
		sdcf.setOrderEndDate(orderTeachTime+" 23:59:59");
		sdcf.setStatus(4);
		
		List<StudentDemandVo> list = connectMapper.queryAllDemandOrderListNotBegin(sdcf);
		
		if(list != null && list.size() > 0) {
		
			for(StudentDemandVo sdv:list) {
				
				String teachBranchName = sdv.getTeachBranchName();
				Date TeachTime = sdv.getOrderTeachTime();
				String classAddress = sdv.getDemandAddress();
				String teacherName = sdv.getTeachName();
				String studentName = sdv.getStudentName();
				String openId = sdv.getOpenId();
				
				
				JSONObject data = new JSONObject();
				
				Map<String, Object> keyMap1 = new HashMap<String, Object>();
				keyMap1.put("value", teachBranchName);
				// 课程名称
				data.put("thing1", keyMap1);
				
				Map<String, Object> keyMap2 = new HashMap<String, Object>();
				keyMap2.put("value", TeachTime);
				// 上课时间
				data.put("time5", keyMap2);
				
				Map<String, Object> keyMap3 = new HashMap<String, Object>();
				keyMap3.put("value", classAddress);
				// 上课地点
				data.put("thing6", keyMap3);
				
				Map<String, Object> keyMap4 = new HashMap<String, Object>();
				keyMap4.put("value", teacherName);
				// 上课教员
				data.put("name12", keyMap4);
				
				Map<String, Object> keyMap5 = new HashMap<String, Object>();
				keyMap5.put("value", studentName);
				// 上课学生
				data.put("name10", keyMap5);
				
				JSONObject sendRedPackRsult = SendWXMessageUtils.sendSubscribeMessage(openId, Constant.CLASS_BEGIN_MESSAGE, data);
				
				logger.info("发出正式上课提醒的结果： " + sendRedPackRsult.getString("errcode") + " "
						+ sendRedPackRsult.getString("errmsg"));
			}
		}
	}

}
