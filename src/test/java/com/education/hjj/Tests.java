package com.education.hjj;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.entity.vo.WeekTimeVo;
import com.education.hjj.bz.formBean.*;
import com.education.hjj.bz.mapper.*;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.weixinUtil.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@MapperScan("com.commonrail.network.data.mapper")
@SpringBootTest
public class Tests {

	@Autowired
	private UserAccountLogMapper userAccountLogMapper;

	@Autowired
	private StudentDemandConnectMapper mapper;

	@Autowired
	private DemandLogMapper demandLogMapper;

	@Autowired
	private DemandCourseInfoMapper demandCourseInfoMapper;

	@Autowired
	private StudentDemandMapper studentDemandMapper;

	@Autowired
	private StudentDemandConnectMapper connectMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private VerificationCodeMapper codeMapper;

	@Test
	public void insert() {
		VerificationCodeForm form = new VerificationCodeForm();
		form.setPhone("13861726854");
		form.setCode("1234");
		form.setCreateTime(new Date());
		codeMapper.insertCode(form);

//		StudentDemandForm demandForm = new StudentDemandForm();
//		demandForm.setDemandId(185);
//		demandForm.setTimeRange("[{'week':4,'time':2}]");
//		demandForm.setWeekNum(1);
//		demandForm.setTeacherId(9);
//		demandForm.setOrderMoney("0.01");
//		Date date = new Date();
//
//		// 插入一条日志信息，记录结课/支付记录
//		List<DemandCourseInfoForm> courseInfoFormList = new ArrayList<>();
//		StudentDemandVo demandVo = studentDemandMapper.findStudentDemandInfo(demandForm);
//
//		// 记录上个订单的信息
//		DemandLogForm logForm = new DemandLogForm();
//		logForm.setCreateTime(date);
//		logForm.setMark(JSON.toJSONString(demandVo));
//		logForm.setDemandId(demandForm.getDemandId());
//		logForm.setCreateUser(demandVo.getStudentId().toString());
//
//		demandLogMapper.insert(logForm);
//		Integer weekDay = DateUtil.getWeekOfDate(date);
//		demandForm.setCurrentWeekDay(weekDay);
//
//		// 修改当前订单成新订单
//		demandForm.setOrderType(2);
//		demandForm.setOrderMoney(demandForm.getOrderMoney());
//		demandForm.setPaymentStreamId("asdf");
//		demandForm.setOrderStart(date);
//		demandForm.setUpdateTime(date);
//		demandForm.setCreateTime(date);
//		Long sid = studentDemandMapper.updateOldDemandToNew(demandForm);
//
//		// 修改订单状态至续课状态：状态变成4
//		StudentDemandConnectForm connectForm = new StudentDemandConnectForm();
//		connectForm.setTeacherId(demandVo.getTeacherId());
//		connectForm.setDemandId(demandVo.getSid());
//		connectForm.setStatus(4);
//		connectMapper.updateByDemandId(connectForm);
//
//		// 试讲通过，则将其它报名的订单全部改成5
//		connectForm.setTeacherId(demandVo.getTeacherId());
//		connectForm.setStatus(5);
//		connectMapper.updateStatusAndPass(connectForm);
//
//		Integer teacherId = demandVo.getTeacherId();
//
//		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);
//		// 更新教员对所有报名订单的数量
//		TeacherPo teacherPo = new TeacherPo();
//
//		teacherPo.setEmployCount(teacherVo.getEmployCount() + 1);
//
//		int chooseCount = teacherVo.getChooseCount();
//
//		double newRate = (teacherVo.getEmployCount() + 1) / chooseCount;
//
//		BigDecimal bg = new BigDecimal(newRate).setScale(2, RoundingMode.DOWN);
//		// 更新该教员的聘用率
//		teacherPo.setEmployRate(bg);
//
//		userInfoMapper.updateUserInfo(teacherPo);
//
//		StudentDemandVo demand = demandVo;
//
//		// 根据订单插入每个节课时
//		for (int i = 0; i < demandForm.getWeekNum(); i++) {
//			List<WeekTimeVo> list = JSON.parseArray(demandForm.getTimeRange(), WeekTimeVo.class);
//
//			final Integer weekNum = i;
//			list.forEach(w -> {
//				DemandCourseInfoForm courseInfoForm = new DemandCourseInfoForm();
//				if (weekDay >= w.getWeek()) {
//					Integer week = DateUtil.getWeekOfDate(DateUtil.addDay(date, 7 + 7*weekNum));
//					courseInfoForm.setOrderTeachTime(DateUtil.addDay(date, 7 + 7*weekNum + w.getWeek() - week));
//				} else {
//					courseInfoForm.setOrderTeachTime(DateUtil.addDay(date, w.getWeek() - weekDay + (7*weekNum)));
//				}
//
//				courseInfoForm.setStatus(0);
//				courseInfoForm.setDeleteStatus(0);
//				courseInfoForm.setCreateTime(date);
//				courseInfoForm.setUpdateTime(date);
//				courseInfoForm.setWeekNum(w.getWeek());
//				courseInfoForm.setTimeNum(w.getTime());
//				courseInfoForm.setDemandId(demandForm.getDemandId());
//				courseInfoForm.setStudentId(demand.getStudentId());
//				courseInfoForm.setTeacherId(demand.getTeacherId());
//				courseInfoForm.setCreateUser(demand.getStudentId().toString());
//
//				courseInfoFormList.add(courseInfoForm);
//			});
//		}
//
//		demandCourseInfoMapper.insert(courseInfoFormList);
//
//		// 插入一条日志信息，记录结课/支付记录
//		TeacherAccountOperateLogPo paymentLog = new TeacherAccountOperateLogPo();
//		paymentLog.setOrderId("1234wer");
//		paymentLog.setPaymentStreamId("awerasdf");
//		paymentLog.setPaymentPersonId(demandVo.getStudentId());
//		paymentLog.setPaymentPersonName(demandVo.getStudentName());
//		paymentLog.setPaymentType(3);
//
//		// 统计了课时
//		List<WeekTimeVo> list = JSON.parseArray(demandForm.getTimeRange(), WeekTimeVo.class);
//		paymentLog.setPaymentDesc("购买"+ demandForm.getWeekNum() + "周" + demandForm.getWeekNum() * list.size() + "课时");
//		paymentLog.setStatus(1);
//		paymentLog.setCreateTime(date);
//		paymentLog.setCreateUser(demandVo.getStudentName());
//		paymentLog.setPaymentAccount(new BigDecimal(demandForm.getOrderMoney()));
//		paymentLog.setUpdateTime(date);
//		paymentLog.setUpdateUser(demandVo.getStudentName());
//		userAccountLogMapper.insertUserAccountLog(paymentLog);
	}



