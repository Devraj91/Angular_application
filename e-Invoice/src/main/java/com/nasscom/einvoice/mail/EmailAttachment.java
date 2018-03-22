package com.nasscom.einvoice.mail;

import java.io.File;

public class EmailAttachment {
	
	private String name;
	private File file;
	
	public EmailAttachment(String name, File file) {
		super();
		this.name = name;
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	

}
