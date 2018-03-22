package com.nasscom.einvoice.exception;

public class InactiveMemberException extends Exception {
	private static final long serialVersionUID = 1L;

	public InactiveMemberException() {
		super();
	}
	
	public InactiveMemberException(String message) {
		super(message);
	}
}
