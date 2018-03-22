package com.nasscom.einvoice.entity;

import java.util.Arrays;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "invoice_receipt_file")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceReceiptFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "receipt_file_id")
	private Long id;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "file_data", columnDefinition = "BLOB")
	private byte[] data;
	@Column(name = "isCancel", columnDefinition = "tinyint(1) default 1")
	private Boolean isCancel = new Boolean(false);
	@Column(name = "remarks", columnDefinition = "TEXT")
	private String remarks;
	@OneToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "invoice_id", nullable = true, updatable = true, insertable = true)
	private InvoiceDetail invoiceDetail;
	@Column(name = "status")
	private Status status;
	@Column(name = "receipt_date")
	private String receiptDate;
	@Column(name = "receipt_No", unique = true)
	private String receiptNo;
	
	public enum Status {
		GENERATED, FAILED, REGENERATED
	}
	public InvoiceReceiptFile() {
		super();
	}
	
	
	public InvoiceReceiptFile(String fileName, byte[] data, Status status,InvoiceDetail invoiceDetail) {
		super();
		this.fileName = fileName;
		this.data = data;
		this.invoiceDetail = invoiceDetail;
		this.status = status;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}


	public Boolean getIsCancel() {
		return isCancel;
	}


	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public InvoiceDetail getInvoiceDetail() {
		return invoiceDetail;
	}


	public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public String getReceiptDate() {
		return receiptDate;
	}


	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}


	public String getReceiptNo() {
		return receiptNo;
	}


	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}


	@Override
	public String toString() {
		return "InvoiceReceiptFile [id=" + id + ", fileName=" + fileName + ", data=" + Arrays.toString(data)
				+ ", isCancel=" + isCancel + ", remarks=" + remarks + ", invoiceDetail=" + invoiceDetail + ", status="
				+ status + "]";
	}
	
	
	
}
