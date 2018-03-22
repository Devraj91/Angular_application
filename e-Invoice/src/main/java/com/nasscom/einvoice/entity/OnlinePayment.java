package com.nasscom.einvoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "online_payment")
public class OnlinePayment extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "online_payment_id")
	private Long onlinePaymentId;
	@Column(name = "request")
	private String request;
	@Column(name = "response")
	private String response;
	@Column(name = "invoice_id")
	private Long invoiceId;	

	public OnlinePayment() {

	}

	public OnlinePayment(String request, String response, Long invoiceId) {
		super();
		this.request = request;
		this.response = response;
		this.invoiceId = invoiceId;
	}

	public Long getOnlinePaymentId() {
		return onlinePaymentId;
	}

	public void setOnlinePaymentId(Long onlinePaymentId) {
		this.onlinePaymentId = onlinePaymentId;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Override
	public String toString() {
		return "OnlinePayment [onlinePaymentId=" + onlinePaymentId + ", request=" + request + ", response=" + response
				+ ", invoiceId=" + invoiceId + "]";
	}

	
}
