package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.vo.PictureVo;

@Mapper
public interface UserPictureInfoMapper {

	Integer queryUserPictureInfoByTelephone(PicturePo picturePo);
	
	int insertUserPictureInfo(List<PicturePo> list);
	
	int insertUserOnePictureInfo(PicturePo picture);
	
	int updateUserPictureInfo(PicturePo picture);
	
	PictureVo queryUserPictureByteacherId(Integer teacherId);
	
	List<PictureVo> queryUserPicturesByteacherId(Integer teacherId);
	
	List<PictureVo> queryUserPicturesByType(PicturePo pp);
	
	PictureVo queryUserPicturesDetail(Integer pictureId);
}
