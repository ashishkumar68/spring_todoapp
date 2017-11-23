package com.todoapp.requestformat.api.v1;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.todoapp.utils.Constants;
import com.todoapp.utils.Constants.TodoError;

@Component
public class TodoCreateRequest implements Validator {

	private List<Task> tasks;

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "TodoCreateRequest [tasks=" + tasks + "]";
	}

	public static class Task implements Validator {
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return "Task [description=" + description + "]";
		}

		@Override
		public boolean supports(Class<?> clazz) {
			return Task.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			Task task = (Task) target;
			TodoError todoError = null;

			if (null == task || null == task.getDescription()) {
				todoError = (TodoError) Constants.todoErrorMap.get("INCOMPLETEREQ");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
				return;
			}

			if (task.getDescription().length() < 1 || task.getDescription().length() > 200) {
				todoError = (TodoError) Constants.todoErrorMap.get("INVALIDDESC");
				errors.reject(todoError.getErrorCode(), todoError.getErrorText());
			}
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return TodoCreateRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TodoCreateRequest request = (TodoCreateRequest) target;
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
