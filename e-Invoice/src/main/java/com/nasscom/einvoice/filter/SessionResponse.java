package com.nasscom.einvoice.filter;


public class SessionResponse {
	public enum ResponseStatus {
		SUCCESS, ERROR, WARNING, NO_ACCESS
	};

	private SessionItem item;
	private String  operationMessage;
	private ResponseStatus  operationStatus;

	public SessionItem getItem() {
		return item;
	}

	public void setItem(SessionItem item) {
		this.item = item;
	}

	public String getOperationMessage() {
		return operationMessage;
	}

	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}

	public ResponseStatus getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(ResponseStatus operationStatus) {
		this.operationStatus = operationStatus;
	}
}
