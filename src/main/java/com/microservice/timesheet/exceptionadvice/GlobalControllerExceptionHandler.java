package com.microservice.timesheet.exceptionadvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ExceptionHandler(CustomBusinessException.class)
	public ResponseEntity<String> handleBusinessException(CustomBusinessException psCustomBusinessException) {
		
		return new ResponseEntity<>(psCustomBusinessException.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleTechnicalException(Exception psCustomBusinessException) {
		
		return new ResponseEntity<>(psCustomBusinessException.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
public ResponseEntity<String> handleAccessDeniedException(Exception psCustomBusinessException) {
		
		return new ResponseEntity<>(psCustomBusinessException.getMessage(), HttpStatus.FORBIDDEN);
		
	}
	

}
