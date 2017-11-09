package com.todoapp.service;

import org.springframework.stereotype.Component;

@Component
public interface MessageByLocale {

	public String getMessage(String id);
}
