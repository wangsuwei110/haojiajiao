package com.education.hjj.bz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.PictureVo;
import com.education.hjj.bz.formBean.PictureForm;
import com.education.hjj.bz.mapper.UserPictureInfoMapper;
import com.education.hjj.bz.service.UserPictureInfoService;

@Service
@Transactional
public class UserPictureInfoServiceImpl implements UserPictureInfoService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserPictureInfoMapper userPictureInfoMapper;

	@Override
	public PictureVo queryUserPictureInfoByTelephone(Integer teacherId) {
		
		return null;
	}

	@Override
	public int insertUserPictureInfo(PicturePo picture) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUserPictureInfo(PicturePo picture) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PictureVo> queryUserPicturesByType(PictureForm pictureForm) {
		
		String teacherId = pictureForm.getTeacherId();
		
		Integer tid = null;
		
		if(teacherId !=null && StringUtils.isNoneBlank(teacherId)) {
			tid = Integer.valueOf(teacherId);
		}
		
		Integer pictureType = pictureForm.getPictureType();
		
		PicturePo pp = new PicturePo();
		
		pp.setTeacherId(tid);
		pp.setPictureType(pictureType);
		
		List<PictureVo> lists = userPictureInfoMapper.queryUserPicturesByType(pp);
		
		List<PictureVo> pictures = new ArrayList<PictureVo>();
		
		for(PictureVo p:lists) {
			
			String dataPath = p.getPictureUrl();
			
			String pictureName = dataPath.substring(dataPath.lastIndexOf('/')+1);
			
			p.setPictureUrl(pictureName);
			
			pictures.add(p);
		}
		
		return pictures;
	}

	@Override
	public PictureVo queryUserPicturesDetail(PictureForm pictureForm) {
		
		Integer pictureId = pictureForm.getPictureId();
		
		PictureVo pictureVo = userPictureInfoMapper.queryUserPicturesDetail(pictureId);
		
		
		String dataPath = pictureVo.getPictureUrl();
		
		String pictureName = dataPath.substring(dataPath.lastIndexOf('/')+1);
		
		pictureVo.setPictureUrl(pictureName);
		
		return pictureVo;
	}

	@Override
	public List<PictureVo> queryUserPicturesByteacherId(Integer teacherId) {
		List<PictureVo> list = userPictureInfoMapper.queryUserPicturesByteacherId(teacherId);
		return list;
	}

}
