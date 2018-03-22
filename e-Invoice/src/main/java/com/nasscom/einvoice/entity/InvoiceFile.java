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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "invoice_file")
public class InvoiceFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_id")
	private Long id;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "file_data", columnDefinition = "BLOB")
	private byte[] data;
	@Column(name = "isCancel", columnDefinition = "tinyint(1) default 1")
	private Boolean isCancel = new Boolean(false);
	@Column(name = "remarks", columnDefinition = "TEXT")
	private String remarks;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = true, updatable = true, insertable = true)
	@JsonBackReference
	private InvoiceDetail invoiceDetail;
	@Column(name = "status")
	private Status status;
	public enum Status {
		GENERATED, FAILED, REGENERATED
	}
	public InvoiceFile() {
		super();
	}
	public InvoiceFile(String fileName, byte[] data,Status status,InvoiceDetail invoiceDetail) {
		super();
		this.fileName = fileName;
		this.data = data;
		this.status=status;
		this.invoiceDetail=invoiceDetail;
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

	@Override
	public String toString() {
		return "InvoiceFile [id=" + id + ", fileName=" + fileName + ", data=" + Arrays.toString(data) + ", isCancel="
				+ isCancel + ", remarks=" + remarks + "]";
	}

}
