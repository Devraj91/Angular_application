package com.nasscom.einvoice.domain;

import org.springframework.stereotype.Component;

@Component
public class ExceptionResponse {

	public ExceptionResponse() {
		super();
	}

	private int code;

	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
