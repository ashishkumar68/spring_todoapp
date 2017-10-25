package com.todoapp.controller.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.controller.api.TodoBaseController;
import com.todoapp.model.Task;
import com.todoapp.service.TodoService;

import io.swagger.annotations.Api;

@RestController
@Api(value="Todo App", produces="application/json", description="This is Tasks Api version 1.")
public class TodoControllerV1 extends TodoBaseController {
	
	@Autowired
	private TodoService todoService;
	
	@RequestMapping(value="/v1/tasks", method=RequestMethod.GET)
	public @ResponseBody List<Task> getTasksAction() {
		return todoService.getTasks();
	}
	
	@RequestMapping(value="/v1/tasks",method=RequestMethod.POST)
	public @ResponseBody List<Task> createTasksAction() {
		return todoService.getTasks();
	}
	
	@RequestMapping(value="/v1/tasks",method=RequestMethod.PUT)
	public @ResponseBody List<Task> updateTasksAction() {
		return todoService.getTasks();
	}
	@RequestMapping(value="/v1/tasks",method=RequestMethod.DELETE)
	public @ResponseBody List<Task> deleteTasksAction() {
		return todoService.getTasks();
	}
}
