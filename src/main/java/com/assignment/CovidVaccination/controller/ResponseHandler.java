package com.assignment.CovidVaccination.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	// Method to generate json response object
	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("message", message);
		map.put("status", status.value());
		map.put("data", responseObj);
		
		return new ResponseEntity<Object>(map, status);
	}
	
	// Method to generate json error object
	public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", new ErrorResponse(
				status.value(),
				message,
				java.time.LocalDateTime.now()));
		
		return new ResponseEntity<Object>(map, status);
	}
}
