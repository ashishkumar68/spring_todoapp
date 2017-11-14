package com.todoapp.exceptionhandler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * For 404 Exception
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		TODOError apiException = new TODOError(HttpStatus.NOT_FOUND.toString(), ex.getLocalizedMessage());
		Map<String, TODOError> exceptionMap = new HashMap<String, TODOError>();
		exceptionMap.put("TODOException", apiException);
		return new ResponseEntity<Object>(exceptionMap, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	/**
	 * For 405 Exception
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		TODOError apiException = new TODOError(HttpStatus.METHOD_NOT_ALLOWED.toString(),
				ex.getLocalizedMessage());
		Map<String, TODOError> exceptionMap = new HashMap<String, TODOError>();
		exceptionMap.put("TODOException", apiException);
		return new ResponseEntity<Object>(exceptionMap, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * For 500 Exception
	 * 
	 */
	@ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class, Exception.class,
		MethodArgumentConversionNotSupportedException.class, 
		ParseException.class, MethodArgumentTypeMismatchException.class, Exception.class})
	protected ResponseEntity<Object> handleInternal(RuntimeException ex, WebRequest request) {
		
		TODOError apiException = new TODOError(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				ex.getLocalizedMessage());
		Map<String, TODOError> exceptionMap = new HashMap<String, TODOError>();
		exceptionMap.put("TODOException", apiException);
		return new ResponseEntity<Object>(exceptionMap, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return super.handleMissingPathVariable(ex, headers, status, request);
	}
	
	/**
	 *  For 400 Exception
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		TODOError apiException = new TODOError(HttpStatus.BAD_REQUEST.toString(),
				ex.getLocalizedMessage());
		Map<String, TODOError> exceptionMap = new HashMap<String, TODOError>();
		exceptionMap.put("TODOExecption", apiException);
		return new ResponseEntity<Object>(exceptionMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}
