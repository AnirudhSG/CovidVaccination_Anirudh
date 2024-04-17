package com.assignment.CovidVaccination.controller.exceptions;

public class MinimumPeriodViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MinimumPeriodViolationException() {
		// TODO Auto-generated constructor stub
	}

	public MinimumPeriodViolationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MinimumPeriodViolationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public MinimumPeriodViolationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MinimumPeriodViolationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
