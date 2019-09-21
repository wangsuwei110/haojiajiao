package com.education.hjj.bz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.education.hjj.bz.entity.ComplaintSuggestionPo;
import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.vo.PictureVo;
import com.education.hjj.bz.enums.ImagePath;
import com.education.hjj.bz.formBean.ComplaintSuggestionForm;
import com.education.hjj.bz.formBean.PictureForm;
import com.education.hjj.bz.mapper.ComplaintSuggestionMapper;
import com.education.hjj.bz.mapper.UserPictureInfoMapper;
import com.education.hjj.bz.service.ComplaintSuggestionService;
import com.education.hjj.bz.util.UploadFile;

@Service
@Transactional
public class ComplaintSuggestionServiceImpl implements ComplaintSuggestionService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ComplaintSuggestionMapper complaintSuggestionMapper;

	@Autowired
	private UserPictureInfoMapper userPictureInfoMapper;

	@Override
	public int addComplaintSuggestion(ComplaintSuggestionForm complaintSuggestionForm, MultipartFile files , Integer type, PictureForm pictureForm) {
		
		String targetFilePath = "";
		
		String telephone = complaintSuggestionForm.getTelephone();
		
		String teacherId = complaintSuggestionForm.getPersonId();
		
		logger.info("telephone={}",telephone);
		if(type != null && type == ImagePath.COMPLAINT_SUGGESTION.getType()) {
			targetFilePath =  ImagePath.COMPLAINT_SUGGESTION.getValue();
		}
		//先上传图片
		String fullPaths = UploadFile.uploadIMG(files, targetFilePath);
		
		List<PicturePo> list = new ArrayList<PicturePo>();


		PicturePo picture = new PicturePo();
		picture.setTeacherId(Integer.valueOf(teacherId));
		picture.setPictureType(type);
		picture.setPictureTitle(pictureForm.getPictureTitle());
		picture.setPictureUrl(fullPaths);
		picture.setPictureDesc(pictureForm.getPictureDesc());
		picture.setStatus(1);
		picture.setCreateTime(new Date());
		picture.setCreateUser(complaintSuggestionForm.getPersonId());
		picture.setUpdateTime(new Date());
		picture.setUpdateUser(complaintSuggestionForm.getPersonId());
		list.add(picture);
			
		userPictureInfoMapper.insertUserPictureInfo(list);
		
		List<PictureVo> pictures=userPictureInfoMapper.queryUserPicturesByteacherId(Integer.valueOf(teacherId));
		StringBuilder sb=new StringBuilder();
		for(PictureVo p:pictures) {
			sb.append(p.getPictureId());
			sb.append(",");
		}

		
		
		ComplaintSuggestionPo complaintSuggestionPo = new ComplaintSuggestionPo();
		complaintSuggestionPo.setPersonId(Integer.valueOf(complaintSuggestionForm.getPersonId()));
		complaintSuggestionPo.setType(Integer.valueOf(complaintSuggestionForm.getType()));
		complaintSuggestionPo.setContent(complaintSuggestionForm.getContent());
		complaintSuggestionPo.setTelephone(complaintSuggestionForm.getTelephone());
		complaintSuggestionPo.setPictures(sb.toString());
		complaintSuggestionPo.setCreateTime(new Date());
		complaintSuggestionPo.setCreateUser(complaintSuggestionForm.getPersonId());
		complaintSuggestionPo.setUpdateTime(new Date());
		complaintSuggestionPo.setUpdateUser(complaintSuggestionForm.getPersonId());
		
		int i = complaintSuggestionMapper.addComplaintSuggestion(complaintSuggestionPo);
		
		return i;
	}

}
