package com.assignment.CovidVaccination.controller.exceptions;

public class CitizenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CitizenNotFoundException() {
		
	}

	public CitizenNotFoundException(String message) {
		super(message);
	}

	public CitizenNotFoundException(Throwable cause) {
		super(cause);
	}

	public CitizenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CitizenNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
