package com.todoapp.exceptionhandler;

public class TODOException {

	private String errorCode;
	private String errorText;

	public TODOException(String errorCode, String errorText) {
		this.errorCode = errorCode;
		this.errorText = errorText;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

}
