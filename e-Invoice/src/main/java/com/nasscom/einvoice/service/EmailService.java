package com.nasscom.einvoice.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.context.Context;

import com.nasscom.einvoice.constants.EmailCategory;
import com.nasscom.einvoice.domain.InvoiceTransactionDetailResponse;
import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.entity.CategoryFee;
import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.CityRegionMapping;
import com.nasscom.einvoice.entity.EmailDetail;
import com.nasscom.einvoice.entity.EmailDetail.Status;
import com.nasscom.einvoice.entity.EmailTemplate;
import com.nasscom.einvoice.entity.InvoiceDetail;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.exception.InactiveMemberException;
import com.nasscom.einvoice.mail.EmailAttachment;
import com.nasscom.einvoice.mail.EmailHtmlSender;
import com.nasscom.einvoice.mail.EmailStatus;
import com.nasscom.einvoice.pdf.CalculatedTax;
import com.nasscom.einvoice.pdf.PdfGenerator;
import com.nasscom.einvoice.repository.BranchRepository;
import com.nasscom.einvoice.repository.CategoryFeeRepository;
import com.nasscom.einvoice.repository.CityRegionMappingRepository;
import com.nasscom.einvoice.repository.EmailDetailRepository;
import com.nasscom.einvoice.repository.EmailTemplateRepository;
import com.nasscom.einvoice.repository.InvoiceRepository;
import com.nasscom.einvoice.repository.MemberRepository;
import com.nasscom.einvoice.utils.Utility;

@Service
public class EmailService {

	private static final String TEMPLATES_EMAIL_URL = "templates/email/";

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	
	@Autowired
	private PdfGenerator pdfgenerator;
	@Autowired
	private EmailHtmlSender emailHtmlSender;
	@Autowired
	private InvoiceService invoiceService;
	@Qualifier("EmailExecutor")
	@Autowired
	private ThreadPoolTaskExecutor exec;
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private EmbeddedWebApplicationContext appContext;
	@Autowired
	UserService userService;
	@Autowired
	EmailDetailRepository emailDetailRepository;
	@Autowired
	CityRegionMappingRepository cityRegionMappingRepository;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	CategoryFeeRepository categoryFeeRepository;

	@Value("${spring.mail.from}")
	private String fromEmail = null;

	public EmailTemplate saveEmailTemplate(EmailTemplate emailTemplateObj) {
		return emailTemplateRepository.save(emailTemplateObj);
	}

	public List<EmailTemplate> getEmailTemplateTypes() {
		List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
		return emailTemplateList;
	}

	public EmailTemplate getEmailTemplateByTemplate(String template) {
		return emailTemplateRepository.findByTemplateType(template);
	}

	public Member getActiveMemberByEmailId(String emailId) {
		Member member= memberRepository.findByEmailIdAndIsActive(emailId, true);
		if(member==null) 
			logger.warn("This Member emailId is Inactive> " +emailId);
		return member;

	}

	public List<Branch> getBranches() {
		return branchRepository.findAll();

	}

	public List<EmailStatus> sendEmail(EmailTemplate emailTemplate, HttpServletRequest request) throws InactiveMemberException {
		return sendEmail(emailTemplate, null, request);
	}

	public List<EmailStatus> sendEmail(EmailTemplate emailTemplate, List<Member> members) throws InactiveMemberException {
		return sendEmail(emailTemplate, members, null);
	}

/*	public List<EmailStatus> sendEmail(EmailTemplate emailTemplate, List<Member> members, HttpServletRequest request) throws InactiveMemberException {
		return sendEmail(emailTemplate, members, request, null);
	}*/

	public List<EmailStatus> sendEmail(EmailTemplate emailTemplate, List<Member> members, HttpServletRequest request) throws InactiveMemberException {
		List<EmailStatus> emailStatusList = new ArrayList<EmailStatus>();
		if (null == members || members.isEmpty())
			members = getMembers(emailTemplate);
		if (emailTemplate.getFrom() == null) {
			emailTemplate.setFrom(fromEmail);
		}
		emailStatusList = mailsender(members, emailTemplate, request, emailStatusList);
		return emailStatusList;
	}

