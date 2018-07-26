package com.kevin.exception;

/**
 * 
 * @author dell2
 *
 */
public class CommonException extends Exception {
	
	public String message;
	
	public CommonException(String message){
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}