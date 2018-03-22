package com.nasscom.einvoice.constants;

public interface MemberConstants {
	enum transType {
		MANUAL("Offline"), AUTOMATIC("Online");
		private String value;

		transType(String value) {
			this.value = value;
		}
	}
	
	public final static String SUCCESS="SUCCESS";
}