	private List<EmailStatus> mailsender(List<Member> members, EmailTemplate emailTemplate, HttpServletRequest request,
			List<EmailStatus> emailStatusList) {
		List<EmailDetail> emailDetails = new ArrayList<>();
		if (!members.isEmpty()) {
			List<Future<EmailStatus>> futureEmailStatusList = new ArrayList<>();
			for (Member member : members) {
				//check & set member location with branch sender details in auto schedule
				if (null == emailTemplate.getFrom() || null == emailTemplate.getName() || null == emailTemplate.getDesignation())
					prepareEmailTemplate(member, emailTemplate);
				//
				Future<EmailStatus> emailStatus = exec.submit(new Callable<EmailStatus>() {
	
					@Override
					public EmailStatus call() {
						EmailStatus emailStatus = null;
						
						try {
							List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();
							Context context = new Context();
							setContext(emailTemplate, context, member);
							context.setVariable("greeting", "Dear <b>" + member.getName() + "</b>");
							String emailTemplateType=emailTemplate.getTemplateType();
							String[] ccList=member.getCcEmails();
							
							if(EmailCategory.EmailTemplates.Invoice.name().equalsIgnoreCase(emailTemplateType)) {
								InvoiceDetail invoiceDetailForCurrentFY =setInvoiceDynamicContent( member, context);
								attachments.addAll(prepareInvoiceEmailAttachments(invoiceDetailForCurrentFY,member));
								emailDetails.addAll(prepareInitialEmailDetails(member, invoiceDetailForCurrentFY, emailTemplate));
							} else if(EmailCategory.EmailTemplates.Receipt.name().equalsIgnoreCase(emailTemplateType)) {
								InvoiceDetail invoiceDetailForCurrentFY = invoiceService.getInvoiceDetailCurrentFY(member);
								attachments.addAll(prepareReceiptEmailAttachments(invoiceDetailForCurrentFY,member));
								emailDetails.addAll(prepareInitialEmailDetails(member, invoiceDetailForCurrentFY, emailTemplate));		
							}else {
								InvoiceDetail invoiceDetailForCurrentFY = invoiceService.getInvoiceDetailCurrentFY(member);
								attachments.addAll(prepareInvoiceEmailAttachments(invoiceDetailForCurrentFY,member));
								emailDetails.addAll(prepareInitialEmailDetails(member, invoiceDetailForCurrentFY, emailTemplate));
								setDynamicContent(emailTemplate, member, context, request, attachments, emailDetails);
							}
							
							logger.info("Shooting Email for "+emailTemplate.getCategory());
							emailStatus=new EmailStatus(member.getEmailId(),emailTemplate.getSubject(),emailTemplate.getBody());
							InvoiceDetail invoiceDetail = invoiceService.getInvoiceDetailCurrentFY(member); 
							String toEmail = invoiceDetail.getToEmail();
							// shoot mail
							emailStatus = emailHtmlSender.send(toEmail, emailTemplate.getSubject(),
									TEMPLATES_EMAIL_URL + emailTemplate.getTemplateType(), context,
									emailTemplate.getFrom(), attachments, ccList);
						} catch (MessagingException e) {
							logger.error("Unable to Send {} ", e);
							String errorMessage="Unable to Send {}";
							//emailStatus=new EmailStatus(member.getEmailId(),emailTemplate.getSubject(),emailTemplate.getBody());
							emailStatus=new EmailStatus(member.getEmailId(),emailTemplate.getSubject(),emailTemplate.getBody());
							emailStatus.error(errorMessage);
						} catch (IOException e) {
							logger.error("Unable to IO the mail content {} ", e);
							String errorMessage="Unable to IO the mail content {}";
							emailStatus=new EmailStatus(member.getEmailId(),emailTemplate.getSubject(),emailTemplate.getBody());
							emailStatus.error(errorMessage);
						} catch (Exception e) {
							logger.error("Error in mail content {} ", e);
							String errorMessage="Error in mail content {}"+e;
							emailStatus=new EmailStatus(member.getEmailId(),emailTemplate.getSubject(),emailTemplate.getBody());
							emailStatus.error(errorMessage);
						}
						emailStatus.success();
						return emailStatus;
					}
				});

				futureEmailStatusList.add(emailStatus);
			}

			saveEmailDetails(emailStatusList, emailDetails, futureEmailStatusList);
		}
		return emailStatusList;
	}

