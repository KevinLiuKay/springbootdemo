package com.kevin.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kevin.common.GlobalConstant.GlobalConstant;

/**
 * 日期转换工具类
 */
public class DateUtil {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	// ********************** 时间 ******************************

	/**
	 * 格式化日期
	 * 
	 * @param dateFormat
	 *            日期格式 如：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String format2String(Date date, String dateFormat) {
		if (date == null) {
			return GlobalConstant.EMPTY;
		}
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = GlobalConstant.DATE_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 字符串 转 日期
	 * 
	 * @param dateFormat
	 *            日期格式 如：yyyy-MM-dd HH:mm:ss
	 * @param dateStr
	 *            字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date parse2Date(String dateStr, String dateFormat) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = GlobalConstant.DATE_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			//throw new RuntimeException("时间格式异常");
		}
		return null;
	}

	// ********************** 时间戳 ******************************

	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param dateFormat
	 *            日期格式 如：yyyy-MM-dd HH:mm:ss
	 * @param timeMillis
	 *            字符串
	 * @return
	 */
	public static String timeStamp2String(Long timeMillis, String dateFormat) {
		if (timeMillis == null) {
			return GlobalConstant.EMPTY;
		}
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = GlobalConstant.DATE_FORMAT;
		}
		// SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		// sdf.format(new Date());
		return DateFormatUtils.format(timeMillis, dateFormat);
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param dateFormat
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @param dateStr
	 *            日期字符串
	 * @return
	 */
	public static Long string2TimeStamp(String dateStr, String dateFormat) {
		if (dateStr == null) {
			return null;
		}
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = GlobalConstant.DATE_FORMAT;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.parse(dateStr).getTime();
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		long currentTime = System.currentTimeMillis();
		logger.info("currentTime:" + currentTime);
		// 该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

		// 微信关注时间subscribe_time 转 yyyy-MM-dd HH:mm:ss
		String date = timeStamp2String(currentTime, GlobalConstant.DATE_FORMAT);
		logger.info("timeStamp2String:" + date);

		Long timeStamp2 = string2TimeStamp(date, GlobalConstant.DATE_FORMAT);
		logger.info("string2TimeStamp:" + timeStamp2);

	}

	// **************************** 获取当前时间 **********************************

	/**
	 * 取得当前日期
	 * 
	 * @param dateFormat
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrTimeFormat(String dateFormat) {
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = GlobalConstant.DATE_FORMAT;
		}
		return DateFormatUtils.format(System.currentTimeMillis(), dateFormat);
	}

	// ***********************　计算天数相差　******************************

	/**
	 * Method signDaysBetweenTowDate 两日期间相差天数.
	 * 
	 * @param d1
	 *            日期字符串
	 * @param d2
	 *            日期字符串
	 * @return long 天数
	 */
	public static long getDaysBetweenTwoString(String d1, String d2) {
		java.sql.Date dd1 = null;
		java.sql.Date dd2 = null;
		long result;
		try {
			dd1 = java.sql.Date.valueOf(d1);
			dd2 = java.sql.Date.valueOf(d2);
			result = getDaysBetweenTwoSqlDate(dd1, dd2);
		} catch (Exception ex) {
			result = -1;
		}
		return result;
	}

	/**
	 * Method signDaysBetweenTowDate 两日期间相差天数.
	 * 
	 * @param d1
	 *            开始日期 日期型
	 * @param d2
	 *            开始日期 日期型
	 * @return long 天数
	 */
	public static long getDaysBetweenTwoSqlDate(java.sql.Date d1,
			java.sql.Date d2) {
		return (d1.getTime() - d2.getTime()) / 86400000; //86400000 = 3600 * 24 * 1000 
	}

	/**
	 * Method signDaysBetweenTowDate 两日期间相差天数.
	 * 
	 * @param d1
	 *            开始日期 日期型
	 * @param d2
	 *            开始日期 日期型
	 * @return long 天数
	 */
	public static long getDaysBetweenTwoUtilDate(Date d1, Date d2) {
		return (d1.getTime() - d2.getTime()) / 86400000; //86400000 = 3600 * 24 * 1000
	}
}
