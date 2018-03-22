package com.nasscom.einvoice.mail;

import java.util.List;

public interface EmailSender {
	public EmailStatus sendPlainText(String to, String subject, String text, String from,
			List<EmailAttachment> attachments, String[] ccList);

	public EmailStatus sendHtml(String to, String subject, String htmlBody, String from,
			List<EmailAttachment> attachments, String[] ccList);
}
