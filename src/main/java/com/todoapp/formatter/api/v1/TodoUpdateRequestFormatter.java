package com.todoapp.formatter.api.v1;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.todoapp.requestformat.api.v1.TodoUpdateRequest;
import com.todoapp.requestformat.api.v1.TodoUpdateRequest.Task;

@Component
public class TodoUpdateRequestFormatter implements Formatter<TodoUpdateRequest>{

	@Autowired
	private Gson gson;
	
	@Override
	public String print(TodoUpdateRequest object, Locale locale) {
		return gson.toJson(object);
	}

	@Override
	public TodoUpdateRequest parse(String text, Locale locale) throws ParseException {
		TodoUpdateRequest request = gson.fromJson(text, TodoUpdateRequest.class);
		
		if (request !=null && request.getTasks() != null && request.getTasks().size() > 0) {
			for (Task task: request.getTasks()) {
				if (null != task.getSerialNumber()) {
					task.setSerialNumber(task.getSerialNumber().trim());
				}
				
				if (null != task.getNewDescription()) {
					task.setNewDescription(task.getNewDescription());
				}
			}
		}
		return request;
	}

}
