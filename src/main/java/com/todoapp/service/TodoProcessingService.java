package com.todoapp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.todoapp.model.Task;
import com.todoapp.requestformat.api.v1.TodoCreateRequest;
import com.todoapp.requestformat.api.v1.TodoDeleteRequest;
import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.requestformat.api.v1.TodoUpdateRequest;
import com.todoapp.responseformat.api.v1.TodoCRUDResponse;

@Component
public interface TodoProcessingService {
	
	public Object processTodoGetRequest(TodoGetRequest request);
	
	public String generateSerialNumber();
	
	public TodoCRUDResponse createTodoCRUDResponse(List<Task> tasks);
	
	public Object processTodoCreateRequest(TodoCreateRequest request);
	
	public Object processTodoUpdateRequest(TodoUpdateRequest request);
	
	public Object processTodoDeleteRequest(TodoDeleteRequest request);
}
