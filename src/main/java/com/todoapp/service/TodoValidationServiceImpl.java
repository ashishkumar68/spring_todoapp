package com.todoapp.service;

import org.springframework.stereotype.Service;

import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.responseformat.api.v1.TodoValidationResponse;

@Service
public class TodoValidationServiceImpl implements TodoValidationService{
	
	/**
	 *  Function to validate the TodoGetRequest Request object.
	 * 
	 * @param TodoGetRequest request
	 */
	 public TodoValidationResponse validateTodoGetRequest(TodoGetRequest request) {
		 if (
				 (request.getTaskList() == null && request.getTasks() ==  null)
				 || (request.getTaskList() != null && request.getTasks() !=  null)
		 ) {
			 
		 }
		 return  null;
	 }
}
