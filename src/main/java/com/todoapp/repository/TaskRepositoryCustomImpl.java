package com.todoapp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.todoapp.model.Task;

@Repository
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean saveOrUpdateTasksInBatch(List<Task> tasks, int batchSize) {
		for (int i=0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			this.entityManager.persist(task);
			if ((i+1) % batchSize == 0) {
				// Flushing tasks in Batch as per batch size.
				this.entityManager.flush();
			}
		}
		// to flush the remaining Tasks
		this.entityManager.flush();
		return true;
	}

}
