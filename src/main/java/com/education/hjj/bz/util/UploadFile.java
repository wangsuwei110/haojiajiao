package com.education.hjj.bz.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

	private static final Logger logger = LoggerFactory.getLogger(UploadFile.class);

	public static List<String> uploadIMG(MultipartFile[] files, Integer type, String targetFilePath) {

		logger.info("tyep={}, targetFilePath={}", type, targetFilePath);

		List<String> list = new ArrayList<String>();

		for(MultipartFile file:files) {

		// 获取原始名字
		String fileName = file.getOriginalFilename();
		// 获取后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));

		// boolean isImage = RegUtils.isIMG(suffixName);
		boolean isImage = true;
		if (!isImage) {
			logger.error("the transfer file is not a image!");
			return list;
		}

		// 文件保存路径
		File dest = new File(targetFilePath);
		if (!dest.exists()) {
			dest.mkdirs();
		}
		String dateName = DateUtil.getDateString(new Date());
		// 文件重命名,防止重复,日期+UUID+文件名
		fileName = dateName + "-" + UUID.randomUUID().toString().replaceAll("-", "") + suffixName;

		InputStream inputStream = null;

		try {
			inputStream = file.getInputStream();
			
			logger.info("begin rewrite picture infos");
			
			resize(inputStream, fileName, targetFilePath);
		} catch (Exception e) {
			logger.error("the transfer file is not a image!");
			logger.info("the exception is :{}" , e);
			return list;
		}

		String fullPath = targetFilePath +File.separator+ fileName;
		
		logger.info("上传文件路径：{}", fullPath);
		
		list.add(fullPath);
		}
		return list;
	}

	public static void resize(InputStream inputStream, String fileName, String uploadDir) throws Exception {
		if (inputStream == null) {
			return;
		}
		BufferedImage src = ImageIO.read(inputStream);
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		BufferedImage newImg = null;
		// 判断输入图片的类型
		switch (src.getType()) {
		case 13:
			// png,gif
			newImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_4BYTE_ABGR);
			break;
		default:
			newImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
			break;
		}
		Graphics2D g = newImg.createGraphics();
		// 从原图上取颜色绘制新图
		g.drawImage(src, 0, 0, old_w, old_h, null);
		g.dispose();
		// 根据图片尺寸压缩比得到新图的尺寸
		newImg.getGraphics().drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
		File newFile = new File(uploadDir +File.separator+ fileName);
		
		String endName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		
		logger.info("the endName is {} , uploadDir = {} , fileName = {}" , endName , uploadDir , fileName);
		
		logger.info("the newImg is {} , newFile = {} " , newImg , newFile);
		
		ImageIO.setUseCache(false);
		
		ImageIO.write(newImg, endName, newFile);
		
		
	}

	/**
	 * 单图片上传接口
	 * 
	 * @param file
	 * @param targetFilePath
	 * @return
	 */
	public static String uploadIMG(MultipartFile file, String targetFilePath) {

		logger.info("targetFilePath={}", targetFilePath);

		// 获取原始名字
		String fileName = file.getOriginalFilename();
		
		logger.info("上传图片原始名字={}", fileName);
		
		// 获取后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));

		boolean isImage = RegUtils.isIMG(fileName);
		
		logger.info("是否是图片校验结果={}", isImage);

		if (!isImage) {
			logger.error("the transfer file is not a image!");
			return null;
		}

		// 文件保存路径
		File dest = new File(targetFilePath);
		if (!dest.exists()) {
			dest.mkdirs();
		}
		String dateName = DateUtil.getDateString(new Date());
		// 文件重命名,防止重复,日期+UUID+文件名
		fileName = dateName + "-" + UUID.randomUUID().toString().replaceAll("-", "") + suffixName;

		InputStream inputStream = null;

		try {
			inputStream = file.getInputStream();
			resize(inputStream, fileName, targetFilePath);
		} catch (Exception e) {
			logger.error("the transfer file is not a image!");
			logger.info("the exception is {}" , e);
			return null;
		}

		String fullPath = targetFilePath +File.separator+ fileName;

		logger.info("上传后的图片全路径={}", fullPath);
		
		return fullPath;
	}

}
