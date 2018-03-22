package com.nasscom.einvoice.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "response_transaction")
public class ResponseTransaction extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "txnId")
	private Long txnId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	@Column(name = "cardno")
	private String cardNo; // in the form of 2XXX-XXXX-XXXX-X123
	@Column(name = "expirydate")
	private String expiryDate;
	@Column(name = "order_id")
	private String order_id;
	@Column(name = "tracking_id")
	private String tracking_id;
	@Column(name = "bank_ref_no")
	private String bank_ref_no;
	@Column(name = "order_status")
	private String order_status;
	@Column(name = "payment_mode")
	private String payment_mode;
	@Column(name = "card_name")
	private String card_name;
	@Column(name = "status_code")
	private String status_code;
	@Column(name = "currency")
	private String currency;
	@Column(name = "amount")
	private Double amount;

	@Column(name = "billing_name")
	private String billing_name;
	@Column(name = "billing_address")
	private String billingAddress;
	@Column(name = "billing_email")
	private String billing_email;
	
	@Column(name="res_status")
	private Status resStatus;
	@Column(name="reason")
	private String reason;

	public enum Status {
		Initiate, Processed, Fail,Success
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}

	public String getBank_ref_no() {
		return bank_ref_no;
	}

	public void setBank_ref_no(String bank_ref_no) {
		this.bank_ref_no = bank_ref_no;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBilling_name() {
		return billing_name;
	}

	public void setBilling_name(String billing_name) {
		this.billing_name = billing_name;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBilling_email() {
		return billing_email;
	}

	public void setBilling_email(String billing_email) {
		this.billing_email = billing_email;
	}

	public Status getResStatus() {
		return resStatus;
	}

	public void setResStatus(Status resStatus) {
		this.resStatus = resStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
