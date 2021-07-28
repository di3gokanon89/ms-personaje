/**
 * 
 */
package com.devpredator.mspersonaje.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Generic Exception class that get all kind of exceptions in out Controllers classes.
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 25/07/2021
 */
@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	public final ResponseEntity<?> handlingExceptions(Exception exception) {
		ExceptionResponse er = new ExceptionResponse(
				LocalDateTime.now(), "Ocurrió un error", exception.getMessage(), null);
		
		return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest webRequest) {
		
		//Create a new mapa that will be contains all the validations errors.
		Map<String, String> errors = new HashMap<>();
		
		//Get all errors and iterate them to get the name of the field error and the message.
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			
			//Add all errors to the map.
			errors.put(fieldName, errorMessage);
		});
		
		ExceptionResponse er = new ExceptionResponse(
				LocalDateTime.now(), "Ocurrió un error", ex.getMessage(), errors);
		
		return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
	}
}