	private void prepareEmailTemplate(Member member, EmailTemplate emailTemplate) {
		// prepare email template according member location
		Branch branch = memberService.getBranch(member);
		if(branch != null) {
			logger.debug("Member :=>{} , associated with branch :=>{}",member,branch);
			if(branch.getSenderDetail()!=null) {
				emailTemplate.setFrom(branch.getSenderDetail().getEmail());
				emailTemplate.setDesignation(branch.getSenderDetail().getDesignation());
				emailTemplate.setName(branch.getSenderDetail().getName());
			}
		}
	}

	private void saveEmailDetails(List<EmailStatus> emailStatusList, List<EmailDetail> emailDetails,
			List<Future<EmailStatus>> futureEmailStatusList) {
		for (Future<EmailStatus> eachEamilStatus : futureEmailStatusList) {
			try {
				EmailStatus emailStatus = eachEamilStatus.get();
				emailStatusList.add(emailStatus);
				// prepare email details
				emailDetails.stream().filter(emailDetail -> emailStatus.getTo().equalsIgnoreCase(emailDetail.getTo()))//if
						.forEach(emailDetail -> {
							emailDetail.setStatus(Status.valueOf(emailStatus.getStatus()));
							emailDetail.setReason(emailStatus.getErrorMessage());
						});
				System.out.println("emailDetails "+ emailDetails);
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Unable to prepare EmailDetails {} ", e);
			}
			catch (Exception e) {
				logger.error("Unable to prepare EmailDetails {} ", e);
			}
		}
		// save into db the email details
		emailDetailRepository.save(emailDetails);
	}

	private void setContext(EmailTemplate emailTemplate, Context context, Member member) {
		context.setVariable("title", emailTemplate.getSubject());
		context.setVariable("thankYouMsg", emailTemplate.getPs());
		context.setVariable("description", emailTemplate.getBody());
		context.setVariable("name", emailTemplate.getName());
		context.setVariable("signature", emailTemplate.getSignature());
		context.setVariable("designation", emailTemplate.getDesignation());
		context.setVariable("ps", emailTemplate.getPs());
		context.setVariable("logoImage", "nasscom_logo.png");
	}

	private String getPaymentUrl(Member member,String invoiceId) {
		String baseUrl = "";
		//String token = "";
		try {
			baseUrl = getBaseUrl();
			//token = userService.getTokenForPayment(member);
		} catch (UnknownHostException e) {
			logger.debug("Invalid Payment Url generateted, due to {}", e);
			e.printStackTrace();
		} catch (Exception ex) {
			logger.debug("Token not for Payment url generateted, due to {}", ex);
			ex.printStackTrace();
		}
		logger.debug("Base Url : {}", baseUrl);
		//return baseUrl + "#/payment?invoiceId="+invoiceId+"&token=" + token;
		return baseUrl + "#/payment?invoiceId="+invoiceId;

	}

	public String getBaseUrl() throws Exception {
		Connector connector = ((TomcatEmbeddedServletContainer) appContext.getEmbeddedServletContainer()).getTomcat()
				.getConnector();
		String scheme = connector.getScheme();
		String ip = "";
		URL url_name = new URL("http://bot.whatismyipaddress.com");
		BufferedReader sc =
        new BufferedReader(new InputStreamReader(url_name.openStream()));
		// reads system IPAddress
        ip = sc.readLine().trim();
        //System.out.println("Public IP Address: " + systemipaddress +"\n");
		int port = connector.getPort();
		String contextPath = appContext.getServletContext().getContextPath();
		return scheme + "://" + ip + ":" + port + contextPath;
	}

