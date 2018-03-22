package com.nasscom.einvoice.constants;

public interface EmailCategory {
	String ALL_MEMBER= "All Members";
	String Member_Email = "Member Email";
	String Region = "Region";
	
	public enum EmailTemplates {
		Invoice,
		Reminder,
		Receipt
		}

}
