package com.todoapp.controller.api.v1;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.todoapp.model.Task;
import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.service.TodoService;
import com.todoapp.service.TodoValidationService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/")
@Api(value = "Todo App", produces = "application/json", description = "This is Tasks Api version 1.")
public class TodoControllerV1 {

	@Autowired
	private TodoService todoService;

	@Autowired
	private TodoValidationService todoValidationService;
	
	@Autowired
	private Gson gson;
	
	@RequestMapping(value = "tasks", method = RequestMethod.GET)
	public @ResponseBody String getTasksAction(@RequestParam String data) {
		TodoGetRequest request = gson.fromJson(data, TodoGetRequest.class);
		return request.toString();
	}

	@RequestMapping(value = "tasks", method = RequestMethod.POST)
	public @ResponseBody List<Task> createTasksAction() {
		throw new NullPointerException("");
	}

	@RequestMapping(value = "tasks", method = RequestMethod.PUT)
	public @ResponseBody List<Task> updateTasksAction() {
		return todoService.getTasks();
	}

	@RequestMapping(value = "tasks", method = RequestMethod.DELETE)
	public @ResponseBody List<Task> deleteTasksAction() {
		return todoService.getTasks();
	}
}
