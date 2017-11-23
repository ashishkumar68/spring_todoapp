package com.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.todoapp.model.User;

@Repository
@RepositoryRestResource(exported=false)
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);
}
