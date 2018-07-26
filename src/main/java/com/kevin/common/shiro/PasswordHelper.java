package com.kevin.common.shiro;

import com.kevin.common.GlobalConstant.GlobalConstant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordHelper {
	
	private static Logger logger = LoggerFactory.getLogger(PasswordHelper.class);

	private static String algorithmName = "md5";
	private static int hashIterations = 2;

	/**
	 * 密码加密
	 * @param userId 
	 * @param userPasswd
	 * @return
	 */
	public static String encryptPassword(String userId, String userPasswd) {
		String newPassword = new SimpleHash(algorithmName, userPasswd, ByteSource.Util.bytes(userId), hashIterations).toHex();
		return newPassword;
	}
	
	public static void main(String[] args) {
		String pwd = encryptPassword(GlobalConstant.ROOT_USER_ID, GlobalConstant.ROOT_PASSWORD);
		System.out.println(pwd);
	}
}
