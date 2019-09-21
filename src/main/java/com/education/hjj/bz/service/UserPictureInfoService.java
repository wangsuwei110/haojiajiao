package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.PictureVo;
import com.education.hjj.bz.formBean.PictureForm;

public interface UserPictureInfoService {
	
	PictureVo queryUserPictureInfoByTelephone(Integer teacherId);
	
	int insertUserPictureInfo(PicturePo picture);
	
	int updateUserPictureInfo(PicturePo picture);
	
	List<PictureVo> queryUserPicturesByType(PictureForm pictureForm);
	
	PictureVo queryUserPicturesDetail(PictureForm pictureForm);
	
	List<PictureVo> queryUserPicturesByteacherId(Integer teacherId);
}
