package com.rocha.barbalho.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestServiceExcepetion extends IllegalArgumentException {
	
	private static final long serialVersionUID = -1906067772639846642L;

	public BadRequestServiceExcepetion(String message) {
		super(message);
	}

	public BadRequestServiceExcepetion(String message, Throwable cause) {
		super(message, cause);
	}

}
