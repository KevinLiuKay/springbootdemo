package com.kevin.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.kevin.common.GlobalConstant.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * 导入、导出Excel工具类
 * 
 * @author Tiger Mo
 * @create 2016年5月15日
 * 
 */
public class ExcelUtil {
	
	public static final String VERSION_CAN_NOT_BE_RESOLVED = "不能解析的excel版本！";
	
    public static final String MSIE = "MSIE"; //IE浏览器
    public static final String Mozilla = "Mozilla"; //火狐浏览器
    
    public static final String SHEET_1 = "sheet1";

	// ****************** 第一部分： 导入 ！！！**************
	
	/**
	 * 若是整数，则去小数点后面的.0
	 * @param d
	 * @return
	 */
	public static String _doubleTrans(double d) {
		if ((double) Math.round(d) - d == 0.0D)
			return String.valueOf((long) d);
		else
			return String.valueOf(d);
	}

	public static Workbook createCommonWorkbook(InputStream inp) throws IOException, InvalidFormatException {
		// 首先判断流是否支持mark和reset方法，最后两个if分支中的方法才能支持
		if (!inp.markSupported()) {
			// 还原流信息
			inp = new PushbackInputStream(inp);
		}
		// EXCEL2003使用的是微软的文件系统
		if (POIFSFileSystem.hasPOIFSHeader(inp)) {
			return new HSSFWorkbook(inp);
		}
		// EXCEL2007使用的是OOM文件格式
		if (POIXMLDocument.hasOOXMLHeader(inp)) {
			// 可以直接传流参数，但是推荐使用OPCPackage容器打开
			return new XSSFWorkbook(OPCPackage.open(inp));
		}
		throw new IOException(VERSION_CAN_NOT_BE_RESOLVED);
	}
	
	//********************* 华丽的分割线  ***********************
	
    /**
     * 是否是2003的excel 
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath)  {  
        return filePath.matches("^.+\\.(?i)(xls)$");  
    }  
  
    /**
     * 是否是2007的excel
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath)  {  
        return filePath.matches("^.+\\.(?i)(xlsx)$");  
    }  

	
	// ******************** 第二部分：  导出 ！********************
	
	/**
	 * 文件名编码！！
	 * @param fileName
	 * @param userAgent
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeFilleNameByUserAgent(String fileName, final String userAgent) throws UnsupportedEncodingException {
		if (StringUtils.contains(userAgent, MSIE)) {// IE浏览器
			fileName = URLEncoder.encode(fileName, GlobalConstant.UTF_8);
		} else if (StringUtils.contains(userAgent, Mozilla)) {//火狐浏览器
			fileName = new String(fileName.getBytes(), GlobalConstant.ISO_8859_1);
		} else {
			fileName = URLEncoder.encode(fileName, GlobalConstant.UTF_8);// 其他浏览器
		}
		return fileName;
	}
	
	// TODO 可使用！ 存在大量重复代码 未合并！！！！！！
	
	/**
	 * 导出ExcelList（仅适合单表使用）
	 * @param titles
	 * @param dataList
	 * @param cless
	 * @param os
	 * @throws Exception
	 */
	public static <T> void exportSimpleExcel(String[] titles, List<T> dataList, Class<T> cless, OutputStream os) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(SHEET_1);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		List<String> paramIds = new ArrayList<String>();

		HSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			String[] title = titles[i].split(GlobalConstant.COLON);
			cell = row.createCell(i);
			cell.setCellValue(title[1]);
			cell.setCellStyle(style);
			paramIds.add(title[0]);
		}
		if (dataList != null) {
			for (int i = 0; i < dataList.size(); i++) {
				T item = dataList.get(i);
				row = sheet.createRow(i + 1);
				Object result = null;
				for (int j = 0; j < paramIds.size(); j++) {
					String paramId = paramIds.get(j);
					String firstStr = paramId.substring(0, 1).toUpperCase();
					String secondStr = paramId.substring(1);
					Method mt = cless.getMethod("get" + firstStr + secondStr);
					result = mt.invoke(item);
					if (result != null) {
						row.createCell(j).setCellValue(result.toString());
					} else {
						row.createCell(j).setCellValue("");
					}

				}

			}
		}
		wb.write(os);
		os.close();
	}

	/**
	 * 导出ExcelList
	 * @param titles  列名
	 * @param dataList	需要导出的List
	 * @param os
	 * @throws Exception
	 */
	public static void exportExcelList(String[] titles, List dataList, OutputStream os) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(SHEET_1);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		List<String> paramIds = new ArrayList<String>();

		HSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			String[] title = titles[i].split(GlobalConstant.COLON);
			cell = row.createCell(i);
			cell.setCellValue(title[1]);
			cell.setCellStyle(style);
			paramIds.add(title[0]);
			int length = title[1].length();
			sheet.setColumnWidth(i, length * 1000);
		}
		if (dataList != null) {
			for (int i = 0; i < dataList.size(); i++) {
				Object item = dataList.get(i);
				row = sheet.createRow(i + 1);
				Object result = null;
				for (int j = 0; j < paramIds.size(); j++) {
					String paramId = paramIds.get(j);
					result = getValueByAttrs(paramId, item);
					row.createCell(j).setCellValue(result.toString());
				}

			}
		}
		wb.write(os);
		os.close();
	}


	/**
	 * 导出ExcelList
	 * @param headLines  标题
	 * @param titles 	列名
	 * @param dataList	 需要导出的List
	 * @param os
	 * @throws Exception
	 */
	public static void exportExcelListWithHeadlines(String[] headLines, String[] titles, List dataList, OutputStream os) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(SHEET_1);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		// HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCellStyle styleTwo = wb.createCellStyle();
		styleTwo.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCellStyle styleThree = wb.createCellStyle();
		styleThree.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 17);
		HSSFFont fontTwo = wb.createFont();
		fontTwo.setFontHeightInPoints((short) 12);
		//
		int headLength = headLines.length;
		if (headLines != null && headLength > 0) {
			for (int i = 0; i < headLength; i++) {
				HSSFRow row = sheet.createRow(i);
				HSSFCell hSSFCell0 = row.createCell(0);
				hSSFCell0.setCellValue(headLines[i]);
				if (i == 0) {
					styleTwo.setFont(font);
					hSSFCell0.setCellStyle(styleTwo);
				}
				if (i == 1) {
					styleThree.setFont(fontTwo);
					hSSFCell0.setCellStyle(styleThree);
				}
				sheet.addMergedRegion(new CellRangeAddress(i, (short) i, 0, (short) titles.length - 1));
			}
		}

		List<String> paramIds = new ArrayList<String>();
		HSSFRow row = sheet.createRow((int) headLength);
		HSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			String[] title = titles[i].split(GlobalConstant.COLON);
			cell = row.createCell(i);
			cell.setCellValue(title[1]);
			cell.setCellStyle(style);
			paramIds.add(title[0]);
			int length = title[1].length();
			sheet.setColumnWidth(i, length * 1000);
		}

		if (dataList != null) {
			HSSFCell rowCell = null;
			for (int i = 0; i < dataList.size(); i++) {
				Object item = dataList.get(i);
				row = sheet.createRow(headLength + 1 + i);
				Object result = null;
				for (int j = 0; j < paramIds.size(); j++) {
					String paramId = paramIds.get(j);
					if (StringUtils.isBlank(paramId)) {// 序号
						result = i + 1;
					} else {
						result = getValueByAttrs(paramId, item);
					}
					rowCell = row.createCell(j);
					rowCell.setCellStyle(style);
					rowCell.setCellValue(result.toString());
				}

			}
		}
		wb.write(os);
		os.close(); 
	}

	/**
	 * 通过属性名获取属性值！
	 * @param attrNames
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private static Object getValueByAttrs(String attrNames, Object obj) throws Exception {
		Object value = null;
		if (StringUtils.isNotBlank(attrNames)) {
			String proptyName = "";
			int pIndex = attrNames.indexOf(GlobalConstant.DOT);
			if (pIndex >= 0) {
				proptyName = attrNames.substring(0, pIndex);
			} else {
				proptyName = attrNames;
			}

			if (StringUtils.isNotBlank(proptyName) && obj != null) {
				Class clazz = obj.getClass();
				String firstStr = proptyName.substring(0, 1).toUpperCase();
				String secondStr = proptyName.substring(1);
				Method mt = clazz.getMethod("get" + firstStr + secondStr);
				Object result = mt.invoke(obj);
				if (result != null) {
					String stringClassName = String.class.getSimpleName();
					String valueClassName = result.getClass().getSimpleName();
					if (stringClassName.equals(valueClassName)) {
						value = result;
					} else {
						String surplusName = attrNames.substring(pIndex + 1);
						value = getValueByAttrs(surplusName, result);
					}
				}
			}
		}
		return value;
	}
	
}