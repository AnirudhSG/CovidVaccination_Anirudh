package com.assignment.CovidVaccination.controller.exceptions;

public class InvalidAadharException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidAadharException() {
		
	}

	public InvalidAadharException(String message) {
		super(message);
	}

	public InvalidAadharException(Throwable cause) {
		super(cause);
	}

	public InvalidAadharException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAadharException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
