package com.kevin.service.pub.impl;

import com.kevin.common.GlobalConstant.GlobalConstant;
import com.kevin.common.listener.ServletContextListenerImpl;
import com.kevin.service.pub.IFileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FileServiceImpl implements IFileService {

	private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public String acceptExcel(MultipartFile file) {
		List<String> mimeList = new ArrayList<String>();
		if (StringUtils.isNotBlank(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_EXCEL_MIME)))) {
			mimeList = Arrays.asList(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_EXCEL_MIME)).split(GlobalConstant.COMMA));
		}
		List<String> suffixList = new ArrayList<String>();
		if (StringUtils.isNotBlank(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_EXCEL_SUFFIX)))) {
			suffixList = Arrays.asList(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_EXCEL_SUFFIX).toLowerCase()).split(GlobalConstant.COMMA));
		}
		String fileName = file.getOriginalFilename();// 文件名
		String suffix = fileName.substring(fileName.lastIndexOf(GlobalConstant.DOT)).toLowerCase();// 后缀名
		if (!suffixList.contains(suffix)) {
			return "不允许上传的Excel文件后缀：" + suffix + "，请咨询系统管理员。";
		}
		String contentType = file.getContentType();// MIME类型;
		// application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
		if (!mimeList.contains(contentType)) {
			return "不允许上传的Excel文件MIME类型：" + contentType + "，请咨询系统管理员。";
		}
		long limitSize = Long.parseLong(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_FILE_SIZE_LIMIT)));// 大小限制
		if (file.getSize() > limitSize * GlobalConstant.FILE_HEX_TWO_POWER) {
			return GlobalConstant.FILE_SIZE_EXCEED + limitSize + GlobalConstant.FILE_UNIT_M;
		}
		return GlobalConstant.Y;// 可执行保存
	}

	@Override
	public String acceptImg(MultipartFile file) {
		List<String> mimeList = new ArrayList<String>();
		// 判断image 的MIME类型
		if (StringUtils.isNotBlank(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_IMAGE_MIME)))) {
			mimeList = Arrays.asList(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_IMAGE_MIME)).split(GlobalConstant.COMMA));
		}
		// 判断image 的后缀名
		List<String> suffixList = new ArrayList<String>();
		if (StringUtils.isNotBlank(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_IMAGE_SUFFIX)))) {
			suffixList = Arrays.asList(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_IMAGE_SUFFIX).toLowerCase()).split(GlobalConstant.COMMA));
		}
		String fileName = file.getOriginalFilename();// 文件名
		String suffix = fileName.substring(fileName.lastIndexOf(GlobalConstant.DOT)).toLowerCase();// 后缀名
		if (!suffixList.contains(suffix)) {
			return "不允许上传的图片文件后缀：" + suffix + "，请咨询系统管理员。";
		}
		String contentType = file.getContentType();// MIME类型;
		// application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
		if (!mimeList.contains(contentType)) {
			return "不允许上传的图片文件MIME类型：" + contentType + "，请咨询系统管理员。";
		}
		// 允许上传文件的大小限制
		long limitSize = Long.parseLong(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_IMAGE_SIZE_LIMIT)));
		if (file.getSize() > limitSize * GlobalConstant.FILE_HEX_TWO_POWER) {
			return GlobalConstant.IMAGE_SIZE_EXCEED + limitSize + GlobalConstant.FILE_UNIT_M;
		}
		return GlobalConstant.Y;// 可执行保存
	}

	@Override
	public String acceptFile(MultipartFile file) {
		List<String> mimeList = new ArrayList<String>();
		// 判断文件 的后缀名
		List<String> suffixList = new ArrayList<String>();
		if (StringUtils.isNotBlank(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_FILE_MIME)))) {
			suffixList = Arrays.asList(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_FILE_SUFFIX).toLowerCase()).split(GlobalConstant.COMMA));
		}
		String fileName = file.getOriginalFilename();// 文件名
		String suffix = fileName.substring(fileName.lastIndexOf(GlobalConstant.DOT)).toLowerCase();// 后缀名
		if (!suffixList.contains(suffix)) {
			return "不允许上传的文件后缀：" + suffix + "，请咨询系统管理员。";
		}
		String contentType = file.getContentType();// MIME类型;
		// application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
		if (!mimeList.contains(contentType)) {
			return "不允许上传的文件MIME类型：" + contentType + "，请咨询系统管理员。";
		}
		// 允许上传文件的大小限制
		long limitSize = Long.parseLong(StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.ACCEPT_FILE_SIZE_LIMIT)));
		if (file.getSize() > limitSize * GlobalConstant.FILE_HEX_TWO_POWER) {
			return GlobalConstant.FILE_SIZE_EXCEED + limitSize + GlobalConstant.FILE_UNIT_M;
		}
		return GlobalConstant.Y;// 可执行保存
	}

	@Override
	public String uploadFile(MultipartFile multipartFile, String folderName, String oldFileUrl) {
		if (multipartFile == null) {
			return null;
		}
		// 创建路径并上传图片
		String newDir = StringUtils.defaultString(ServletContextListenerImpl.getSysCfg(GlobalConstant.UPLOAD_BASE_DIR)) + File.separator + folderName;
		// String newDir =
		// "F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/ygj/temp";
		String fileName = multipartFile.getOriginalFilename();// 文件名
		// String fileSuffix =
		// fileName.substring(fileName.lastIndexOf(GlobalConstant.DOT));//文件后缀
		fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf(GlobalConstant.DOT));
		/* 创建目录 */
		File fileDir = new File(newDir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File newFile = new File(fileDir, fileName);
		try {
			multipartFile.transferTo(newFile);
		} catch (Exception e) {
			logger.debug("-----> catch Exception:" + e.getMessage());
		} finally {

		}
		// 删除原文件
		if (StringUtils.isNotBlank(oldFileUrl)) {
			deleteFile(oldFileUrl);
		}
		String uploadPath = folderName + File.separator + fileName;
		// String uploadPath = folderName + "/" + fileName;
		return uploadPath;
	}

	@Override
	public String deleteFile(String oldFileUrl) {
		if (StringUtils.isNotBlank(oldFileUrl)) {
			try {
				String uploadBaseDir = ServletContextListenerImpl.getSysCfg(GlobalConstant.UPLOAD_BASE_DIR);
				String oldFile = StringUtils.defaultString(uploadBaseDir) + File.separator + oldFileUrl;
				File imgFile = new File(oldFile);
				if (imgFile.exists()) {
					imgFile.delete();
				}
				return GlobalConstant.DELETE_SUCCESSED;
			} catch (Exception e) {
				logger.debug("-----> catch Exception:" + e.getMessage());
				throw new RuntimeException(GlobalConstant.DELETE_FILE_FROM_DIR_FAIL);
			}
		}
		return GlobalConstant.DELETE_FAIL;
	}

	@Override
	public void downFile(File file, String fileName, final HttpServletResponse response) {
		byte[] data = getByte(file);
		int dataLength = 0;
		if (data != null) {
			dataLength = data.length;
		}
		OutputStream outputStream = null;
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.addHeader("Content-Length", "" + dataLength);
			response.setContentType("application/octet-stream;charset=UTF-8");
			outputStream = new BufferedOutputStream(response.getOutputStream());
			if (data != null) {
				outputStream.write(data);
			}
		} catch (Exception e) {
			logger.debug("-----> catch Exception:" + e.getMessage());
			throw new RuntimeException("下载文件异常！");
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					
				}
			}
		}
	}

	/**
	 * 把一个文件转化为字节 
	 * @param file 
	 * @return byte[] 
	 * @throw Exception
	 */
	public static byte[] getByte(File file) {
		if (file == null) {
			return null;
		}
		FileInputStream stream = null;
		ByteArrayOutputStream out = null;
		try {
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE) { // 当文件的长度超过了int的最大值
				logger.debug("-----> 文件的长度超过了int的最大值");
			}
			stream = new FileInputStream(file);
			out = new ByteArrayOutputStream(length);
			byte[] b = new byte[length];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			return out.toByteArray();
		} catch (IOException e) {
			logger.debug("-----> catch Exception:" + e.getMessage());
			throw new RuntimeException("文件转化为字节异常！");
		}finally {
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					
				}
			}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					
				}
			}
		}
	}

}
