package com.todoapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.model.Task;
import com.todoapp.repository.TaskRepository;

@Service
public class TodoService {

	@Autowired
	private TaskRepository taskRepository;
	
	public List<Task> getTasks() {
		return taskRepository.findAll();
	}
}
