package com.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.todoapp.model.Task;

@Repository
@RepositoryRestResource(exported=false)
public interface TaskRepository extends JpaRepository<Task, Long> {

}
