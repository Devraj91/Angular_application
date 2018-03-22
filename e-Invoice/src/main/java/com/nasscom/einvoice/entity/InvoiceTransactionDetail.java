package com.nasscom.einvoice.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "invoicetransactiondetail")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceTransactionDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invoice_transactiondetail_id")
	private Long invoicetxnDetailId;
	@Column(name = "member_id")
	private Long memberId;	
	@JsonIgnore
	@Column(name = "is_tds_deducted")
	private Boolean isTdsDeducted = false;
	@JsonIgnore
	@Column(name = "tds_rate")
	private Double tdsRate;
	@Column(name = "tds_amount")
	private Double tdsAmount;
	@Column(name = "balance_tax")
	private Double balanceTax;
	@Column(name = "balance_invoice_amt")
	private Double balanceInvoiceAmt;
	@Column(name = "paid_amount")
	private Double paidAmount;	
	@Column(name = "overdraft_amount")
	private Double overdraftAmount;
	@Column(name = "transaction_type")
	private String transactionType;
	@Column(name = "invoice_id")
	private Long invoiceId;	
	@Column(name = "mode_of_payment")
	private String modeOfPayment;	
	@Column(name = "transaction_remarks")
	private String transactionRemarks;	
	public InvoiceTransactionDetail() {

	}

	/**
	 * @param memberId
	 * @param isTdsDeducted
	 * @param tdsRate
	 * @param tdsAmount
	 * @param balanceTax
	 * @param balanceInvoiceAmt
	 * @param paidAmount
	 * @param overdraftAmount
	 * @param transactionType
	 * @param invoiceId
	 */
	
	public InvoiceTransactionDetail(Long memberId, Boolean isTdsDeducted, Double tdsRate, Double tdsAmount,
			Double balanceTax, Double balanceInvoiceAmt, Double paidAmount, Double overdraftAmount,
			String transactionType, Long invoiceId, String modeOfPayment, String transactionRemarks ) {
		super();
		this.memberId = memberId;
		this.isTdsDeducted = isTdsDeducted;
		this.tdsRate = tdsRate;
		this.tdsAmount = tdsAmount;
		this.balanceTax = balanceTax;
		this.balanceInvoiceAmt = balanceInvoiceAmt;
		this.overdraftAmount = overdraftAmount;
		this.transactionType = transactionType;
		this.paidAmount = paidAmount;
		this.invoiceId = invoiceId;
		this.modeOfPayment = modeOfPayment;
		this.transactionRemarks = transactionRemarks;
	}

	public Long getMembertaxdetailId() {
		return invoicetxnDetailId;
	}

	public void setMembertaxdetailId(Long membertaxdetailId) {
		this.invoicetxnDetailId = membertaxdetailId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Boolean getIsTdsDeducted() {
		return isTdsDeducted;
	}

	public void setIsTdsDeducted(Boolean isTdsDeducted) {
		this.isTdsDeducted = isTdsDeducted;
	}

	public Double getTdsRate() {
		return tdsRate;
	}

	public void setTdsRate(Double tdsRate) {
		this.tdsRate = tdsRate;
	}

	public Double getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(Double tdsAmount) {
		this.tdsAmount = tdsAmount;
	}

	public Double getBalanceTax() {
		return balanceTax;
	}

	public void setBalanceTax(Double balanceTax) {
		this.balanceTax = balanceTax;
	}

	public Double getBalanceInvoiceAmt() {
		return balanceInvoiceAmt;
	}

	public void setBalanceInvoiceAmt(Double balanceInvoiceAmt) {
		this.balanceInvoiceAmt = balanceInvoiceAmt;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getOverdraftAmount() {
		return overdraftAmount;
	}

	public void setOverdraftAmount(Double overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
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
	
	
}

