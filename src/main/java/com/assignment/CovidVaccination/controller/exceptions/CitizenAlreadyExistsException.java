package com.assignment.CovidVaccination.controller.exceptions;

public class CitizenAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CitizenAlreadyExistsException() {
		
	}
	
	public CitizenAlreadyExistsException(String message) {
		super(message);
	}
	
	public CitizenAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	public CitizenAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CitizenAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
