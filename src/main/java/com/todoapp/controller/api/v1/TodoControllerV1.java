package com.todoapp.controller.api.v1;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.exceptionhandler.TODOError;
import com.todoapp.formatter.api.v1.TodoCreateRequestFormatter;
import com.todoapp.formatter.api.v1.TodoDeleteRequestFormatter;
import com.todoapp.formatter.api.v1.TodoUpdateRequestFormatter;
import com.todoapp.model.Task;
import com.todoapp.requestformat.api.v1.TodoCreateRequest;
import com.todoapp.requestformat.api.v1.TodoDeleteRequest;
import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.requestformat.api.v1.TodoUpdateRequest;
import com.todoapp.responseformat.api.v1.TodoGeneralResponse;
import com.todoapp.service.MessageByLocale;
import com.todoapp.service.TodoProcessingService;
import com.todoapp.utils.Constants.TodoError;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/")
@Api(value = "Todo App", produces = "application/json", description = "This is Tasks Api version 1.")
public class TodoControllerV1 {

	@Autowired
	private TodoProcessingService todoService;

	@Autowired
	private TodoGetRequest todoGetRequestValidator;

	@Autowired
	private TodoCreateRequest todoCreateRequestValidator;

	@Autowired
	private TodoUpdateRequest todoUpdateRequestValidator;

	@Autowired
	private TodoDeleteRequest todoDeleteRequestValidator;

	@Autowired
	private TodoCreateRequestFormatter todoCreateRequestFormatter;

	@Autowired
	private TodoUpdateRequestFormatter todoUpdateRequestFormatter;

	@Autowired
	private TodoDeleteRequestFormatter todoDeleteRequestFormatter;
	
	@Autowired
	private MessageByLocale messageService;

	@Autowired
	private TODOError errorResponse;

