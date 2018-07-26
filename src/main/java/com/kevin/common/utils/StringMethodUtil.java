package com.kevin.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kevin.common.GlobalConstant.GlobalConstant;

/**
 * String方法工具类
 * @author Tiger Mo
 * @create 2016.04.13
 */
public class StringMethodUtil {
	
	private static Logger logger = LoggerFactory.getLogger(StringMethodUtil.class);
	
	/**
	 * 生成指定位数的随机码
	 * @param length 随机码的位数
	 * @return 指定位数的随机码
	 */
	public static String getRandomCode(int length) {
		String[] source = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M",
				"N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		String code = "";
		Random rd = new Random();
		for (int i = 0; i < length; i++) {
			code += source[rd.nextInt(source.length)];
		}
		return code;
	}

	public static String subString(String text, int length, String endWith) {
		int textLength = text.length();
		int byteLength = 0;
		StringBuffer returnStr = new StringBuffer();
		for (int i = 0; i < textLength && byteLength < length * 2; i++) {
			String str_i = text.substring(i, i + 1);
			if (str_i.getBytes().length == 1) {// 英文
				byteLength++;
			} else {// 中文
				byteLength += 2;
			}
			returnStr.append(str_i);
		}
		try {
			if (byteLength < text.getBytes(GlobalConstant.GBK).length) {// getBytes("GBK")每个汉字长2，getBytes(GlobalConstant.ENCODING_UTF_8)每个汉字长度为3
				returnStr.append(endWith);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnStr.toString();
	}


	/**
	 * 通过分隔符（字符串）将字符串分隔为字符串数组
	 * @param strSc 源字符串
	 * @param separator 分隔符（字符串）
	 * @return String[] 字符串数组
	 */
	public static String[] split(String strSc, String separator) {
		String temp = strSc + "";
		String[] ret = null;
		try {
			if (temp != null && separator != null) {
				int lent = countSeparator(temp, separator) + 1;
				ret = new String[lent];
				int endindex = 0;
				int sptlent = separator.length();
				for (int i = 0; i < lent - 1; i++) {
					endindex = temp.indexOf(separator);
					ret[i] = temp.substring(0, endindex);
					temp = temp.substring(endindex + sptlent);
				}
				ret[lent - 1] = temp;
			} else if (temp != null && separator == null) {
				ret = new String[1];
				ret[0] = temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 计算一个字符串中分隔符（字符串）的个数
	 * @param strSc 源字符串
	 * @param separator 分隔符（字符串）
	 * @return int 分隔符（字符串）的个数
	 */
	public static int countSeparator(String strSc, String separator) {
		int count = 0;
		if (strSc != null && separator != null) {
			int endindex = 0;
			int sptlent = separator.length();
			while ((endindex = strSc.indexOf(separator, endindex)) > -1) {
				endindex += sptlent;
				count++;
			}
		}
		return count;
	}
	
	
    /**
     * 过滤字符串中html代码并截取字符串
     * @param baseString 原始字符串
     * @param length 截取长度
     * @param isAdd 是否加省略号 ture：添加，false：不添加
     * @param count 省略号个数：3：（3个），其他（6个）
     * @return
     */
    public static String subString(String baseString, int length, boolean isAdd, int count){
    	String newString = Html2Text(baseString);
    	if(newString!=null && !"".equals(newString)){
    		newString = newString.trim();
    			if(isAdd){
    				if(count==3){
    					newString = subString(newString, length, "...");
    				} else{
    					newString = subString(newString, length, "......");
    				}
	    		} else {
	    			newString = subString(newString, length, "");
	    		}
    		
    	}
    	return newString;
    }
    
    /**
     * 去除html代码
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
    	
    	 String htmlStr = inputString; 
	        String textStr ="";
	        Pattern p_script;
	        java.util.regex.Matcher m_script;
	        Pattern p_style;
	        java.util.regex.Matcher m_style;
	        Pattern p_html;
	        java.util.regex.Matcher m_html;          
	        Pattern p_ba;
	        java.util.regex.Matcher m_ba;
	        Pattern p_ba2;
	        java.util.regex.Matcher m_ba2;
	        
	        try {
	            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
	            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
	            String regEx_html = "<[^>]+>";
	            String patternStr = "\\s+";
	            String patternStr2 = "&[a-zA-Z0-9]+;";
	            
	            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
	            m_script = p_script.matcher(htmlStr);
	            htmlStr = m_script.replaceAll(" "); 

	            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
	            m_style = p_style.matcher(htmlStr);
	            htmlStr = m_style.replaceAll(" "); 
	         
	            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
	            m_html = p_html.matcher(htmlStr);
	            htmlStr = m_html.replaceAll(" "); 
	            
	            p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
	            m_ba = p_ba.matcher(htmlStr);
	            htmlStr = m_ba.replaceAll(" "); 
	            
	            p_ba2 = Pattern.compile(patternStr2,Pattern.CASE_INSENSITIVE);
	            m_ba2 = p_ba2.matcher(htmlStr);
	            htmlStr = m_ba2.replaceAll(" "); 
	         
	         textStr = htmlStr;
	         
	        }catch(Exception e) {
	        	logger.debug("-----> Html2Text: " + e.getMessage());
	        }          
	        return textStr;
    	
    }


}
