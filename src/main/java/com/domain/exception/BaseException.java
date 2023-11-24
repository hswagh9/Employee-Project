package com.domain.exception;

import com.domain.enums.Status;

public abstract class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseException() {
	}

	public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, String detailMessage, Long id) {
		super(message + ": " + detailMessage + " (ID: " + id + ")");
	}

	public BaseException(String message, String detailMessage, Status status) {
		super(message + ": " + detailMessage + " (Status: " + status + ")");
	}
}
