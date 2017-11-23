package com.todoapp.formatter.api.v1;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.todoapp.requestformat.api.v1.TodoCreateRequest;

@Component
public class TodoCreateRequestFormatter implements Formatter<TodoCreateRequest>{

	@Autowired
	private Gson gson;

	@Override
	public String print(TodoCreateRequest object, Locale locale) {
		return gson.toJson(object);
	}

	@Override
	public TodoCreateRequest parse(String text, Locale locale) throws ParseException {
		TodoCreateRequest request = gson.fromJson(text, TodoCreateRequest.class);
		
		// trimming the content of TodoCreateRequest
		if (request.getTasks() != null) {
			for (int i=0; i < request.getTasks().size() ; i++ ) {
				com.todoapp.requestformat.api.v1.TodoCreateRequest.Task task = request.getTasks().get(i);
				if (task != null && task.getDescription() != null) {
					task.setDescription(task.getDescription().trim());
				}
			}
		}
		return request;
	}
}
