package com.todoapp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.todoapp.model.Task;
import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.responseformat.api.v1.TodoCRUDResponse;

@Component
public interface TodoProcessingService {
	
	public List<Task> processTodoGetRequest(TodoGetRequest request);
	
	public String generateSerialNumber();
	
	public TodoCRUDResponse createTodoCRUDResponse(List<Task> tasks);
}
