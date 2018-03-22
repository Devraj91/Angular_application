package com.nasscom.einvoice.domain;

public class Message {
	public enum MsgCode {
		Success, Fail, Error,Warn,Info
	}

	private MsgCode msgCode;
	private String message;

	public Message(String message) {
		super();
		this.message = message;
	}

	public Message(MsgCode msgCode, String message) {
		super();
		this.msgCode = msgCode;
		this.message = message;
	}

	public MsgCode getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(MsgCode msgCode) {
		this.msgCode = msgCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