	@RequestMapping(value = "tasks", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody Map<String, TodoGeneralResponse> getTasksAction(
			@RequestParam(name = "data") TodoGetRequest todoGetRequest) {

		DataBinder binder = new DataBinder(todoGetRequest);
		binder.addValidators(todoGetRequestValidator);
		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		List<ObjectError> errors = binder.getBindingResult().getAllErrors();

		TodoGeneralResponse todoResponse = new TodoGeneralResponse();

		if (errors.size() > 0) {
			todoResponse.setReasonCode("0");
			todoResponse.setReasonText(messageService.getMessage("api.response.error.invalid_request"));
			errorResponse.setReasonCode(errors.get(0).getCode());
			errorResponse.setReasonText(messageService.getMessage(errors.get(0).getDefaultMessage()));
			todoResponse.setError(errorResponse);

		} else {

			Object todoServiceResponse = todoService.processTodoGetRequest(todoGetRequest);
			if (todoServiceResponse instanceof List<?>) {

				@SuppressWarnings("unchecked")
				List<Task> tasks = (List<Task>) todoServiceResponse;
				todoResponse.setReasonCode("1");
				todoResponse.setReasonText(messageService.getMessage("api.response.success.valid_request"));
				todoResponse.setTodoGetResponse(todoService.createTodoCRUDResponse(tasks));
			} else {
				TodoError errorResult = (TodoError) todoServiceResponse;
				todoResponse.setReasonCode("0");
				todoResponse.setReasonText(messageService.getMessage("api.response.error.invalid_request"));
				errorResponse.setReasonCode(errorResult.getErrorCode());
				errorResponse.setReasonText(messageService.getMessage(errorResult.getErrorText()));
				todoResponse.setError(errorResponse);
			}
		}

		Map<String, TodoGeneralResponse> responseMap = new HashMap<String, TodoGeneralResponse>();
		responseMap.put("Response", todoResponse);

		return responseMap;
	}

	@RequestMapping(value = "tasks", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, TodoGeneralResponse> createTasksAction(@RequestBody String requestContent,
			Locale locale) throws ParseException {

		TodoCreateRequest todoCreateRequest = todoCreateRequestFormatter.parse(requestContent, locale);

		TodoGeneralResponse todoResponse = new TodoGeneralResponse();
		DataBinder binder = new DataBinder(todoCreateRequest);
		binder.addValidators(todoCreateRequestValidator);
		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		BindingResult result = binder.getBindingResult();

		if (result.hasErrors()) {
			ObjectError error = result.getAllErrors().get(0);
			todoResponse.setReasonCode("0");
			todoResponse.setReasonText(messageService.getMessage("api.response.error.invalid_request"));
			errorResponse.setReasonCode(error.getCode());
			errorResponse.setReasonText(messageService.getMessage(error.getDefaultMessage()));
			todoResponse.setError(errorResponse);
		} else {
			Object todoServiceResponse = todoService.processTodoCreateRequest(todoCreateRequest);
			if (todoServiceResponse instanceof List<?>) {

				@SuppressWarnings("unchecked")
				List<Task> tasks = (List<Task>) todoServiceResponse;
				todoResponse.setReasonCode("1");
				todoResponse.setReasonText(messageService.getMessage("api.response.success.valid_request"));
				todoResponse.setTodoCreateResponse(todoService.createTodoCRUDResponse(tasks));
			} else {
				TodoError errorResult = (TodoError) todoServiceResponse;
				todoResponse.setReasonCode("0");
				todoResponse.setReasonText(messageService.getMessage("api.response.error.invalid_request"));
				errorResponse.setReasonCode(errorResult.getErrorCode());
				errorResponse.setReasonText(messageService.getMessage(errorResult.getErrorText()));
				todoResponse.setError(errorResponse);
			}
		}
		Map<String, TodoGeneralResponse> responseMap = new HashMap<String, TodoGeneralResponse>();
		responseMap.put("Response", todoResponse);

		return responseMap;
	}

	@RequestMapping(value = "tasks", produces = "application/json", method = RequestMethod.PUT)
	public @ResponseBody Map<String, TodoGeneralResponse> updateTasksAction(@RequestBody String requestContent,
			Locale locale) throws ParseException {

		TodoUpdateRequest request = this.todoUpdateRequestFormatter.parse(requestContent, locale);

		DataBinder binder = new DataBinder(request);
		binder.addValidators(todoUpdateRequestValidator);
		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		BindingResult result = binder.getBindingResult();

		TodoGeneralResponse todoResponse = new TodoGeneralResponse();
		if (result.hasErrors()) {
			ObjectError error = result.getAllErrors().get(0);
			todoResponse.setReasonCode("0");
			todoResponse.setReasonText(messageService.getMessage("api.response.error.invalid_request"));
			errorResponse.setReasonCode(error.getCode());
			errorResponse.setReasonText(messageService.getMessage(error.getDefaultMessage()));
			todoResponse.setError(errorResponse);
		} else {
			Object todoServiceResponse = todoService.processTodoUpdateRequest(request);
			if (todoServiceResponse instanceof List<?>) {

				@SuppressWarnings("unchecked")
				List<Task> tasks = (List<Task>) todoServiceResponse;
				todoResponse.setReasonCode("1");
				todoResponse.setReasonText(messageService.getMessage("api.response.success.valid_request"));
				todoResponse.setTodoUpdateResponse(todoService.createTodoCRUDResponse(tasks));
			} else {
				TodoError errorResult = (TodoError) todoServiceResponse;
				todoResponse.setReasonCode("0");
				todoResponse.setReasonText(messageService.getMessage("api.response.error.invalid_request"));
				errorResponse.setReasonCode(errorResult.getErrorCode());
				errorResponse.setReasonText(messageService.getMessage(errorResult.getErrorText()));
				todoResponse.setError(errorResponse);
			}
		}

		Map<String, TodoGeneralResponse> responseMap = new HashMap<String, TodoGeneralResponse>();
		responseMap.put("Response", todoResponse);

		return responseMap;
	}

	@RequestMapping(value = "tasks", produces = "application/json", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, TodoGeneralResponse> deleteTasksAction(
			@RequestBody String requestContent, Locale locale) throws ParseException {
		
		TodoDeleteRequest todoDeleteRequest = todoDeleteRequestFormatter.parse(requestContent, locale);
		DataBinder binder = new DataBinder(todoDeleteRequest);
		binder.addValidators(todoDeleteRequestValidator);
		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		List<ObjectError> errors = binder.getBindingResult().getAllErrors();

		TodoGeneralResponse todoResponse = new TodoGeneralResponse();
		if (errors.size() > 0) {
			todoResponse.setReasonCode("0");
			todoResponse.setReasonText(messageService.getMessage("api.response.error.invalid_request"));
			errorResponse.setReasonCode(errors.get(0).getCode());
			errorResponse.setReasonText(messageService.getMessage(errors.get(0).getDefaultMessage()));
			todoResponse.setError(errorResponse);

		} else {
			Object todoServiceResponse = todoService.processTodoDeleteRequest(todoDeleteRequest);
			if (todoServiceResponse instanceof List<?>) {

				@SuppressWarnings("unchecked")
				List<Task> tasks = (List<Task>) todoServiceResponse;
				todoResponse.setReasonCode("1");
				todoResponse.setReasonText(messageService.getMessage("api.response.success.valid_request"));
				todoResponse.setTodoDeleteResponse(todoService.createTodoCRUDResponse(tasks));
			} else {
				TodoError errorResult = (TodoError) todoServiceResponse;
				todoResponse.setReasonCode("0");
				todoResponse.setReasonText(messageService.getMessage("api.response.error.invalid_request"));
				errorResponse.setReasonCode(errorResult.getErrorCode());
				errorResponse.setReasonText(messageService.getMessage(errorResult.getErrorText()));
				todoResponse.setError(errorResponse);
			}
		}

		Map<String, TodoGeneralResponse> responseMap = new HashMap<String, TodoGeneralResponse>();
		responseMap.put("Response", todoResponse);

		return responseMap;
	}
}
