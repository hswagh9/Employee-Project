package com.domain.exception;

import com.domain.enums.Status;

public class ServiceException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, String string, Long id) {
		super(message, string, id);
	}

	public ServiceException(String string, String string2, Status status) {
		super(string, string2, status);
	}

}
