package com.nasscom.einvoice.mail;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailSenderImpl implements EmailSender {
	private static final Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);
 
    @Autowired
    private JavaMailSender javaMailSender;
    
    //@Value(value = "classpath:static/nasscom_logo.png")
    //private Resource nasscomLogo;
    
    public EmailStatus sendPlainText(String to, String subject, String text,String from,List<EmailAttachment> attachments,String[]ccList) {
        return sendM(to, subject, text, false,from,attachments,ccList);
    }
 
    public EmailStatus sendHtml(String to, String subject, String htmlBody,String from,List<EmailAttachment> attachments,String[]ccList) {
        return sendM(to, subject, htmlBody, true,from,attachments,ccList);
    }
 
    private EmailStatus sendM(String to, String subject, String text, Boolean isHtml,String from,List<EmailAttachment> attachments,String[]cc) {
    	try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            if(cc!=null&&cc.length>0){
            helper.setCc(cc);
            }
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            
            attachments.forEach(attachment->{
            	try {
					helper.addAttachment(attachment.getName(), attachment.getFile());
				} catch (MessagingException e) {
					logger.error(String.format("Problem with attachment to: {}, error message: {}", to, e.getMessage()));	
				}
            	
            });
           // helper.addInline(nasscomLogo.getFile().getName(),nasscomLogo.getFile());
            javaMailSender.send(mail);
            logger.info("Send email '{}' to: {}", subject, to);
            return new EmailStatus(to, subject, text).success();
        } catch (Exception e) {
            logger.error(String.format("Problem with sending email to: {}, error message: {}", to, e.getMessage()));
            e.printStackTrace();
            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    	finally {
    		//remove all temp attachment files
    		attachments.forEach(attachment->{
    			logger.debug("Remove  attachement from temp Location : {}",attachment.getFile().exists());
    			if(attachment.getFile().exists())attachment.getFile().deleteOnExit();
    		});
    	}
    }
}