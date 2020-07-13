package com.rocha.barbalho.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundServiceException extends IllegalArgumentException {

	private static final long serialVersionUID = -1906067772639846645L;

	public NotFoundServiceException(String message) {
		super(message);
	}

	public NotFoundServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
