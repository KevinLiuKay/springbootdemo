package com.kevin.common.utils;

/**
 * 响应前端请求
 */
public class JsonResult {
	private Boolean status;
	private String message;
	private Object model;
	
	public JsonResult() {
		// TODO Auto-generated constructor stub
	}
	
	public JsonResult(Boolean status,String message,Object model) {
		this.status = status;
		this.message = message;
		this.model = model;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
}
