package com.nasscom.einvoice.domain;
import java.io.Serializable;
import java.util.List;

import com.nasscom.einvoice.entity.Address;
import com.nasscom.einvoice.entity.City;

public class InvoiceDetailResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String emailId;
	private String name;
	private String membershipID;
	private Double invoiceAmt=0.0;
	private Double balanceAmt=0.0;
	private Long invoiceId;
	private Double paidAmt=0.0;
	private String poNumber;	
	private Boolean isTaxApplicable;
	private Boolean isPerforma;
	private String status;
	private Long memberId;	
	private Address address;
	private String year;
	private String gstNo;
	private String toEmail;
	private String ccEmails;
	private List<City> cities;
	private Boolean isCancelable=true;
	

	public Boolean getIsCancelable() {
		return isCancelable;
	}
	public void setIsCancelable(Boolean isCancelable) {
		this.isCancelable = isCancelable;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMembershipID() {
		return membershipID;
	}
	public void setMembershipID(String membershipID) {
		this.membershipID = membershipID;
	}
	public Double getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(Double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public Double getBalanceAmt() {
		return balanceAmt;
	}
	public void setBalanceAmt(Double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Double getPaidAmt() {
		return paidAmt;
	}
	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}
	public Boolean getIsTaxApplicable() {
		return isTaxApplicable;
	}
	public void setIsTaxApplicable(Boolean isTaxApplicable) {
		this.isTaxApplicable = isTaxApplicable;
	}
	public Boolean getIsPerforma() {
		return isPerforma;
	}
	public void setIsPerforma(Boolean isPerforma) {
		this.isPerforma = isPerforma;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCcEmails() {
		return ccEmails;
	}
	public void setCcEmails(String ccEmails) {
		this.ccEmails = ccEmails;
	}
	
	
}