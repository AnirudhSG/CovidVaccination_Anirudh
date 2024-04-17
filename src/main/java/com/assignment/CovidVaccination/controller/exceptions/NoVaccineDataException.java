package com.assignment.CovidVaccination.controller.exceptions;

public class NoVaccineDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoVaccineDataException() {
		// TODO Auto-generated constructor stub
	}

	public NoVaccineDataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoVaccineDataException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoVaccineDataException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoVaccineDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
