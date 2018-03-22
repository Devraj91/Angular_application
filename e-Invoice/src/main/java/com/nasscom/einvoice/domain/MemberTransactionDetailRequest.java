package com.nasscom.einvoice.domain;


public class MemberTransactionDetailRequest {
	Long invoiceId;
	Long memberId;	
	Double baseAmt;		
	Boolean isTdsDeducted;
	Double tdsRate;
	Double onlineTaxAmt;
	Integer year;
	String mode;
	String modeOfPayment;
	String transactionRemarks;

	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}	
	public Boolean getIsTdsDeducted() {
		return isTdsDeducted;
	}
	public void setIsTdsDeducted(Boolean isTdsDeducted) {
		this.isTdsDeducted = isTdsDeducted;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Double getTdsRate() {
		return tdsRate;
	}
	public void setTdsRate(Double tdsRate) {
		this.tdsRate = tdsRate;
	}
	public Double getOnlineTaxAmt() {
		return onlineTaxAmt;
	}
	public void setOnlineTaxAmt(Double onlineTaxAmt) {
		this.onlineTaxAmt = onlineTaxAmt;
	}
	public Double getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(Double baseAmt) {
		this.baseAmt = baseAmt;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	public String getTransactionRemarks() {
		return transactionRemarks;
	}
	public void setTransactionRemarks(String transactionRemarks) {
		this.transactionRemarks = transactionRemarks;
	}
	@Override
	public String toString() {
		return "MemberTransactionDetailRequest [invoiceId=" + invoiceId + ", memberId=" + memberId + ", baseAmt="
				+ baseAmt + ", isTdsDeducted=" + isTdsDeducted + ", tdsRate=" + tdsRate + ", onlineTaxAmt="
				+ onlineTaxAmt + ", year=" + year + ", mode=" + mode + ", modeOfPayment=" + modeOfPayment
				+ ", transactionRemarks=" + transactionRemarks + "]";
	}
	
	
	
}
