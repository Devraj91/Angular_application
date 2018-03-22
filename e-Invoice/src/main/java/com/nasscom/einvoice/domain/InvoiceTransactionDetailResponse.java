package com.nasscom.einvoice.domain;

import java.io.Serializable;

public class InvoiceTransactionDetailResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long memberId;
	private Long invoiceId;
	private String memberName;
	private Double invoiceAmt=0.0;
	private Double taxAmt=0.0;
	private Double overDraftAmt=0.0;
	private Double paidAmt=0.0;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Double getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(Double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public Double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Double getOverDraftAmt() {
		return overDraftAmt;
	}

	public void setOverDraftAmt(Double overDraftAmt) {
		this.overDraftAmt = overDraftAmt;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}
	

}