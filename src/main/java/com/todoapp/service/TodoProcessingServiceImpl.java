package com.todoapp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.repository.TaskRepository;
import com.todoapp.repository.TaskRepositoryCustom;
import com.todoapp.repository.UserRepository;
import com.todoapp.requestformat.api.v1.TodoCreateRequest;
import com.todoapp.requestformat.api.v1.TodoDeleteRequest;
import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.requestformat.api.v1.TodoUpdateRequest;
import com.todoapp.responseformat.api.v1.TodoCRUDResponse;
import com.todoapp.utils.Constants;
import com.todoapp.utils.Constants.TodoError;

@Transactional
@Service
public class TodoProcessingServiceImpl implements TodoProcessingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TodoProcessingServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskRepositoryCustom taskRepositoryCustom;

	@Override
	public Object processTodoGetRequest(TodoGetRequest request) {
		try {
			List<Task> taskList = null;
			// Check if its a Get Request for fetching tasks based on the serialNumbers.
			if (request.getTasks() != null) {
				taskList = new ArrayList<Task>();
				for (com.todoapp.requestformat.api.v1.TodoGetRequest.Task task : request.getTasks()) {
					taskList.add(taskRepository.findBySerialNumber(task.getSerialNumber()));
				}
			} else {
				Pageable page = new PageRequest(request.getTaskList().getPage(), request.getTaskList().getLimit());
				taskList = taskRepository.findAll(page).getContent();
			}
			return taskList;
		} catch (Exception ex) {
			LOGGER.error("Exception =>" + ex.getMessage() + " Occurred");
			TodoError error = Constants.todoErrorMap.get("INTERNALERR");
			return error;
		}
	}

	@Override
	public String generateSerialNumber() {
		return String.valueOf(System.nanoTime());
	}

	@Override
	public TodoCRUDResponse createTodoCRUDResponse(List<Task> tasks) {
		List<com.todoapp.responseformat.api.v1.TodoCRUDResponse.Task> taskList = new ArrayList<>();

		// Creating Final Task List to be sent in response.
		for (Task task : tasks) {
			com.todoapp.responseformat.api.v1.TodoCRUDResponse.Task responseTaskObject = new com.todoapp.responseformat.api.v1.TodoCRUDResponse.Task();
			responseTaskObject.setSerialNumber(task.getSerialNumber());
			responseTaskObject.setDescription(task.getDescription());
			taskList.add(responseTaskObject);
		}

		TodoCRUDResponse todoResponse = new TodoCRUDResponse();
		todoResponse.setTasks(taskList);

		return todoResponse;
	}

	@Transactional
	@Override
	public Object processTodoCreateRequest(TodoCreateRequest request) {
		try {
			// Taken Sample User for this Operation.
			User user = userRepository.findByEmail("ashishgoel68@gmail.com");
			List<Task> tasks = new ArrayList<Task>();
			for (com.todoapp.requestformat.api.v1.TodoCreateRequest.Task task : request.getTasks()) {
				Task newTask = new Task();
				newTask.setDescription(task.getDescription());
				newTask.setSerialNumber(this.generateSerialNumber());
				newTask.setUser(user);
				tasks.add(newTask);
			}

			taskRepositoryCustom.saveOrUpdateTasksInBatch(tasks, 20);
			return tasks;
		} catch (Exception ex) {
			LOGGER.error("Exception : " + ex.getMessage() + " Occurred");
			TodoError error = Constants.todoErrorMap.get("INTERNALERR");
			return error;
		}
	}

	@Transactional
	@Override
	public Object processTodoUpdateRequest(TodoUpdateRequest request) {

		try {
			List<String> serialNumberList = new ArrayList<String>();
			for (com.todoapp.requestformat.api.v1.TodoUpdateRequest.Task task : request.getTasks()) {
				serialNumberList.add(task.getSerialNumber());
			}

			List<Task> tasks = taskRepository.findBySerialNumberIn(serialNumberList);

			// Since request Tasks Objects and Tasks list objects are equal in numbers.
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				task.setDescription(request.getTasks().get(i).getNewDescription());
			}

			taskRepositoryCustom.saveOrUpdateTasksInBatch(tasks, 20);
			return tasks;
		} catch (Exception ex) {
			LOGGER.error("Exception =>" + ex.getMessage() + " Occurred");
			TodoError error = Constants.todoErrorMap.get("INTERNALERR");
			return error;
		}
	}

	@Transactional
	@Override
	public Object processTodoDeleteRequest(TodoDeleteRequest request) {

		try {
			List<String> serialNumberList = new ArrayList<String>();
			for (com.todoapp.requestformat.api.v1.TodoDeleteRequest.Task task : request.getTasks()) {
				serialNumberList.add(task.getSerialNumber());
			}

			List<Task> tasks = taskRepository.findBySerialNumberIn(serialNumberList);
			taskRepository.deleteInBatch(tasks);
			return tasks;
		} catch (Exception ex) {
			LOGGER.error("Exception =>" + ex.getMessage() + " Occurred");
			TodoError error = Constants.todoErrorMap.get("INTERNALERR");
			return error;
		}
	}
}
