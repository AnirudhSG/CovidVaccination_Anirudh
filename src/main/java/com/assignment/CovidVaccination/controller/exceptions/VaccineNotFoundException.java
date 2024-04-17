package com.assignment.CovidVaccination.controller.exceptions;

public class VaccineNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VaccineNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public VaccineNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public VaccineNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public VaccineNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public VaccineNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