	private void setDynamicContent(EmailTemplate emailTemplate, Member member, Context context,
			HttpServletRequest request, List<EmailAttachment> attachments, List<EmailDetail> emailDetails)
			throws Exception {
		switch (EmailCategory.EmailTemplates.valueOf(emailTemplate.getTemplateType())) {
		case Reminder:
			getDynamicReminder(member, context);
			break;
		case Receipt:
			break;
		default:
			break;
		}
	}
	
	private InvoiceDetail setInvoiceDynamicContent(Member member, Context context) throws Exception {
		InvoiceDetail invoiceDetailForCurrentFY = invoiceService.getInvoiceDetailCurrentFY(member);
		if (invoiceDetailForCurrentFY != null) {
			String invoiceFileId = invoiceDetailForCurrentFY.getInvoiceFile().getId() + "";
			logger.debug("setInvoiceDynamicContent invoiceFileId " + invoiceFileId);
			//set invoiceFile id for payment u rl to check url expire
			context.setVariable("paymentUrl", getPaymentUrl(member, invoiceFileId));// "/member/payment?token=xxx-xxx-xxx-xxx-xxxx");
		}
		return invoiceDetailForCurrentFY;
	}

	private void getDynamicReminder(Member member, Context context) throws Exception{
		String currentFinancialYear=Utility.getCurrentFinancialYear();//eg. format 2017-2018
		logger.info("currentFinancialYear--> "+currentFinancialYear );
		InvoiceDetail invoiceDetailForCurrentFY = invoiceRepository.findByMemberAndYear(member,currentFinancialYear);
		Boolean isAllPaid=false;
		isAllPaid=invoiceService.isAllPaid(invoiceDetailForCurrentFY);
		if(isAllPaid)
			throw new Exception("Member " +member.getEmailId()+" has fully paid his taxes and invoice amount. Reminder Mail Can not be sent");
		Long invoiceId=0l;
		Double totalPaidAmount=0.0;
		Double balanceAmount=0.0;
		Double invoiceAmount=0.0;
		
		if(invoiceDetailForCurrentFY!=null)
			invoiceId=invoiceDetailForCurrentFY.getInvoiceId();
		
		if(invoiceDetailForCurrentFY!=null ){
			invoiceAmount=invoiceDetailForCurrentFY.getInvoiceAmt()+invoiceDetailForCurrentFY.getTaxAmt();
			totalPaidAmount=invoiceService.getTotalPaidAmountPerInvoice(invoiceId);
			InvoiceTransactionDetailResponse outStandingPaymentDetails=invoiceService.getOutstandingPaymentDetails(invoiceId);
			if(outStandingPaymentDetails!=null)
				balanceAmount=outStandingPaymentDetails.getInvoiceAmt()+outStandingPaymentDetails.getTaxAmt()-outStandingPaymentDetails.getOverDraftAmt();
		 }else{//TODO :not sure is this else required?How reminder can be sent without invoiceDetail 
				Double taxAmount=0.0;
				Double subscriptionAmount=memberService.getSubscription(member);
				
				//City branchCity = invoiceService.getBranchCity(member);
				
				City invoiceCity = member.getAddress().getCity();
				City branchCity=invoiceCity;
				List<CalculatedTax> taxAmountList=invoiceService.calculateTaxes(branchCity, invoiceCity, member);
				
				for(CalculatedTax calculatedTax:taxAmountList) {
					taxAmount+=calculatedTax.getTaxCharge();
				}
				
				invoiceAmount=subscriptionAmount+taxAmount;
				totalPaidAmount=0.0D;
				balanceAmount=invoiceAmount;
			}
		//
		
		
		CategoryFee categoryFee = categoryFeeRepository.findByCategory(member.getCategory());
		if(null==categoryFee) {throw new Exception("Invalid Member Category ");}	 
		context.setVariable("invoiceAmt", invoiceAmount);
		context.setVariable("totalPaid", totalPaidAmount);
		context.setVariable("balance", balanceAmount);
		context.setVariable("date", LocalDateTime.now().toLocalDate().plusWeeks(21));//3 months
		context.setVariable("time", LocalDateTime.now().toLocalTime());
	}
	
	
	private List<EmailAttachment>  prepareInvoiceEmailAttachments(InvoiceDetail invoiceDetailForCurrentFY,Member member) throws Exception {
		List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();
		final String INVOICE_TEMPLATE = "templates/pdf/invoice";
		final String PERFORMA_INVOICE_TEMPLATE = "templates/pdf/performa_invoice";

		byte[] pdfByte;
		if(invoiceDetailForCurrentFY.getIsPerforma())
			pdfByte = pdfgenerator.createPdf(PERFORMA_INVOICE_TEMPLATE, invoiceService.getInvoiceTemplateInput(invoiceDetailForCurrentFY, member));
		else
			pdfByte = pdfgenerator.createPdf(INVOICE_TEMPLATE, invoiceService.getInvoiceTemplateInput(invoiceDetailForCurrentFY, member));
		
		// create member invoice temp file
		File tempFile = File.createTempFile(invoiceDetailForCurrentFY.getInvoiceFile().getFileName(), ".pdf");
		try (FileOutputStream fos = new FileOutputStream(tempFile);) {
			fos.write(pdfByte);
		}
		
		// add attachment
		attachments.add(new EmailAttachment(invoiceDetailForCurrentFY.getInvoiceFile().getFileName(), tempFile));
		
		logger.debug("prepareInvoiceMailAttachments "+attachments);
		return attachments;
	}
	
