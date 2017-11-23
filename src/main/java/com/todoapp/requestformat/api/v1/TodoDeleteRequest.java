package com.todoapp.requestformat.api.v1;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.todoapp.repository.TaskRepository;
import com.todoapp.utils.Constants;
import com.todoapp.utils.Constants.TodoError;

@Transactional
@Component
public class TodoDeleteRequest implements Validator{

	private List<Task> tasks;

	@Autowired
	private TaskRepository taskRepository;

	@PostConstruct
	public void init() {
		Task.taskRepository = this.taskRepository;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public static class Task implements Validator {

		private static TaskRepository taskRepository;

		private String serialNumber;

		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		@Override
		public boolean supports(Class<?> clazz) {
			return Task.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			Task task = (Task) target;
			TodoError todoError = null;
			
			if ( null == task || null == task.getSerialNumber()) {
				todoError = (TodoError) Constants.todoErrorMap.get("INCOMPLETEREQ");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
				return;
			}
			
			if (task.getSerialNumber().length() < 1 || task.getSerialNumber().length() > 20
					|| null == taskRepository.findBySerialNumber(task.getSerialNumber())) {
				todoError = (TodoError) Constants.todoErrorMap.get("INVALIDSERIAL");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
			}
		}
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return TodoDeleteRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TodoDeleteRequest request = (TodoDeleteRequest) target;
		DataBinder binder = null;
		
		if (null == request.getTasks()) {
			TodoError todoError = (TodoError) Constants.todoErrorMap.get("INVALIDREQCONTENT");
			errors.reject(todoError.getErrorCode(), todoError.getErrorText());
			return;
		}

		for (Task task : request.getTasks()) {
			binder = new DataBinder(task);
			binder.addValidators(task);
			binder.validate();
			if (binder.getBindingResult().hasErrors()) {
				errors.reject(binder.getBindingResult().getAllErrors().get(0).getCode(),
						binder.getBindingResult().getAllErrors().get(0).getDefaultMessage());
				return;
			}
		}
	}
	
}
