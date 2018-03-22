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
import javax.persistence.Transient;

@Entity
@Table(name = "cc_request_txn")
public class CcRequestTxn extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	public enum Status {
		Initiate, Processed, Fail
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reqId")
	private Long reqId;
	@Column(name="status")
	private Status status;
	@Column(name="reason")
	private String reason;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;
	//
	@Column(name="order_id")
	private Long orderId;
	@Column(name="currency")
	private String currency;
	@Column(name="amount")
	private Double amount;
	@Transient
	private String merchant_id;
	@Transient
	private String redirect_url;
	@Transient
	private String cancel_url;
	@Transient
	private String language;
	@Transient
	private String token;
	public Long getReqId() {
		return reqId;
	}
	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Long getOrder_id() {
		return orderId;
	}
	public void setOrder_id(Long order_id) {
		this.orderId = order_id;
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
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getRedirect_url() {
		return redirect_url;
	}
	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}
	public String getCancel_url() {
		return cancel_url;
	}
	public void setCancel_url(String cancel_url) {
		this.cancel_url = cancel_url;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