	private List<EmailAttachment>  prepareReceiptEmailAttachments(InvoiceDetail invoiceDetailForCurrentFY,Member member) throws Exception {
		List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();
		
		final String RECEIPT_TEMPLATE = "templates/pdf/InvoiceReceipt";
		byte[] pdfByte;
		String fileName=invoiceService.getReceiptFileName(member);
		String receiptDate=invoiceService.getFormatDate();
		String receiptNo=invoiceService.getReceiptNo();
		pdfByte = pdfgenerator.createPdf(RECEIPT_TEMPLATE, invoiceService.getInvoiceReceiptTemplateInput(invoiceDetailForCurrentFY, member,receiptDate,receiptNo));
		// create member invoice temp file
		File tempFile = File.createTempFile(fileName, ".pdf");
		try (FileOutputStream fos = new FileOutputStream(tempFile);) {
			fos.write(pdfByte);
		}
		
		invoiceService.generateInvoiceReceiptFile(invoiceDetailForCurrentFY,pdfByte,fileName,receiptDate,receiptNo);
		// add attachment
		attachments.add(new EmailAttachment(fileName, tempFile));
		logger.debug("prepareInvoiceMailAttachments "+attachments);
		return attachments;
	}
	
	private List<EmailDetail> prepareInitialEmailDetails(Member member, InvoiceDetail invoiceDetailForCurrentFY,
			EmailTemplate emailTemplate) {

		List<EmailDetail> emailDetails = new ArrayList<EmailDetail>();
		// prepare initial emailDetails
		switch (EmailCategory.EmailTemplates.valueOf(emailTemplate.getTemplateType())) {
		case Invoice: 
			emailDetails.add(new EmailDetail(member.getEmailId(), emailTemplate.getSubject(),
				invoiceDetailForCurrentFY.getInvoiceFile(),null,null, "", member));
			break;
		case Receipt: 
			emailDetails.add(new EmailDetail(member.getEmailId(), emailTemplate.getSubject(),
					null,invoiceDetailForCurrentFY.getInvoiceReceiptFile(), null, "", member));
		case Reminder: 
			emailDetails.add(new EmailDetail(member.getEmailId(), emailTemplate.getSubject(),
					invoiceDetailForCurrentFY.getInvoiceFile(),null, null, "", member));			
		default:
			break;
		}
		logger.debug("prepareInitialEmailDetails "+emailDetails);
		return emailDetails;
	}

