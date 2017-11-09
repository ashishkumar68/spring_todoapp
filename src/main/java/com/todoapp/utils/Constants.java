package com.todoapp.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	/**
	 * TodoError Static Class declaration for API Errors Type.
	 *
	 */
	static class TodoError {
		String errorCode;
		String messageCode;

		public TodoError(String errorCode, String messageCode) {
			this.errorCode = errorCode;
			this.messageCode = messageCode;
		}
	}

	/**
	 * HashMap for Constants Map.
	 */
	public static final Map<String, TodoError> todoErrorMap = new HashMap<String, TodoError>();

	/**
	 * Static Block for initializing todoErrorMap.
	 */
	static {
		todoErrorMap.put("INVALIDTOKEN", new TodoError("1000", "api.response.error.invalid_auth_token"));
		todoErrorMap.put("TOKENEXPIRY", new TodoError("1001", "api.response.error.auth_token_expiry"));
		todoErrorMap.put("INVALIDSERIAL", new TodoError("1002", "api.response.error.invalid_serial"));
	}
}
