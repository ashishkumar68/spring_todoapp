package com.todo.validator.api.v1;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.todo.requestformat.api.v1.TodoGetRequest;

@Component
public class TodoGetValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TodoGetRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		TodoGetRequest todoRequest = (TodoGetRequest) object;

		if (
					(todoRequest.getTasks() == null && todoRequest.getTaskList() == null)
				|| 	(todoRequest.getTasks() != null && todoRequest.getTaskList() != null)
		) {
			errors.reject("Invalid Input");
			return;
		}
		
		if (todoRequest.getTasks() != null) {
			
		}
	}
	
	
}