package com.todoapp.requestformat.api.v1;

import java.util.List;


public class TodoGetRequest {
	
	class Task {
		String serialNumber;

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

	}

	class TaskList {
		String page;
		
		String limit;

		public String getPage() {
			return page;
		}

		public void setPage(String page) {
			this.page = page;
		}

		public String getLimit() {
			return limit;
		}

		public void setLimit(String limit) {
			this.limit = limit;
		}

		@Override
		public String toString() {
			return "TaskList [page=" + page + ", limit=" + limit + "]";
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
	
}
