package com.todoapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.todoapp.formatter.api.v1.TodoGetRequestFormatter;
import com.todoapp.interceptor.RequestInterceptor;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ApplicationRun extends WebMvcConfigurerAdapter {

	/*
	@Autowired
	private ValidatorConfig validatorConfig;
	*/
	public static void main(String[] args) {
		SpringApplication.run(ApplicationRun.class, args);
	}

	@Autowired
	RequestInterceptor requestInterceptor;

	@Autowired
	TodoGetRequestFormatter todoGetRequestFormatter;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor);
		//registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(todoGetRequestFormatter);
	}

	/*@Override
	public Validator getValidator() {
		return validatorConfig.validator();
	}*/
	
	
}