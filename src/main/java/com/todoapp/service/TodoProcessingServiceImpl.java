package com.todoapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.todoapp.model.Task;
import com.todoapp.repository.TaskRepository;
import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.responseformat.api.v1.TodoCRUDResponse;

@Service
public class TodoProcessingServiceImpl implements TodoProcessingService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> processTodoGetRequest(TodoGetRequest request) {
		
		List<Task> taskList = null;
		// Check if its a Get Request for fetching tasks based on the serialNumbers.
		if (request.getTasks() != null) {
			taskList = new ArrayList<Task>();
			for (com.todoapp.requestformat.api.v1.TodoGetRequest.Task task: request.getTasks()) {
				taskList.add(taskRepository.findBySerialNumber(task.getSerialNumber()));
			}
		} else {
			Pageable page = new PageRequest(request.getTaskList().getPage(), request.getTaskList().getLimit());
			taskList = taskRepository.findAll(page).getContent();
		}
		return taskList;
	}

	@Override
	public String generateSerialNumber() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		return String.valueOf(cal.getTimeInMillis());
	}

	@Override
	public TodoCRUDResponse createTodoCRUDResponse(List<Task> tasks) {
		List<com.todoapp.responseformat.api.v1.TodoCRUDResponse.Task> taskList = new ArrayList<>();
		
		// Creating Final Task List to be sent in response.
		for (Task task: tasks) {
			com.todoapp.responseformat.api.v1.TodoCRUDResponse.Task responseTaskObject = 
					new com.todoapp.responseformat.api.v1.TodoCRUDResponse.Task();
			responseTaskObject.setSerialNumber(task.getSerialNumber());
			responseTaskObject.setDescription(task.getDescription());
			taskList.add(responseTaskObject);
		}
		
		TodoCRUDResponse todoResponse = new TodoCRUDResponse();
		todoResponse.setTasks(taskList);
		
		return todoResponse;
	}
}
