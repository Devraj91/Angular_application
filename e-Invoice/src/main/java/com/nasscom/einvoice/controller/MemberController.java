package com.nasscom.einvoice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nasscom.einvoice.domain.InvoiceTransactionDetailResponse;
import com.nasscom.einvoice.domain.Message;
import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.InvoiceDetail;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.entity.User;
import com.nasscom.einvoice.exception.PageNotFoundException;
import com.nasscom.einvoice.filter.TokenService;
import com.nasscom.einvoice.pdf.CalculatedTax;
import com.nasscom.einvoice.repository.InvoiceRepository;
import com.nasscom.einvoice.service.InvoiceService;
import com.nasscom.einvoice.service.MemberService;
import com.nasscom.einvoice.utils.Utility;

@RestController
@RequestMapping("/member")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Autowired
	MemberService memberService;
	@Autowired
	TokenService tokenService;
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	InvoiceRepository invoiceRepository;	
	@RequestMapping(value = "/get", params = { "page", "size" }, method = RequestMethod.GET)
	public Page<Member> getMembers(@RequestParam("page") int page, @RequestParam("size") int size)
			throws Exception {
		Page<Member> resultPage = memberService.findPaginated(page, size);
		for(Member member:resultPage)
		{
			String currentFinancialYear=Utility.getCurrentFinancialYear();//eg. format 2017-2018
			logger.info("currentFinancialYear--> "+currentFinancialYear );
			InvoiceDetail invoiceDetailForCurrentFY = invoiceRepository.findByMemberAndYear(member,currentFinancialYear);
			Long invoiceId=0l;
			
			if(invoiceDetailForCurrentFY!=null)
				invoiceId=invoiceDetailForCurrentFY.getInvoiceId();
				
			if(invoiceDetailForCurrentFY!=null ){
				Double totalPaidAmount=0.0;
				Double balanceAmount=0.0;
				
				totalPaidAmount=invoiceService.getTotalPaidAmountPerInvoice(invoiceId);
				InvoiceTransactionDetailResponse outStandingPaymentDetails=invoiceService.getOutstandingPaymentDetails(invoiceId);
				if(outStandingPaymentDetails!=null)
					balanceAmount=outStandingPaymentDetails.getInvoiceAmt()+outStandingPaymentDetails.getTaxAmt()-outStandingPaymentDetails.getOverDraftAmt();
				
				member.setInvoiceAmt(invoiceDetailForCurrentFY.getInvoiceAmt()+invoiceDetailForCurrentFY.getTaxAmt());
				member.setPaidAmt(totalPaidAmount);
				member.setBalanceAmt(balanceAmount);
			}else{
				Double taxAmount=0.0;
				Double invoiceAmount=memberService.getSubscription(member);
				
				//City branchCity = invoiceService.getBranchCity(member);
				City invoiceCity = member.getAddress().getCity();
				City branchCity =invoiceCity;
				List<CalculatedTax> taxAmountList=invoiceService.calculateTaxes(branchCity, invoiceCity, member);
				
				for(CalculatedTax calculatedTax:taxAmountList) {
					taxAmount+=calculatedTax.getTaxCharge();
				}
				
				member.setInvoiceAmt(invoiceAmount+taxAmount);
				member.setPaidAmt(0.0D);
				if(null!=member.getInvoiceAmt())
				member.setBalanceAmt(member.getInvoiceAmt());
			}
		}
		if (page > resultPage.getTotalPages()) {
			throw new PageNotFoundException("No-More-record-found");
		}
		return resultPage;
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<Member> getAllMembers() {
		return memberService.findAll();

	}
	
	@RequestMapping(value = "/get/allbranches", method = RequestMethod.GET)
	public List<Branch> getAllBranches() {
		return memberService.getBranches();

	}
	
	@RequestMapping(value = "/get/branch", method = RequestMethod.GET)
	public Branch getBranch(@RequestBody Member member) {
		return memberService.getBranch(member);

	}

	@RequestMapping(value = "/syncmember/{ids:.+}", method = RequestMethod.PUT)
	public List<Member> syncMember(@PathVariable(value = "ids") List<String> memberIds) {
		return memberService.syncMemberManual(memberIds.toArray(new String[0]));
	}

	@RequestMapping(value = "/get/category/all", method = RequestMethod.GET)
	public List<Character> getAllMemberCategories() {
		return memberService.findDistinctByCategory();

	}

	@RequestMapping(value = "/get/category/{category}", method = RequestMethod.GET)
	public List<Member> getAllMembersCategoryWise(@PathVariable(value = "category") String category) {
		return memberService.findByCategory(category);

	}

//	@RequestMapping(value = "/updateSubscription/{category}/{memberId}/{subscriptionFee}", method = RequestMethod.PUT)
//	public Object updateSubscription(@PathVariable(value = "category") Character category,
//			@PathVariable(value = "memberId") String memberId,
//			@PathVariable(value = "subscriptionFee") Double subscriptionFee) {
//		if (null == category && memberId.isEmpty() && null != subscriptionFee) {
//			return new Message(Message.MsgCode.Warn, "Category, memberId and subscription Required.");
//		}else { 
//			 try{
//				 memberService.updateSubscription(category, memberId, subscriptionFee);
//			 }catch(Exception ex) {
//				 return new Message(Message.MsgCode.Error, ex.getMessage()); 
//			 }
//			 }
//		return new Message(Message.MsgCode.Info, "Subscription Updated.");
//	}
	
	/**
	 * Payment Api which will hit by Member when he will receive a mail for it.
	 * @param request
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public Message submitNewPassword(HttpServletRequest request, @RequestParam("token") String token) {
		Message message=null;
		try {
			User user = tokenService.parseUserFromToken(token);
			if (user != null) {
				logger.debug("Member Click Einvoice system url : {}",user);
				message=new Message(Message.MsgCode.Info,"User validated {} "+user.getName()+" <=>"+user.getEmailId());
			} else {
				message=new Message(Message.MsgCode.Info,"Url Invalid for User, Please contact your Nasscome Branch.");
			}
		} catch (Exception e) {
			message=new Message(Message.MsgCode.Info,"Url Invalid for Member Payment ,");
		}
		return message;
	}	
	}

