package com.kevin.service.pub;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;


public interface IFileService {
	
	/**
	 * 允许上传的Excel 的MIME类型、后缀、大小
	 * @param file
	 * @return
	 */
	public String acceptExcel(MultipartFile file);
	
	/**
	 * 允许上传的img 的MIME类型、后缀、大小
	 * @param file
	 * @return
	 */
	public String acceptImg(MultipartFile file);
	
	/**
	 * 允许上传的文件 的MIME类型、后缀、大小
	 * @param file
	 * @return
	 */
	public String acceptFile(MultipartFile file);
	

	/**
	 * 上传文件到目录
	 * @param multipartFile 文件
	 * @param folderName  目录
	 * @param oldFileUrl  删除目录
	 * @return
	 */
	public String uploadFile(MultipartFile multipartFile, String folderName, String oldFileUrl);

	/**
	 * 删除文件
	 * @param oldImgUrl 要删除的图片的地址
	 * @return
	 */
	public String deleteFile(String oldImgUrl);
	
	
	/**
	 * 下载文件
	 * @param file
	 * @param fileName
	 * @param response
	 */
	public void downFile(File file, String fileName, final HttpServletResponse response);
}
