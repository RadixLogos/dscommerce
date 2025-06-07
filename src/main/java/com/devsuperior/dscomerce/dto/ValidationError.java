package com.devsuperior.dscomerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{
	private List<FildMessage> errors = new ArrayList<>();
	
	public ValidationError(Instant timestamp, Integer status, String error, String path) {
		super(timestamp, status, error, path);
	}

	public List<FildMessage> getErrors() {
		return errors;
	}
	
	public void addErrors(String field, String message) {
		errors.add(new FildMessage(field, message));
	}
}
