package com.todoapp.controller.api.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.exceptionhandler.TODOError;
import com.todoapp.model.Task;
import com.todoapp.requestformat.api.v1.TodoGetRequest;
import com.todoapp.responseformat.api.v1.TodoGeneralResponse;
import com.todoapp.service.MessageByLocale;
import com.todoapp.service.TodoProcessingService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/")
@Api(value = "Todo App", produces = "application/json", description = "This is Tasks Api version 1.")
public class TodoControllerV1 {

	@Autowired
	private TodoProcessingService todoService;
	
	@Autowired
	private TodoGetRequest todoRequestValidator;
	
	@Autowired
	private MessageByLocale messageService;
	
	@Autowired
	private TODOError errorResponse;
	
	/*@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(todoRequestValidator);
    }
	*/
	@RequestMapping(value = "tasks", produces="application/json", method = RequestMethod.GET)
	public @ResponseBody Map<String, TodoGeneralResponse> getTasksAction(@RequestParam(name="data") TodoGetRequest request) {

		DataBinder binder = new DataBinder(request);
		binder.addValidators(todoRequestValidator);
		// validate the target object
		binder.validate();

		// get BindingResult that includes any validation errors
		List<ObjectError> errors = binder.getBindingResult().getAllErrors();
		
		TodoGeneralResponse todoResponse = new TodoGeneralResponse();
		
		if (errors.size() > 0) {
			todoResponse.setReasonCode("0");
			todoResponse.setReasonText(messageService.getMessage("api.success.invalid_request"));
		   errorResponse.setReasonCode(errors.get(0).getCode());
		   errorResponse.setReasonText(messageService.getMessage(errors.get(0).getDefaultMessage()));
		   todoResponse.setError(errorResponse);
		   
		} else {
			todoResponse.setReasonCode("1");
			todoResponse.setReasonText(messageService.getMessage("api.success.valid_request"));
			todoResponse.setTodoGetResponse(
					todoService.createTodoCRUDResponse(todoService.processTodoGetRequest(request)));
		}
		
		Map<String, TodoGeneralResponse> responseMap = new HashMap<String, TodoGeneralResponse>();
		responseMap.put("Response", todoResponse);
		
		return responseMap;
	}

	/*@RequestMapping(value = "tasks", method = RequestMethod.POST)
	public @ResponseBody List<Task> createTasksAction() {
		throw new NullPointerException("");
	}*/

	@RequestMapping(value = "tasks", method = RequestMethod.PUT)
	public @ResponseBody List<Task> updateTasksAction() {
		return null;
	}

	@RequestMapping(value = "tasks", method = RequestMethod.DELETE)
	public @ResponseBody List<Task> deleteTasksAction() {
		return null;
	}
}
