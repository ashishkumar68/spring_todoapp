package com.todoapp.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Constants {

	/**
	 * TodoError Static Class declaration for API Errors Type.
	 *
	 */
	public static class TodoError {
		private String errorCode;
		private String errorText;

		public TodoError(String errorCode, String errorText) {
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
		todoErrorMap.put("INVALIDREQCONTENT", new TodoError("1003", "api.response.error.invalid_request_content"));
		todoErrorMap.put("INVALIDPAGE", new TodoError("1004", "api.response.error.invalid_page"));
		todoErrorMap.put("INVALIDLIMIT", new TodoError("1005", "api.response.error.invalid_limit"));
		todoErrorMap.put("INCOMPLETEREQ", new TodoError("1006", "api.response.error.incomplete_request"));
	}
}
