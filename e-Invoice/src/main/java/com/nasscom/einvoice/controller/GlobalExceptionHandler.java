package com.nasscom.einvoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nasscom.einvoice.domain.ExceptionResponse;
import com.nasscom.einvoice.exception.InactiveMemberException;
import com.nasscom.einvoice.exception.NoRecordFoundException;
import com.nasscom.einvoice.exception.PageNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@Autowired
	ExceptionResponse er;

	@ExceptionHandler(PageNotFoundException.class)
	public ResponseEntity<ExceptionResponse> generalException(PageNotFoundException e) throws Exception {
		er.setCode(HttpStatus.BAD_REQUEST.value());
		er.setMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);

	}
	
	@ExceptionHandler(NoRecordFoundException.class)
	public ResponseEntity<ExceptionResponse> noRecordFoundException(NoRecordFoundException e) throws Exception {
		er.setCode(HttpStatus.NOT_FOUND.value());
		er.setMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);

	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> noRecordFoundException(Exception e) throws Exception {
		er.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		er.setMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);

	}

	@ExceptionHandler(InactiveMemberException.class)	
	    public ResponseEntity<ExceptionResponse> inactiveMemberException(Exception e) throws Exception {
        er.setCode(HttpStatus.BAD_REQUEST.value());
        er.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
	}
}
