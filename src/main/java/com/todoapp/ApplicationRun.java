package com.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.todoapp.interceptor.RequestInterceptor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ApplicationRun extends WebMvcConfigurerAdapter {

	/*@Autowired
	private TodoGetRequestFormatter todoGetRequestFormatter;*/

	/*@Autowired
	private TodoDeleteRequestFormatter todoDeleteRequestFormatter;*/

	public static void main(String[] args) {
		SpringApplication.run(ApplicationRun.class, args);
	}

	@Autowired
	private RequestInterceptor requestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor);
	}

	/*@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(todoGetRequestFormatter);
		registry.addFormatter(todoDeleteRequestFormatter);
	}*/
}