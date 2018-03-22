package com.nasscom.einvoice.exception;

public class NoRecordFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoRecordFoundException() {
		super();
	}

	public NoRecordFoundException(String message) {
		super(message);
	}
}
