package com.assignment.CovidVaccination.controller.exceptions;

public class VaccineLimitExceedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VaccineLimitExceedException() {
		// TODO Auto-generated constructor stub
	}

	public VaccineLimitExceedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public VaccineLimitExceedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public VaccineLimitExceedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public VaccineLimitExceedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
