package com.kevin.exception;

/**
 * @author lzk
 */
public class CommonException extends Exception {
	
	public String message;
	
	public CommonException(String message){
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}