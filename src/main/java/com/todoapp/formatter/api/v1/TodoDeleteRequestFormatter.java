package com.todoapp.formatter.api.v1;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.todoapp.requestformat.api.v1.TodoDeleteRequest;
import com.todoapp.requestformat.api.v1.TodoDeleteRequest.Task;

@Component
public class TodoDeleteRequestFormatter implements Formatter<TodoDeleteRequest>{

	@Autowired
	private Gson gson;
	
	@Override
	public String print(TodoDeleteRequest object, Locale locale) {
		return gson.toJson(object);
	}

	@Override
	public TodoDeleteRequest parse(String text, Locale locale) throws ParseException {
		
		TodoDeleteRequest request = gson.fromJson(text, TodoDeleteRequest.class);
		
		if (request !=null && request.getTasks() != null && request.getTasks().size() > 0) {
			for (Task task : request.getTasks()) {
				if (null != task && null != task.getSerialNumber()) {
					task.setSerialNumber(task.getSerialNumber().trim());
				}
			}
		}
		return request;
	}
	
}
