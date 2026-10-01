package com.nt.employeerestapi.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidationException(MethodArgumentNotValidException ex) {
		return ex.getBindingResult()
				.getFieldError().
				getDefaultMessage();
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEmployeeNotFound(
	        EmployeeNotFoundException ex) {

	    ErrorResponse error = new ErrorResponse();

	    error.setMessage(ex.getMessage());
	    error.setStatus("NOT_FOUND");

	    return ResponseEntity.status(404).body(error);
	}

}

