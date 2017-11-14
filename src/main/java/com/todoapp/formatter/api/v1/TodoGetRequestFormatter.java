package com.todoapp.formatter.api.v1;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.requestformat.api.v1.TodoGetRequest.Task;

@Component
public class TodoGetRequestFormatter implements Formatter<TodoGetRequest>{

	@Autowired
	private Gson gson;
	
	@Override
	public String print(TodoGetRequest object, Locale locale) {
		return gson.toJson(object);
	}

	@Override
	public TodoGetRequest parse(String text, Locale locale) throws ParseException {
		TodoGetRequest request = gson.fromJson(text, TodoGetRequest.class);
		
		if (request.getTasks() != null) {
			int tasksSize = request.getTasks().size();
			// trimming Serial Number from TodoGetRequest
			for (int i=0; i < tasksSize ; i++) {
				Task task = request.getTasks().get(i);
				if (task.getSerialNumber() != null) {
					task.setSerialNumber(task.getSerialNumber().trim());
				}
			}
		}
		return request;
	}

}
