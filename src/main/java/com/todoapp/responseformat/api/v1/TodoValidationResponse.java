package com.todoapp.responseformat.api.v1;


import com.todoapp.exceptionhandler.TODOError;


public class TodoValidationResponse {
	
	private boolean status;
	
	private TODOError validationError;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public TODOError getValidationError() {
		return validationError;
	}

	public void setValidationError(TODOError validationError) {
		this.validationError = validationError;
	}
	
	
}
