package com.todoapp.requestformat.api.v1;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.todoapp.repository.TaskRepository;
import com.todoapp.utils.Constants;
import com.todoapp.utils.Constants.TodoError;

@Component
public class TodoGetRequest implements Validator {

	@Autowired
	private TaskRepository taskRepository;
	
	@PostConstruct
	public void init() {
		Task.taskRepository = this.taskRepository;
	}
	
	/**
	 * Task Class with Validator.
	 *
	 */
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
		public String toString() {
			return "Task [serialNumber=" + serialNumber + "]";
		}

		@Override
		public boolean supports(Class<?> clazz) {
			return Task.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			Task task = (Task) target;
			TodoError todoError = null;
			
			if (null == task) {
				todoError = (TodoError) Constants.todoErrorMap.get("INCOMPLETEREQ");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
				return;
			}
			
			if (
						null == task.getSerialNumber() || task.getSerialNumber().equals("") 
					||	task.getSerialNumber().length() < 1 
					||  null == taskRepository.findBySerialNumber(task.getSerialNumber())
			) {
				todoError = (TodoError) Constants.todoErrorMap.get("INVALIDSERIAL");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
			}
		}

	}

	/**
	 * TaskList class for Data Table Listing Request
	 *
	 */
	public static class TaskList implements Validator {
		private Integer page;

		private Integer limit;

		public Integer getPage() {
			return page;
		}

		public void setPage(Integer page) {
			this.page = page;
		}

		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		@Override
		public String toString() {
			return "TaskList [page=" + page + ", limit=" + limit + "]";
		}

		@Override
		public boolean supports(Class<?> clazz) {
			return TaskList.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			TaskList pagingTask = (TaskList) target;
			TodoError todoError = null;
			
			if (null == pagingTask) {
				todoError = (TodoError) Constants.todoErrorMap.get("INCOMPLETEREQ");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
				return;
			}
			
			if (null == pagingTask.getPage() || pagingTask.getPage() < 0) {
				todoError = (TodoError) Constants.todoErrorMap.get("INVALIDPAGE");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
				return;
			}

			if (null == pagingTask.getLimit() || pagingTask.getLimit() < 0) {
				todoError = (TodoError) Constants.todoErrorMap.get("INVALIDLIMIT");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
			}

		}

	}

	private List<Task> tasks;

	private TaskList taskList;

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

	@Override
	public String toString() {
		return "TodoGetRequest [tasks=" + tasks + ", taskList=" + taskList + "]";
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return TodoGetRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		TodoGetRequest request = (TodoGetRequest) object;

		if ((null == request.getTaskList() && null == request.getTasks())
				|| (null != request.getTaskList() && null != request.getTasks())) {
			TodoError todoError = (TodoError) Constants.todoErrorMap.get("INVALIDREQCONTENT");
			errors.reject(todoError.getErrorCode(), todoError.getErrorText());
			return;
		}

		if (null != request.getTasks()) {
			for (Task task : request.getTasks()) {
				DataBinder binder = new DataBinder(task);
				binder.addValidators(task);
				binder.validate();
				if (binder.getBindingResult().hasErrors()) {
					errors.reject(binder.getBindingResult().getAllErrors().get(0).getCode(),
							binder.getBindingResult().getAllErrors().get(0).getDefaultMessage());
					return;
				}
			}
		}

		if (null != request.getTaskList()) {
			DataBinder binder = new DataBinder(request.getTaskList());
			binder.addValidators(request.getTaskList());
			binder.validate();
			if (binder.getBindingResult().hasErrors()) {
				errors.reject(binder.getBindingResult().getAllErrors().get(0).getCode(),
						binder.getBindingResult().getAllErrors().get(0).getDefaultMessage());
				return;
			}
		}
	}

}
