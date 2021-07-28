/**
 * 
 */
package com.devpredator.mspersonaje.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Custom Exception class that defines our attributes for more clearly details of a message
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 25/07/2021
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
	/**
	 * Date when the exception happened.
	 */
	private LocalDateTime date;
	/**
	 * Error's message.
	 */
	private String message;
	/**
	 * Error's details.
	 */
	private String details;
	/**
	 * Map with errors.
	 */
	private Map<String, String> validationErrors;
	
}
