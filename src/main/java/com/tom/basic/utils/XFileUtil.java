package com.tom.basic.utils;

import java.io.File;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;


public class XFileUtil {

	public static String fetchFileExtension(String fileName) {
		if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
			return "";
		}

		String[] fileNameEle = fileName.split("\\.");
		if (fileNameEle.length == 1) {
			return "";
		}
		return "." + fileNameEle[fileNameEle.length - 1];
	}

	public static String fetchAbsoluteName(String fileName) {
		if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
			return fileName;
		}
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	
	public static File getUploadTempDir(String FOLDER_PATH_UPLOAD) {
		File parentDir = new File(FOLDER_PATH_UPLOAD);
		File uploads = createDir(parentDir, "");
		return uploads;
	}
	
	public static File getUploadDirByName(String imgFile,String FOLDER_PATH_UPLOAD) {
		File parentDir = new File(FOLDER_PATH_UPLOAD);
		File uploads = createDir(parentDir, FOLDER_PATH_UPLOAD+ "/img/");
		File nowUploads = createDir(uploads, imgFile);
		return nowUploads;
	}
	
	/**
	 * 在parent目录下创建dirName的目录
	 * @param parent
	 * @param dirName
	 * @return
	 */
	private static File createDir(File parent, String dirName) {
		File f = new File(parent, dirName);
		if (!f.exists()) {
			f.mkdir();
		}
		if (f.exists() && f.isFile()) {
			f.delete();
			f.mkdir();
		}
		return f;
	}
	
	public static boolean isLocalGifImg(String fileSuffix){
		return StringUtils.equalsIgnoreCase("gif", fileSuffix);
	}
	
	public static String getFileSize(File file){
		DecimalFormat df = new DecimalFormat("#.00");
		long filesize = file.length();
		if (filesize < 1024) {
			return df.format((double) filesize) + "B";
		} else if (filesize < 1048576) {
			return df.format((double) filesize / 1024) + "KB";
		} else if (filesize < 1073741824) {
			return df.format((double) filesize / 1048576) + "MB";
		} else {
			return df.format((double) filesize / 1073741824) + "GB";
		}
	}
	
	public static long getFileLength(File file){
		return file.length();
	}
	/**
	 * 保存小图片
	 * @param nf
	 * @return
	 */
//	public static File getThumbFile(File nf){
//		String currentImgName = FilenameUtils.getBaseName(nf.getName());
//		String fileSuffix = FilenameUtils.getExtension(nf.getName());
//		if(null != nf && nf.exists() && FileUtil.getFileLength(nf) > 1000*30){
//			//需要保存小图片
//			File thumbnailImg = new File(nf.getParent(), currentImgName + "_s." + fileSuffix);
//			if(FileUtil.isLocalGifImg(fileSuffix)){
//				CreateGifThumbnail.getGifImage(nf, thumbnailImg);
//			} else {
//				CreateThumbnail ct = new CreateThumbnail(nf.getAbsolutePath());
//				ct.getThumbnail(CreateGifThumbnail.height, CreateThumbnail.VERTICAL);
//				ct.saveThumbnail(thumbnailImg, fileSuffix);
//			}
//			return thumbnailImg;
//		}
//		return nf;
//	}
	
	
	static int PHOTO_HEIGHT = 240;
//	public static File getPhotoThumbFile(File nf){
//		String currentImgName = FilenameUtils.getBaseName(nf.getName());
//		String fileSuffix = FilenameUtils.getExtension(nf.getName());
//		if(null != nf && nf.exists() && FileUtil.getFileLength(nf) > 1000*30){
//			//需要保存小图片
//			File thumbnailImg = new File(nf.getParent(), currentImgName + "_s." + fileSuffix);
//			if(FileUtil.isLocalGifImg(fileSuffix)){
//				CreateGifThumbnail.getGifImageByHeight(nf, thumbnailImg, PHOTO_HEIGHT);
//			} else {
//				CreateThumbnail ct = new CreateThumbnail(nf.getAbsolutePath());
//				ct.getThumbnail(PHOTO_HEIGHT, CreateThumbnail.VERTICAL);
//				ct.saveThumbnail(thumbnailImg, fileSuffix);
//			}
//			return thumbnailImg;
//		}
//		return nf;
//	}
	
	
}
