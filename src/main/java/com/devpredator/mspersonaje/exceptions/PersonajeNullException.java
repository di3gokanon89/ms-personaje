/**
 * 
 */
package com.devpredator.mspersonaje.exceptions;

/**
 * @author DevPredator
 * @since v1.0
 * @version v1.0 02/10/2021
 *
 * Custom Exception for Characters.
 */
public class PersonajeNullException extends RuntimeException {
	/**
	 * Initialize the custom exception message.
	 * @param message {@link String} details of the error.
	 */
	public PersonajeNullException(String message) {
		super(message);
	}
}
