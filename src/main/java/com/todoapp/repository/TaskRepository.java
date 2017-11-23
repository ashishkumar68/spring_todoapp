package com.todoapp.repository;


import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.todoapp.model.Task;

@Repository
@RepositoryRestResource(exported=false)
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	public Task findBySerialNumber(String serialNumber);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<Task> findBySerialNumberIn(List<String> serialNumber);
	
}
