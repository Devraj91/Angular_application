package com.nasscom.einvoice.domain;

import com.nasscom.einvoice.entity.Address;

public class InvoiceDetailRequest {

	private Long invoiceId;
	private Boolean isTaxApplicable;
	private Integer year;
	private String poNumber;	
	private Address address;
	private String cityname;
    private String street;
    private String pin;
    private String fax;
    private String phone;
    private String ccEmails;
    private Boolean isPerforma;
    private String gstNo;
    private String toEmail;
    
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Boolean getIsTaxApplicable() {
		return isTaxApplicable;
	}
	public void setIsTaxApplicable(Boolean isTaxApplicable) {
		this.isTaxApplicable = isTaxApplicable;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getCcEmails() {
		return ccEmails;
	}
	public void setCcEmails(String ccEmails) {
		this.ccEmails = ccEmails;
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
	
}
