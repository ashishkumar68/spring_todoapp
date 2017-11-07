package com.todoapp;

import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
 
    @ExceptionHandler(value = { ResourceNotFoundException.class })    
    public TODOException resourceNotFoundException(ResourceNotFoundException ex) {
    	return new TODOException("404","Resource requested not found.");
    }
}
