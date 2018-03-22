package com.nasscom.einvoice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nasscom.einvoice.domain.Message;
import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.entity.EmailDetail;
import com.nasscom.einvoice.entity.EmailTemplate;
import com.nasscom.einvoice.service.EmailService;
import com.nasscom.einvoice.service.MemberService;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.entity.SenderDetail;
import com.nasscom.einvoice.exception.InactiveMemberException;


@RestController
@RequestMapping(value = "/emailTemplate")
public class EmailController {
	@Autowired
	EmailService emailService;
	@Autowired
	MemberService memberService;

	@RequestMapping(value = "/getEmailTemplateTypes", method = RequestMethod.GET)
	public List<EmailTemplate> getEmailTemplateTypes() {
		List<EmailTemplate> et = emailService.getEmailTemplateTypes();
		 et.remove(emailService.getEmailTemplateByTemplate("Receipt"));
		 return et;
	}
	

	@RequestMapping(value = "/getEmailTemplateByTemplate", method = RequestMethod.GET)
	public EmailTemplate getEmailTemplateByTemplate(@RequestParam(value = "template") String template) {
		return emailService.getEmailTemplateByTemplate(template);
	}

	@RequestMapping(value = "/getRegions", method = RequestMethod.GET)
	public List<Branch> getRegions() {
		return memberService.getBranches();
	}

	@RequestMapping(value = "/getMembers", method = RequestMethod.GET)
	public List<Member> getMembers() {
		return memberService.findAll();
	}

	@RequestMapping(value = "/getSenderDetails", method = RequestMethod.GET)
	public List<SenderDetail> getSenderDetails() {
		return memberService.getSenderDetails();
	}

	@RequestMapping(value = "/sendMail",method = RequestMethod.POST,consumes="application/json",produces="application/json")
    public Message sentMail(@RequestBody EmailTemplate emailTemplate, HttpServletRequest request) throws InactiveMemberException {
		emailService.sendEmail(emailTemplate,request);
		return new Message(Message.MsgCode.Info,"Email Sent Request Accepted.");
	}
	
	@RequestMapping(value = "/sendReceipt",method = RequestMethod.POST,consumes="application/json",produces="application/json")
    public Message sentReceipt(@RequestBody Member member, HttpServletRequest request) throws InactiveMemberException {
		member = memberService.getMemberById(member.getMemberId());
		List<Member> members = new ArrayList<Member>();
		members.add(member);
		EmailTemplate emailTemplate = emailService.getEmailTemplateByTemplate("Receipt"); 
		emailService.sendEmail(emailTemplate,members,request);
		return new Message(Message.MsgCode.Success,"Email Receipt Sent Successfully.");
	}

	@RequestMapping(value = "/getMails")
	public List<EmailDetail> getAllMails(@RequestParam String memberId) {
		return new ArrayList<>();
	}

}
