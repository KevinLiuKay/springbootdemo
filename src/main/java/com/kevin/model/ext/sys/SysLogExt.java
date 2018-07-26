package com.kevin.model.ext.sys;


import com.kevin.model.SysLog;
import com.kevin.model.SysUser;

public class SysLogExt extends SysLog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7100518471050907608L;
	private SysUser sysUser;
	
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
}
