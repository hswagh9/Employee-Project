package com.domain.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.domain.exception.BaseException;
import com.domain.response.entity.RestCustom;
import com.domain.response.entity.RestResponse;
import com.domain.response.entity.RestStatus;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Object> error(BaseException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(responseBody);
	}
	
	
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("Error", "The request method '" + ex.getMethod() + "' is not supported for this endpoint");

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(responseBody);
	}

	public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("Error", "The media type '" + ex.getContentType() + "' is not supported");

		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(responseBody);
	}

	public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("Error", "None of the supported media types is acceptable");

		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseBody);
	}

	public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("Error", "The path variable '" + ex.getVariableName() + "' is missing");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("Error", "The request parameter '" + ex.getParameterName() + "' is missing");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

	public ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("Error", "The request part '" + ex.getRequestPartName() + "' is missing");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

	public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("Error", "The request binding failed: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			String fieldName = error.getField();
			String message = error.getCodes()[0];
			errors.put(fieldName, message);
		});
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errors", errors);
		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, ex.getMessage()), custom);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	}

	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "Asynchronous request timed out");

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(responseBody);
	}

	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "Conversion not supported");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}

	public ResponseEntity<Object> handleConversionNotSupportedException(ConversionNotSupportedException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "A conversion is not supported: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}

	public ResponseEntity<Object> handleTypeMismatchException(TypeMismatchException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "A type mismatch has occurred: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

	public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "The HTTP message is not readable: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

	public ResponseEntity<Object> handleHttpMessageNotWritableException(HttpMessageNotWritableException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "The HTTP message is not writable: " + ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}

	public ResponseEntity<Object> handleBindException(BindException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			String fieldName = error.getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errors", errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

}