	public static void main(String[] args) throws Exception {

		System.out.println(RandomUtils.generateMixString(32));


		String st = "100.01元/每课时";

		System.out.println(Double.valueOf(st.split("元")[0]));


//		 // TODO 自动生成方法存根 
//		  //日期相减算出秒的算法 
//		  Date date1 = new SimpleDateFormat("yyyy-mm-dd").parse("2018-09-01"); 
//		  Date date2 = new SimpleDateFormat("yyyy-mm-dd").parse("2019-06-01"); 
//		  
//		  long l = date1.getTime()-date2.getTime()>0 ? date1.getTime()-date2.getTime(): 
//		   date2.getTime()-date1.getTime(); 
//		  
//		  System.out.println(l/1000+"秒"); 
//		  
//		  //日期相减得到相差的日期 
//		  long day = (date1.getTime()-date2.getTime())/(24*60*60*1000)>0 ? (date1.getTime()-date2.getTime())/(24*60*60*1000): 
//		   (date2.getTime()-date1.getTime())/(24*60*60*1000); 
//		  
//		  System.out.println("相差的日期: " +day); 
		  
//
//		  String aa="2018-09-01";
//
//		  String bb="2019-09-20";
//
//
//		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//		  long b = format.parse(bb).getTime();
//		  long a = format.parse(aa).getTime();
//
//		  long c = (b-a)/(24 * 60 * 60 * 1000);
//
//		  System.out.println(c);
//
//
//		  String abc = "/haojiajiao/share/IMG/20190916233129-70f02952818c49c5adb5257bc5e8534d.jpg,/haojiajiao/share/IMG/20190916233134-09ed352f740547eda5dc053299cd2cb5.jpg";
//
//		  System.out.println(abc.replaceAll("/haojiajiao/share/IMG/", ""));
	} 
}
