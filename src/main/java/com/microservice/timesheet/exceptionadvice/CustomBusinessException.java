package com.microservice.timesheet.exceptionadvice;

public class CustomBusinessException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String isErrorMsg;

	public CustomBusinessException(String psErrorMsg) {
		
		super(psErrorMsg);
	}

	

	public String getIsErrorMsg() {
		return isErrorMsg;
	}

	public void setIsErrorMsg(String isErrorMsg) {
		this.isErrorMsg = isErrorMsg;
	}
	
	
	

}