	public List<Member> getMembers(EmailTemplate emailTemplate) throws InactiveMemberException {
		List<Member> members = new ArrayList<Member>();
		switch (EmailCategory.EmailTemplates.valueOf(emailTemplate.getTemplateType())) {
		case Reminder:
			members = getMemberCategoryWise(emailTemplate, members);
			break;
		case Invoice:
			members = getMemberCategoryWise(emailTemplate, members);
			break;
		case Receipt:
			members = getMemberCategoryWise(emailTemplate, members);
			break;
		default:
			break;
		}
		return members;
	}

	private List<Member> getMemberCategoryWise(EmailTemplate emailTemplate, List<Member> members) throws InactiveMemberException {
		Assert.notNull(emailTemplate.getCategory(), "EmailCategory must not be null");
		logger.debug("emailtemplate->"+emailTemplate);
		logger.debug("emailtemplate category->"+emailTemplate.getCategory());
		switch (emailTemplate.getCategory()) {
		case EmailCategory.ALL_MEMBER:
			members.addAll(memberRepository.findByIsActive(true));
			break;
		case EmailCategory.Region: {
			Branch branch = branchRepository.findByName(emailTemplate.getRegionName());
			List<CityRegionMapping> cityRegionMappings = cityRegionMappingRepository.findByBranchId(branch.getId());
			for(CityRegionMapping cityregion:cityRegionMappings) {
				City city = cityregion.getCity();
				List<Member> membersInCity = memberRepository.findByAddressCity(city);
				if(membersInCity!=null) members.addAll(memberRepository.findByAddressCity(city));
			}

			break;
		}
		case EmailCategory.Member_Email: {
			if (!emailTemplate.getEmail().contains(",")) {
				Member activeMember = null;
				InvoiceDetail invDetail = invoiceRepository.findByToEmail(emailTemplate.getEmail());
				if(null == invDetail)
					activeMember=getActiveMemberByEmailId(emailTemplate.getEmail());
				else
					activeMember= memberRepository.findByMembershipIDAndIsActive((invDetail.getMember().getMembershipID()),true);
				if(null==activeMember) {
					logger.error("Inactive Member. Mail can not be sent. "+ emailTemplate.getEmail());
					throw new InactiveMemberException("Inactive Member. Mail can not be sent.");
				}
				members.add(activeMember);
			}
			else
				members.addAll(getMemberList(emailTemplate.getEmail().split(",")));
		}
		default:
			break;
		}
		return members;
	}

	public List<Member> getMemberList(String[] emailId) {
		Assert.notNull(emailId, "Member Email Id must not be null");
		if (emailId.length == 0) {
			return null;
		}
		List<Member> memberList = new ArrayList<Member>();
		for (int i = 0; i < emailId.length; i++) {
			InvoiceDetail invDetail = invoiceRepository.findByToEmail(emailId[i]);
			if(invDetail == null)
				memberList.add(memberRepository.findByEmailIdAndIsActive(emailId[i],true));
			else {
				Member activeMember=invDetail.getMember();
				memberList.add(memberRepository.findByMembershipIDAndIsActive(activeMember.getMembershipID(),true));
			}
		}
		return memberList;
	}


	public void executeReminderMailTask() {
		logger.debug("executeReminderMailTask()");
		long startTime = System.currentTimeMillis();
		try {
			EmailTemplate emailTemplate = emailTemplateRepository.findByTemplateType(EmailCategory.EmailTemplates.Reminder.name());
			emailTemplate.setCategory(EmailCategory.ALL_MEMBER);
			List<EmailStatus> emailStatus = sendEmail(emailTemplate, new ArrayList<>());
			logger.debug("Reminder Mail Processed for : {} -> members", emailStatus.size());
		} catch (Exception ex) {
			logger.error("Get issue to process Reminder Mail Task , will try next schedule. {}", ex);
		}
		logger.debug("ReminderMailTask Exection Time taken in (ms) : {}", (System.currentTimeMillis() - startTime));
	}	
	
}
