package com.education.hjj.bz.service.impl;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.ParameterPo;
import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.entity.vo.PictureVo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;
import com.education.hjj.bz.entity.vo.TeacherInfoPicturesVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.enums.ImagePath;
import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.formBean.TeacherInfoForm;
import com.education.hjj.bz.formBean.TeacherInfoReplenishForm;
import com.education.hjj.bz.formBean.UserInfoForm;
import com.education.hjj.bz.mapper.ParameterMapper;
import com.education.hjj.bz.mapper.UserInfoMapper;
import com.education.hjj.bz.mapper.UserPictureInfoMapper;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.StrUtils;
import com.education.hjj.bz.util.UploadFile;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${picture_url}")
	private String PICTURE_URL;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserPictureInfoMapper userPictureInfoMapper;

	@Autowired
	private ParameterMapper parameterMapper;

	@Override
	public int insertTeacherInfo(LoginForm LoginForm) {

		TeacherPo teacher = new TeacherPo();
		teacher.setTelephone(LoginForm.getLoginPhone());
		teacher.setOpenId(LoginForm.getOpenId());
		teacher.setSex(LoginForm.getGender());
		teacher.setPicture(LoginForm.getHeadPicture());
		teacher.setTeacherLevel("T0");

		teacher.setCreateTime(new Date());
		teacher.setCreateUser("admin");
		teacher.setUpdateTime(new Date());
		teacher.setUpdateUser("admin");

		int i = userInfoMapper.insertUserInfo(teacher);

		return i;
	}

	@Override
	@Transactional

	public int updateUserInfo(UserInfoForm userInfoForm) {

		if (userInfoForm != null) {
			String teacherId = userInfoForm.getTeacherId();
			String home = userInfoForm.getHome();
			String weiChar = userInfoForm.getWeiChar();
			String QQ = userInfoForm.getQqh();
			String name = userInfoForm.getTeacherName();
			String idCard = userInfoForm.getIdCard();
			String school = userInfoForm.getSchool();
			String major = userInfoForm.getMajor();
			String degree = userInfoForm.getDegree();
			String beginSchool = userInfoForm.getBeginSchoolTime();
			Integer isGraduate = userInfoForm.getIsGraduate();
			String tag = userInfoForm.getTag();
			String address = userInfoForm.getAddress();

			String type = userInfoForm.getType();

			String pictureTitle = userInfoForm.getPictureTitle();
			String pictureUrl = userInfoForm.getPictureUrl();
			String pictureDesc = userInfoForm.getPictureDesc();

			// 插入到图片表
			PicturePo picture = new PicturePo();
			picture.setTeacherId(Integer.valueOf(teacherId));
			picture.setPictureTitle(pictureTitle);

			if(pictureUrl != null && StringUtils.isNoneBlank(pictureUrl)) {
				if (pictureUrl.contains(File.separator)) {
					picture.setPictureUrl(pictureUrl);
				} else {
					picture.setPictureUrl(PICTURE_URL + File.separator + pictureUrl);
				}
			}
			
			logger.info("pictureUrl = {}" , pictureUrl);

			picture.setPictureDesc(pictureDesc);
			picture.setStatus(1);

			// 查询简历完成度
			TeacherVo tv = userInfoMapper.queryTeacherHomeInfos(Integer.valueOf(teacherId));
			Integer resumeComplete = tv.getResumeComplete();

			TeacherPo teacher = new TeacherPo();
			teacher.setTeacherId(Integer.valueOf(teacherId));
			teacher.setAuditStatus(1);// 只要信息更新就会简历变为审核中

			if (type != null && StringUtils.isNoneBlank(type)) {
				// 更新获奖证书
				if ("certificate".equalsIgnoreCase(type)) {
					if (tv.getTeacherCertificate() == null || StringUtils.isBlank(tv.getTeacherCertificate())) {
						resumeComplete += 10;
					}
					teacher.setTeacherCertificate("1");
					teacher.setResumeComplete(resumeComplete);
					picture.setPictureType(4);
				}

				// 更新教学经验
				if ("experience".equalsIgnoreCase(type)) {

					if (tv.getExperience() == null || StringUtils.isBlank(tv.getExperience())) {
						resumeComplete += 10;
					}
					picture.setPictureType(5);
					teacher.setExperience("1");
					teacher.setResumeComplete(resumeComplete);
				}
			}

			if (type == null || StringUtils.isBlank(type)) {
				// 更新基本信息
				if (name != null && StringUtils.isNoneBlank(name)) {
					teacher.setName(name);
					teacher.setHome(home);
					teacher.setWeiChar(weiChar);
					teacher.setQQ(QQ);
					teacher.setPicture(pictureUrl);
					teacher.setAddress(address);
					
					if (tv.getHome() == null || StringUtils.isBlank(tv.getHome())) {
						resumeComplete += 20;
						teacher.setResumeComplete(resumeComplete);
					}
					picture.setPictureType(1);
				}

				// 更新身份证
				if (idCard != null && StringUtils.isNoneBlank(idCard)) {
					teacher.setIdCard(idCard);
					teacher.setLogonStatus(1);

					if (tv.getIdCard() == null || StringUtils.isBlank(tv.getIdCard())) {
						resumeComplete += 20;
						teacher.setResumeComplete(resumeComplete);
					}
					picture.setPictureType(2);
				}

				// 更新学历信息
				if (major != null && StringUtils.isNoneBlank(major)) {
					teacher.setSchool(school);
					teacher.setMajor(major);
					teacher.setDegree(degree);
					teacher.setBeginSchoolTime(beginSchool);
					teacher.setIsGraduate(isGraduate);

					if (tv.getMajor() == null || StringUtils.isBlank(tv.getMajor())) {
						resumeComplete += 20;
						teacher.setResumeComplete(resumeComplete);
					}
					picture.setPictureType(3);
				}

				// 教员个人标签
				if (tag != null && StringUtils.isNoneBlank(tag)) {

					teacher.setTeacherTag(tag);

					if (tv.getTeacherTag() == null || StringUtils.isBlank(tv.getTeacherTag())) {
						resumeComplete += 20;
					}

					teacher.setResumeComplete(resumeComplete);
				}
			}

			int count = userPictureInfoMapper.queryUserPictureInfoByTelephone(picture);

			if ("certificate".equalsIgnoreCase(type) || "experience".equalsIgnoreCase(type)) {
				count = 0;
			}

			if ((count == 0 && tag == null) || (count == 0 && StringUtils.isBlank(tag))) {

				picture.setCreateTime(new Date());
				picture.setCreateUser("admin");
				picture.setUpdateTime(new Date());
				picture.setUpdateUser("admin");

				userPictureInfoMapper.insertUserOnePictureInfo(picture);

			}

			if ((count != 0 && tag == null) || (count != 0 && StringUtils.isBlank(tag))) {
				picture.setStatus(1);
				picture.setUpdateTime(new Date());
				picture.setUpdateUser("admin");
				userPictureInfoMapper.updateUserPictureInfo(picture);
			}

			// 更新教员信息表
			teacher.setUpdateTime(new Date());
			teacher.setUpdateUser("admin");

			int i = userInfoMapper.updateUserInfo(teacher);
			return i;
		}

		return -1;

	}

	@Override
	public Map<String, Object> queryTeacherInfoByTelephone(String telephone) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (StrUtils.isEmpty(telephone)) {

			logger.error("查询失败，请检查网络！");
			map.put("msg", "查询失败，请检查网络！");
			return map;
		}
		// 查询老师基本信息
		TeacherVo teacherInfo = userInfoMapper.queryTeacherInfoByTelephone(telephone);
		map.put("body", teacherInfo);

		String tag = teacherInfo.getTeacherTag();
		String[] tags = tag.split("|");

		// 查询个人标签
		List<ParameterPo> list = new ArrayList<ParameterPo>();

		for (String t : tags) {
			ParameterPo para = new ParameterPo();
			para.setParameterId(Integer.valueOf(t));
			list.add(para);
		}
		List<ParameterVo> listParameters = parameterMapper.queryParameterListsByTypes(list);
		map.put("body", listParameters);
		// 查询图片

		List<PictureVo> listPictures = userPictureInfoMapper.queryUserPicturesByteacherId(teacherInfo.getTeacherId());

		List<PictureVo> pictures = new ArrayList<PictureVo>();

		for (PictureVo p : listPictures) {

			String dataPath = p.getPictureUrl();

			String pictureName = dataPath.substring(dataPath.lastIndexOf('/') + 1);

			p.setPictureUrl(pictureName);

			pictures.add(p);
		}

		map.put("body", pictures);

		return map;
	}

	@Override
	@Transactional
	public int updateUserInfoByParameter(TeacherInfoForm teacherInfoForm) {
		
		logger.info("teacherInfoForm={}",teacherInfoForm.toString());
		
		TeacherPo teacher = new TeacherPo();
		
		if(teacherInfoForm.getTeacherTag() != null && StringUtils.isNotBlank(teacherInfoForm.getTeacherTag())) {
			teacher.setTeacherTag(teacherInfoForm.getTeacherTag());
		}

		if (teacherInfoForm.getTeachLevel() != null && StringUtils.isNotBlank(teacherInfoForm.getTeachLevel())) {
			teacher.setTeachLevel(teacherInfoForm.getTeachLevel());
		}

		if (teacherInfoForm.getTeachGrade() != null && StringUtils.isNotBlank(teacherInfoForm.getTeachGrade())) {
			teacher.setTeachGrade(teacherInfoForm.getTeachGrade());
		}

		if (teacherInfoForm.getTeachBrance() != null && StringUtils.isNotBlank(teacherInfoForm.getTeachBrance())) {
			teacher.setTeachBrance(Integer.valueOf(teacherInfoForm.getTeachBrance()));
		}

		if(teacherInfoForm.getTeachBranchSlave() != null && StringUtils.isNotBlank(teacherInfoForm.getTeachBranchSlave())) {
			teacher.setTeachBranchSlave(teacherInfoForm.getTeachBranchSlave());
		}
		if(teacherInfoForm.getTeachAddress() != null && StringUtils.isNotBlank(teacherInfoForm.getTeachAddress())) {
			teacher.setTeachAddress(teacherInfoForm.getTeachAddress());
		}
		if(teacherInfoForm.getName() != null && StringUtils.isNotBlank(teacherInfoForm.getName())) {
			teacher.setName(teacherInfoForm.getName());	
		}
		
		if(teacherInfoForm.getSchool() != null && StringUtils.isNotBlank(teacherInfoForm.getSchool())) {
			teacher.setSchool(teacherInfoForm.getSchool());
		}
		if(teacherInfoForm.getAddress() != null && StringUtils.isNotBlank(teacherInfoForm.getAddress())) {
			teacher.setAddress(teacherInfoForm.getAddress());
		}

		teacher.setTeacherId(Integer.valueOf(teacherInfoForm.getTeacherId()));

		if(teacherInfoForm.getTimeList() != null && teacherInfoForm.getTimeList().size() > 0 ) {
			teacher.setTeachTime(JSON.toJSONString(teacherInfoForm.getTimeList()));
		}
		
		if(teacherInfoForm.getVacationStatus() != null && StringUtils.isNotBlank(teacherInfoForm.getVacationStatus())) {
			teacher.setVacationStatus(Integer.valueOf(teacherInfoForm.getVacationStatus()));
		}
		
		teacher.setUpdateTime(new Date());
		teacher.setUpdateUser("admin");
		
		int i = userInfoMapper.updateUserInfo(teacher);

		return i;
	}

	@Override
	public Map<String, Object> queryTeacherAccount(String teacherId) {

		TeacherAccountVo teacherAccount = userInfoMapper.queryTeacherAccount(Integer.valueOf(teacherId));

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("accountMoney", teacherAccount.getAccountMoney());
		map.put("surplusMoney", teacherAccount.getSurplusMoney());

		String nowDate = DateUtil.getStandardDay(new Date());

		String lastedDate = DateUtil.getStandardDay(teacherAccount.getCreateTime());
		String calDays = DateUtil.remainDateToString(lastedDate, nowDate);
		map.put("calDays", calDays);

		return map;
	}

	@Override
	public TeacherAccountOperateLogVo queryUserAccountOperateLog(String teacherId, Integer type) {

		TeacherAccountOperateLogPo teacherAccountOperateLog = new TeacherAccountOperateLogPo();
		teacherAccountOperateLog.setPaymentPersonId(Integer.valueOf(teacherId));
		teacherAccountOperateLog.setPaymentType(type);

		TeacherAccountOperateLogVo teacherAccount = userInfoMapper.queryUserAccountOperateLog(teacherAccountOperateLog);

		return teacherAccount;
	}

	@Override
	public TeacherVo queryTeacherInfosByTelephone(String telephone) {
		TeacherVo teacherVo = userInfoMapper.queryTeacherInfoByTelephone(telephone);
		return teacherVo;
	}

	@Override
	public TeacherVo queryTeacherHomeInfos(String teacherId) {

		logger.info("teacherId = {}", teacherId);
		TeacherVo teacherVo = null;
		if (StringUtils.isNotEmpty(teacherId) && teacherId != null) {
			int tId = Integer.valueOf(teacherId);

			teacherVo = userInfoMapper.queryTeacherHomeInfos(tId);

		}

		return teacherVo;
	}

	@Override
	@Transactional
	public int auditUserIdCardInfo(TeacherInfoForm teacherInfoForm, MultipartFile files) {
		// TODO Auto-generated method stub

		String targetFilePath = ImagePath.IDCARD_PICTURE.getValue();

		String telephone = teacherInfoForm.getTelephone();
		String teacherId = teacherInfoForm.getTeacherId();

		TeacherPo teacher = new TeacherPo();

		Integer type = ImagePath.IDCARD_PICTURE.getType();

		String fullPaths = UploadFile.uploadIMG(files, targetFilePath);

		PicturePo p = new PicturePo();
		p.setTeacherId(Integer.valueOf(teacherId));
		p.setPictureType(type);

		int count = userPictureInfoMapper.queryUserPictureInfoByTelephone(p);

		PicturePo picture = new PicturePo();
		picture.setTeacherId(Integer.valueOf(teacherId));
		picture.setPictureType(2);
		picture.setPictureUrl(fullPaths);
		picture.setStatus(1);
		picture.setCreateTime(new Date());
		picture.setCreateUser(teacherInfoForm.getName());

		if (count == 0) {
			userPictureInfoMapper.insertUserOnePictureInfo(picture);
		} else {
			userPictureInfoMapper.updateUserPictureInfo(picture);
		}
		// 认证状态
		teacher.setLogonStatus(Integer.valueOf(teacherInfoForm.getLogonStatus()));
		teacher.setUpdateTime(new Date());
		teacher.setUpdateUser(teacherInfoForm.getName());
		teacher.setTeacherId(Integer.valueOf(teacherId));
		teacher.setIdCard(teacherInfoForm.getIdCard());
		int i = userInfoMapper.updateUserInfo(teacher);

		return i;
	}

	@Override
	public int auditTeacherInfo(TeacherInfoForm teacherInfoForm) {

		TeacherPo teacher = new TeacherPo();
		teacher.setTelephone(teacherInfoForm.getTelephone());
		teacher.setAuditStatus(teacherInfoForm.getAuditStatus());

		teacher.setUpdateTime(new Date());
		teacher.setUpdateUser(teacherInfoForm.getName());

		int i = userInfoMapper.insertUserInfo(teacher);

		return 0;
	}

	@Override
	public Map<String, Object> queryTeacherInfoByType(PicturePo picturePo) {

		List<TeacherInfoPicturesVo> tps = userInfoMapper.queryTeacherInfoByType(picturePo);

		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

		TeacherVo tv = new TeacherVo();

		for (TeacherInfoPicturesVo tp : tps) {

			List<String> pathList = new ArrayList<String>();

			Map<String, Object> pictureMap = new HashMap<String, Object>();

			if (tp != null && tp.getPictureUrl() != null && StringUtils.isNoneBlank(tp.getPictureUrl())) {

				String pictureUrls = tp.getPictureUrl();
				String[] urls = pictureUrls.split(",");

				for (String dataPath : urls) {
					String path = dataPath.substring(dataPath.lastIndexOf('/') + 1);
					pathList.add(path);
				}

				pictureMap.put("pictureTitle", tp.getPictureTitle());
				pictureMap.put("pictureUrl", pathList);
				pictureMap.put("pictureDesc", tp.getPictureDesc());

				lists.add(pictureMap);
			}
			tv.setIdCard(tp.getIdCard());
			tv.setSchool(tp.getSchool());
			tv.setMajor(tp.getMajor());
			tv.setIsGraduate(tp.getIsGraduate());
			tv.setDegree(tp.getDegree());
			tv.setBeginSchoolTime(tp.getBeginSchoolTime());
			tv.setTeacherTag(tp.getTeacherTag());
			tv.setLogonStatus(tp.getLogonStatus());

		}

		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("userInfos", tv);
		map.put("pictures", lists);

		return map;
	}

	@Override
	public int updateOpenId(String openId , int teacherId) {
		
		TeacherPo tp = new TeacherPo();
		
		tp.setTeacherId(teacherId);
		tp.setOpenId(openId);
		
		tp.setUpdateUser(String.valueOf(teacherId));
		tp.setUpdateTime(new Date());
		
		int  i = userInfoMapper.updateUserInfo(tp);
		
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public int updateUserInfos(TeacherInfoReplenishForm teacherInfoReplenishForm) {
		
		if(teacherInfoReplenishForm != null) {
			
			String teacherId = teacherInfoReplenishForm.getTeacherId();
			String name = teacherInfoReplenishForm.getName();
			String address = teacherInfoReplenishForm.getAddress();
			String school = teacherInfoReplenishForm.getSchool();
			Integer sex = teacherInfoReplenishForm.getSex();
			
			Integer teachBrance = null;
			if(teacherInfoReplenishForm.getTeachBrance() != null && 
					StringUtils.isNoneBlank(teacherInfoReplenishForm.getTeachBrance())) {
				
				teachBrance = Integer.valueOf(teacherInfoReplenishForm.getTeachBrance());
			}
			
			String teachBranchSlave = teacherInfoReplenishForm.getTeachBranchSlave();
			String telephone = teacherInfoReplenishForm.getTelephone();
			String teachGrade = teacherInfoReplenishForm.getTeachGrade();
			
			TeacherPo teacher = new TeacherPo();
			teacher.setTeachBrance(teachBrance);
			teacher.setTeachBranchSlave(teachBranchSlave);
			teacher.setSex(sex);
			teacher.setSchool(school);
			teacher.setName(name);
			teacher.setAddress(address);
			teacher.setTelephone(telephone);
			teacher.setTeacherId(Integer.valueOf(teacherId));
			teacher.setTeachGrade(teachGrade);
			teacher.setUpdateTime(new Date());
			teacher.setUpdateUser(teacherId);
			
			int i = userInfoMapper.updateUserInfo(teacher);
			
			return i;
		}
		
		return -1;
	}

	@Override
	public List<TeacherVo> queryAllTeacherInfos() {
		
		List<TeacherVo> list = userInfoMapper.queryAllTeacherInfos();
		
		// 查询个人标签
		List<ParameterPo> parameterlist = new ArrayList<ParameterPo>();
				
		for(TeacherVo t:list) {
			
			String date = t.getBeginSchoolTime();
			
			try {
				t.setBeginSchoolTime(DateUtil.caculDegree(date));
			} catch (ParseException e) {
				logger.info("转换入学日期到年级失败......");
				e.printStackTrace();
			}
			
			String branchMaster = t.getTeachBrance();
			
			ParameterPo par = new ParameterPo();
			par.setParameterId(Integer.valueOf(branchMaster));
			parameterlist.add(par);
			
			List<ParameterVo> paraList = parameterMapper.queryParameterListsByTypes(parameterlist);
			
			for(ParameterVo po: paraList) {
				t.setTeachBrance(po.getName());
			}
			
			
			String [] branchArray = t.getTeachBranchSlave().split(",");
			
			for(String branch: branchArray) {
				ParameterPo para = new ParameterPo();
				para.setParameterId(Integer.valueOf(branch));
				parameterlist.add(para);
			}
			
			List<ParameterVo> parameterList = parameterMapper.queryParameterListsByTypes(parameterlist);
			
			for(ParameterVo po: parameterList) {
				t.setTeachBranchSlave(po.getName());
			}
		}
		
		return list;
	}

}
