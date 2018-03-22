package com.nasscom.einvoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "email_detail")
public class EmailDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "emailSeq_No")
	private Long emailSeqNo;
	@Column(name = "mail_to")
	private String to;
	@Column(name = "subject")
	private String subject;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id")
	private InvoiceFile invoiceFile;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receipt_file_id")
	private InvoiceReceiptFile receiptFile;
	@Column(name = "status")
	private Status status;
	@Column(name = "reason", columnDefinition = "TEXT")
	private String reason;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	Member member;

	public enum Status {
		COMPLAINS, // report mail as spam
		HARDBOUNCE, // No mail server found
		SOFTBOUNCE, // Mail server found, but account not found
		SUCCESS, // successfully delivered
		ERROR
	}

	public EmailDetail() {
		super();
	}

	public EmailDetail(String to, String subject, InvoiceFile invoiceFile, InvoiceReceiptFile receiptFile,Status status, String reason,
			Member member) {
		super();
		this.to = to;
		this.subject = subject;
		this.invoiceFile = invoiceFile;
		this.receiptFile = receiptFile;
		this.status = status;
		this.reason = reason;
		this.member = member;
	}

	public Long getEmailSeqNo() {
		return emailSeqNo;
	}

	public void setEmailSeqNo(Long emailSeqNo) {
		this.emailSeqNo = emailSeqNo;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public InvoiceFile getInvoiceFile() {
		return invoiceFile;
	}

	public void setInvoiceFile(InvoiceFile invoiceFile) {
		this.invoiceFile = invoiceFile;
	}

	public InvoiceReceiptFile getReceiptFile() {
		return receiptFile;
	}

	public void setReceiptFile(InvoiceReceiptFile receiptFile) {
		this.receiptFile = receiptFile;
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

}
