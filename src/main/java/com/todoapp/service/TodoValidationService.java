package com.todoapp.service;

import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.responseformat.api.v1.TodoValidationResponse;

public interface TodoValidationService {

	public TodoValidationResponse validateTodoGetRequest(TodoGetRequest request);
	
}
