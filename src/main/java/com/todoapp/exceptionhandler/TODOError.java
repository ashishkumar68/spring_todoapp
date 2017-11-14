package com.todoapp.exceptionhandler;

import org.springframework.stereotype.Component;

@Component
public class TODOError {

	private String reasonCode;
	private String reasonText;

	public TODOError() {
		this.reasonCode = null;
		this.reasonText = null;
	}
	
	public TODOError(String errorCode, String errorText) {
		this.reasonCode = errorCode;
		this.reasonText = errorText;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

}
