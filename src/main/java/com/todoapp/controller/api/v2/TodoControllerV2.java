package com.todoapp.controller.api.v2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v2/")
@Api(value="Todo App", produces="application/json", description="This is Tasks Api version 2.")
public class TodoControllerV2 {
}
