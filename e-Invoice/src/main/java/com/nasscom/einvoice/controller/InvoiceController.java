package com.nasscom.einvoice.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccavenue.security.AesCryptUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nasscom.einvoice.constants.DisplayMessages;
import com.nasscom.einvoice.constants.MemberConstants;
import com.nasscom.einvoice.domain.CancelInvoice;
import com.nasscom.einvoice.domain.InvoiceDetailRequest;
import com.nasscom.einvoice.domain.InvoiceDetailResponse;
import com.nasscom.einvoice.domain.InvoiceTransactionDetailResponse;
import com.nasscom.einvoice.domain.MemberTransactionDetailRequest;
import com.nasscom.einvoice.domain.Message;
import com.nasscom.einvoice.entity.CcRequestTxn;
import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.InvoiceDetail;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.entity.ResponseTransaction;
import com.nasscom.einvoice.service.CcRequestTxnService;
import com.nasscom.einvoice.service.InvoiceService;
import com.nasscom.einvoice.service.MemberService;
import com.nasscom.einvoice.service.ResponseTransactionService;
@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	MemberService memberService;
	@Autowired
	CcRequestTxnService ccRequestTxnService;
	@Autowired
	ResponseTransactionService responseTransactionService;
	
	@RequestMapping(value = "/get/{invoiceId}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<byte[]> generateInvoice(@PathVariable("invoiceId")String invoiceId, HttpServletResponse response) {
		InvoiceDetail invoiceDetail=null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		try {
			invoiceDetail = invoiceService.getPdf(invoiceId);
			String fileName = "\"invoice-" + invoiceDetail.getInvoiceFile().getFileName() + ".pdf\"";
			headers.setContentDispositionFormData(fileName, fileName);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
		}
			return new ResponseEntity<>(invoiceDetail.getInvoiceFile().getData(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getReceipt/{invoiceId}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<byte[]> generateReceipt(@PathVariable("invoiceId")String invoiceId, HttpServletResponse response) {
		InvoiceDetail invoiceDetail=null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		try {
			invoiceDetail = invoiceService.getInvoiceDetail(Long.parseLong(invoiceId));
			String fileName = "\"receipt-" + invoiceDetail.getInvoiceReceiptFile().getFileName() + ".pdf\"";
			headers.setContentDispositionFormData(fileName, fileName);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
		}
			return new ResponseEntity<>(invoiceDetail.getInvoiceReceiptFile().getData(), headers, HttpStatus.OK);
	}
	
	/**
	 * Api to cancel invoice.
	 * @param cancelInvoiceRequest
	 * @return Object
	 */
	@RequestMapping(value = "/cancel", consumes="application/json", method = RequestMethod.POST)
	public Object cancelInvoice(@RequestBody CancelInvoice cancelInvoiceRequest) {
		try {
			 Object in = invoiceService.cancelInvoice(cancelInvoiceRequest.getInvoiceId(),cancelInvoiceRequest.getRemarks());
			 if(null!=in)
			 return new Message(Message.MsgCode.Success,"Successfully Invoice Cancel");
		} catch (Exception e) {
			return new Message(Message.MsgCode.Error,e.getMessage());
		}
		return null;
	}
	
	/**
	 * Member TDS Update Api which will allow Member to update PaidAmmount based on TDS.
	 * @param memberTransactionRequest
	 * @return Message
	 */
	@RequestMapping(value = "/addInvoiceTransactionDetail", consumes="application/json", method = RequestMethod.POST)
	public Message addInvoiceTransactionDetail(@RequestBody MemberTransactionDetailRequest memberTransactionRequest) {
		logger.info("memberTransactionRequest in addInvoiceTransactionDetail"+memberTransactionRequest);
		if (null == memberTransactionRequest.getInvoiceId() || null == memberTransactionRequest.getBaseAmt()) {
			return new Message(Message.MsgCode.Error, DisplayMessages.baseAmountInvoiceIdRequiredMsg);
		}else{
			if (memberTransactionRequest.getMode().equals("AUTOMATIC")) {
				if(null== memberTransactionRequest.getOnlineTaxAmt()) {
					return new Message(Message.MsgCode.Error, DisplayMessages.taxAmountRequiredMssg);
				}
				
				if(memberTransactionRequest.getIsTdsDeducted()) {
					return new Message(Message.MsgCode.Error, DisplayMessages.memberTaxTdsRequiredMssg);
				}
			}
		}

		
		return (invoiceService.addInvoiceTransactionDetail(memberTransactionRequest.getInvoiceId(),
				memberTransactionRequest.getMemberId(),memberTransactionRequest.getBaseAmt(), memberTransactionRequest.getOnlineTaxAmt(),
				memberTransactionRequest.getTdsRate(),memberTransactionRequest.getMode(),
				memberTransactionRequest.getIsTdsDeducted(),memberTransactionRequest.getModeOfPayment(),memberTransactionRequest.getTransactionRemarks()).equals(MemberConstants.SUCCESS))?new Message(Message.MsgCode.Success, DisplayMessages.paymentUpdatedSuccess):new Message(Message.MsgCode.Error, DisplayMessages.paymentUpdatedError);
				
	}
	
	/**
	 * Api to retrieve InvoiceDetail data 
	 * @return Object
	 */
	
	@RequestMapping(value = {"/get/all"}, method = RequestMethod.GET)
	public List<InvoiceDetail> getAll(){
		return invoiceService.findAll();
	}
	
	@RequestMapping(value = {"/invoiceDetails"}, method = RequestMethod.GET, produces="application/json")
	public List<InvoiceDetailResponse> getInvoiceDetails() {
		List<InvoiceDetail> invList=invoiceService.findAll();
		List<InvoiceDetailResponse> detailList=new ArrayList<InvoiceDetailResponse>();
		InvoiceDetailResponse udetail;
		Message responseMessage=null;
		
		try {
			List<City> cities=invoiceService.findAllCities();
			for(InvoiceDetail detail:invList)
			{
				udetail=new InvoiceDetailResponse();
				Double paidAmt=0.0;
				Double balanceAmount=0.0;
				String[] ccList;
				paidAmt=invoiceService.getTotalPaidAmountPerInvoice(detail.getInvoiceId());
				InvoiceTransactionDetailResponse outStandingPaymentDetails=invoiceService.getOutstandingPaymentDetails(detail.getInvoiceId());
				if(outStandingPaymentDetails!=null)
					balanceAmount=outStandingPaymentDetails.getInvoiceAmt()+outStandingPaymentDetails.getTaxAmt()-outStandingPaymentDetails.getOverDraftAmt();
				//
				udetail.setInvoiceAmt(detail.getInvoiceAmt()+detail.getTaxAmt());
				udetail.setBalanceAmt(balanceAmount);
				udetail.setPaidAmt(paidAmt);
				udetail.setInvoiceId(detail.getInvoiceId());
				udetail.setPoNumber((detail.getPurchaseOrderDetail()!=null)?detail.getPurchaseOrderDetail().getPoNo():"");
				udetail.setIsTaxApplicable(detail.getTaxApplicable());
				udetail.setIsPerforma(detail.getIsPerforma());
				udetail.setGstNo(detail.getGstNo());
				udetail.setToEmail(detail.getToEmail());
				udetail.setYear(detail.getYear());
				udetail.setAddress(detail.getAddress());
				udetail.setIsCancelable((paidAmt==null)?true:(paidAmt==0.0)?true:false);
				if(paidAmt==0.0)
					udetail.setStatus("Not Paid");
				else if(paidAmt<detail.getInvoiceAmt())
					udetail.setStatus("Partially Paid");
				else
					udetail.setStatus("Fully Paid");
				if(detail.getMember()!=null) {
					Member member =detail.getMember();
					ccList = member.getCcEmails();
					if (ccList != null && !("").equals(ccList)) {
						StringBuilder builder = new StringBuilder();
						for(String s : ccList) {
							builder.append(s);
							builder.append(",");
						}
						String ccEmails = builder.toString();
						udetail.setCcEmails(ccEmails);
					}
					else {
						udetail.setCcEmails("");
					}
					udetail.setEmailId(member.getEmailId());
					udetail.setName(member.getName());
					udetail.setMembershipID(member.getMembershipID());
					udetail.setMemberId(member.getMemberId());
					
				}
				//
				
				udetail.setCities(cities);
				detailList.add(udetail);	
			}
		} catch (Exception e) {
			logger.error("Exception encountered while getting getInvoiceDetails" ,e);
		}
		
/*		if(detailList.isEmpty())
			responseMessage=new Message(Message.MsgCode.Warn, "No data available");
		else
			responseMessage=new Message(Message.MsgCode.Success,"Invoice Records returned successfully");*/
		//return (detailList.isEmpty())?new Message(Message.MsgCode.Error, "No data available"):detailList;
		return detailList;
		//return responseMessage;
	}

	/**
	 * Allows to edit PO Number and Tax detail for an invoice
	 * @param invoiceDetailRequest
	 * @return Message
	 */
	@RequestMapping(value="/editPo", method=RequestMethod.POST)
	public Message editInvoiceDetail(@RequestBody InvoiceDetailRequest invoiceDetailRequest	) {
		if (null==invoiceDetailRequest.getInvoiceId() || null==invoiceDetailRequest.getIsTaxApplicable() || null==invoiceDetailRequest.getYear()) {
			return new Message(Message.MsgCode.Error, "Please provide value for year and specify whether tax is applicable");
		}
		return (invoiceService.editInvoiceDetail(invoiceDetailRequest.getInvoiceId(), invoiceDetailRequest.getPoNumber(), invoiceDetailRequest.getYear(), invoiceDetailRequest.getIsTaxApplicable(),invoiceDetailRequest.getCityname(),invoiceDetailRequest.getFax(),invoiceDetailRequest.getPhone(),invoiceDetailRequest.getPin(),invoiceDetailRequest.getStreet(),invoiceDetailRequest.getCcEmails(),invoiceDetailRequest.getIsPerforma(),invoiceDetailRequest.getGstNo(),invoiceDetailRequest.getToEmail())!=null)?new Message(Message.MsgCode.Info,DisplayMessages.invoiceEditSuccessMsg):new Message(Message.MsgCode.Error,DisplayMessages.invoiceEditErrorMsg);
	}
	/**
	 * Check payment url expiry
	 * @param invoiceId
	 * @return
	 */
	@RequestMapping(value = "/checkUrlExpiry/{invoiceId}", consumes="application/json",produces="application/json", method = RequestMethod.GET)
	public Object checkPaymentUrlExpiry(@PathVariable(value="invoiceId") String invoiceId) {
		try {
			 boolean flag = invoiceService.checkPaymentUrlExpiry(invoiceId);
			 if(flag)
			 return new Message(Message.MsgCode.Success,flag+"");
		} catch (Exception e) {
			return new Message(Message.MsgCode.Error,e.getMessage());
		}
		return new Message(Message.MsgCode.Info,false+"");
	}
	
	
	/**
	 * Api to retrieve outstanding payment details  
	 * @return InvoiceTransactionDetailResponse
	 */
	@RequestMapping(value = {"/paymentDetails/{invoiceId}"}, method = RequestMethod.GET, produces="application/json")
	public InvoiceTransactionDetailResponse getOnlinePaymentDetails(@PathVariable(value="invoiceId") Long invoiceId) {
		
		InvoiceTransactionDetailResponse invoiceTransactionDetailResponse=null;
		invoiceTransactionDetailResponse=invoiceService.getOutstandingPaymentDetails(invoiceId);
		return invoiceTransactionDetailResponse;
	}
	
	@RequestMapping(value = "/payment/ccavanuereq", consumes="application/json",produces="application/json", method = RequestMethod.POST)
	public Object encodeCcavenueRequest(@RequestBody CcRequestTxn ccReqTxn) {
		try {
			ccReqTxn.setOrder_id(System.currentTimeMillis());
			ccReqTxn.setMerchant_id("45");
			ccReqTxn.setMember(memberService.getMemberById(ccReqTxn.getMember().getMemberId()));
			ccRequestTxnService.createCcRequestTxn(ccReqTxn);
			String workingKey = "3EAFBC4B459F771DB16D0946B93AE8A6";    //Put in the 32 Bit Working Key provided by CCAVENUES.
			String accessCode= "AVPG01EK30BW94GPWB";		//Put in the Access Code in quotes provided by CCAVENUES.
			// Enumeration<String> enumeration=request.getParameterNames();
			 String ccaRequest="", pname="", pvalue="";
			// while(enumeration.hasMoreElements()) {
			  //    pname = ""+enumeration.nextElement();
			  //    pvalue = request.getParameter(pname);
			    //  ccaRequest = ccaRequest + pname + "=" + URLEncoder.encode(pvalue,"UTF-8") + "&";
			// }
			 ccaRequest = "merchant_id=" + getEncode(ccReqTxn.getMerchant_id()) + "&";
			 ccaRequest=ccaRequest+"order_id=" + getEncode(String.valueOf(ccReqTxn.getOrder_id())) + "&";
			 ccaRequest=ccaRequest+"currency=" + getEncode(ccReqTxn.getCurrency()) + "&";
			 ccaRequest=ccaRequest+"amount=" + getEncode(String.valueOf(ccReqTxn.getAmount())) + "&";
			 ccaRequest=ccaRequest+"redirect_url=" + getEncode(ccReqTxn.getRedirect_url()) + "&";
			 ccaRequest=ccaRequest+"cancel_url=" + getEncode(ccReqTxn.getCancel_url()) + "&";
			 ccaRequest=ccaRequest+"language=" + getEncode(ccReqTxn.getLanguage()) + "&";
			 AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
			 String encRequest = aesUtil.encrypt(ccaRequest);
			 Map<String,String> map=new HashMap<String,String>();
			 map.put("action", "https://test.ccavenue.com/transaction/transaction.do");
			 map.put("access_code", accessCode.trim());
			 map.put("encRequest", encRequest.trim());
			 return new Message(Message.MsgCode.Success,"https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&access_code="+accessCode.trim()+"&encRequest="+encRequest.trim());
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(Message.MsgCode.Error,e.getMessage());
		}
	}
	
	private String getEncode(String pvalue) throws UnsupportedEncodingException {
		return URLEncoder.encode(pvalue,"UTF-8");
	}
	
	@RequestMapping(value = "/payment/ccavanueres", consumes="application/json",produces="application/json", method = RequestMethod.POST)
	public ResponseTransaction encodeCcavenueResponse(@RequestParam("encResp") String encResp) {
		try {
			String workingKey = "3EAFBC4B459F771DB16D0946B93AE8A6";		//32 Bit Alphanumeric Working Key should be entered here so that data can be decrypted.
			AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
			String decResp = aesUtil.decrypt(encResp);
			StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
			Hashtable hs=new Hashtable();
			String pair=null, pname=null, pvalue=null;
			while (tokenizer.hasMoreTokens()) {
				pair = (String)tokenizer.nextToken();
				if(pair!=null) {
					StringTokenizer strTok=new StringTokenizer(pair, "=");
					pname=""; pvalue="";
					if(strTok.hasMoreTokens()) {
						pname=(String)strTok.nextToken();
						if(strTok.hasMoreTokens())
							pvalue=(String)strTok.nextToken();
						hs.put(pname, pvalue);
					}
				}
			}
			ResponseTransaction responseTransaction = new ResponseTransaction();
			responseTransaction.setAmount((Double)hs.get("amount"));
			responseTransaction.setOrder_id((String)hs.get("order_id"));
			responseTransaction.setBank_ref_no((String) hs.get("bank_ref_no"));
			responseTransaction.setBilling_email((String) hs.get("billing_email"));
			responseTransaction.setBilling_name((String) hs.get("billing_name"));
			responseTransaction.setBillingAddress((String) hs.get("billingAddress"));
			responseTransaction.setCard_name((String) hs.get("card_name"));
			responseTransaction.setCardNo((String) hs.get("cardNo"));
			responseTransaction.setCurrency((String) hs.get("currency"));
			responseTransaction.setOrder_status((String) hs.get("order_status"));
			responseTransaction.setPayment_mode((String) hs.get("payment_mode"));
			responseTransaction.setReason((String) hs.get("reason"));
			responseTransaction.setStatus_code((String) hs.get("status_code"));
			responseTransaction.setTracking_id((String) hs.get("tracking_id"));
			responseTransaction.setMember(ccRequestTxnService.getMember(Long.parseLong(responseTransaction.getOrder_id())));
			responseTransactionService.createResponseTransaction(responseTransaction);
			return responseTransaction;
			 //return new Message(Message.MsgCode.Success,"https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&access_code="+accessCode.trim()+"&encRequest="+encRequest.trim());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseTransaction();
		}
	}

}
