package com.todoapp.responseformat.api.v1;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.todoapp.exceptionhandler.TODOError;

@Component
@JsonInclude(Include.NON_NULL)
public class TodoGeneralResponse {

	private String reasonCode;

	private String reasonText;
	
	private TODOError error;
	
	private TodoCRUDResponse TodoGetResponse;
	
	private TodoCRUDResponse TodoCreateResponse;
	
	private TodoCRUDResponse TodoUpdateResponse;
	
	private TodoCRUDResponse TodoDeleteResponse;
	
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

	public TODOError getError() {
		return error;
	}

	public void setError(TODOError error) {
		this.error = error;
	}

	public TodoCRUDResponse getTodoGetResponse() {
		return TodoGetResponse;
	}

	public void setTodoGetResponse(TodoCRUDResponse todoGetResponse) {
		TodoGetResponse = todoGetResponse;
	}

	public TodoCRUDResponse getTodoCreateResponse() {
		return TodoCreateResponse;
	}

	public void setTodoCreateResponse(TodoCRUDResponse todoCreateResponse) {
		TodoCreateResponse = todoCreateResponse;
	}

	public TodoCRUDResponse getTodoUpdateResponse() {
		return TodoUpdateResponse;
	}

	public void setTodoUpdateResponse(TodoCRUDResponse todoUpdateResponse) {
		TodoUpdateResponse = todoUpdateResponse;
	}

	public TodoCRUDResponse getTodoDeleteResponse() {
		return TodoDeleteResponse;
	}

	public void setTodoDeleteResponse(TodoCRUDResponse todoDeleteResponse) {
		TodoDeleteResponse = todoDeleteResponse;
	}

	
}
