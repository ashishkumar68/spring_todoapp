package com.todoapp.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.todoapp.model.Task;

@Component
public interface TaskRepositoryCustom {

	public boolean saveOrUpdateTasksInBatch(List<Task> tasks, int batchSize);
}
