package com.nasscom.einvoice.entity;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nasscom.einvoice.pdf.CalculatedTax;

@Entity
@Table(name = "invoice_detail")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invoice_id")
	private Long invoiceId;

	@Column(name = "invoice_No", unique = true)
	private String invoiceNo;

	@Column(name = "invoice_date")
	private String invoiceDate;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "file_id")
	private InvoiceFile invoiceFile;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "receipt_file_id")
	private InvoiceReceiptFile invoiceReceiptFile;

	
	@OneToOne(fetch = FetchType.EAGER, optional = false,cascade = { CascadeType.ALL })
	@JoinColumn(name = "address_id", nullable = true, updatable = true)
	@Fetch(value = FetchMode.JOIN)
	private Address address;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "po_id", nullable = true, updatable = true, insertable = true)
	private PurchaseOrder purchaseOrderDetail;

	@Column(name = "year")
	private String year;

	@Column(name = "invoice_amt", nullable = true)
	private Double invoiceAmt;
	
	@Column(name = "tax_amount", nullable = true)
	private Double taxAmount;
	
	@Column(name = "tax_applicable")
	private Boolean taxApplicable = true;
	
	@Column(name = "isPerforma")
	private Boolean isPerforma = true;
	
	@Column(name = "gstNo")
	private String gstNo;

	@Column(name = "toEmail")
	private String toEmail;

	@Transient
	private List<CalculatedTax> taxes;
	@Transient
	private Double totalAmountWithTaxes;
	@Transient
	private String deliveryNote = "NA";
	@Transient
	private String paymentMode = "Online";
	@Transient
	private String supplierRef = "NA";
	@Transient
	private String otherRef = "NA";
	@Transient
	private String dispatchDocNo = "NA";
	@Transient
	private String deliveryNoteDate = "NA";
	@Transient
	private String dispatchThroug = "NA";
	@Transient
	private String destination = "NA";
	@Transient
	private String deliveryTerms = "NA";
	@Transient
	private String amountChargeableInwords;
	@Transient
	private String taxamountChargeableInwords;

	public InvoiceDetail() {
		super();
	}

	public InvoiceDetail(String invoiceNo, String date, Member member, List<CalculatedTax> taxes,
			Double totalAmountWithTaxes,Address address) {
		super();
		this.invoiceNo = invoiceNo;
		this.invoiceDate = date;
		this.member = member;
		this.taxes = taxes;
		this.totalAmountWithTaxes = totalAmountWithTaxes;
		this.address = address;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public InvoiceFile getInvoiceFile() {
		return invoiceFile;
	}

	public void setInvoiceFile(InvoiceFile invoiceFile) {
		this.invoiceFile = invoiceFile;
	}

//	public int getYear() {
//		return year;
//	}
//
//	public void setYear(int year) {
//		this.year = year;
//	}

	
	public InvoiceReceiptFile getInvoiceReceiptFile() {
		return invoiceReceiptFile;
	}

	public void setInvoiceReceiptFile(InvoiceReceiptFile invoiceReceiptFile) {
		this.invoiceReceiptFile = invoiceReceiptFile;
	}
	
	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}

	public Double getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(Double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public Double getTaxAmt() {
		return taxAmount;
	}

	public void setTaxAmt(Double taxAmt) {
		this.taxAmount = taxAmt;
	}

	public Boolean getTaxApplicable() {
		return taxApplicable;
	}

	public void setTaxApplicable(Boolean taxApplicable) {
		this.taxApplicable = taxApplicable;
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

	public List<CalculatedTax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<CalculatedTax> taxes) {
		this.taxes = taxes;
	}

	public Double getTotalAmountWithTaxes() {
		return totalAmountWithTaxes;
	}

	public void setTotalAmountWithTaxes(Double totalAmountWithTaxes) {
		this.totalAmountWithTaxes = totalAmountWithTaxes;
	}

	public String getDeliveryNote() {
		return deliveryNote;
	}

	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getSupplierRef() {
		return supplierRef;
	}

	public void setSupplierRef(String supplierRef) {
		this.supplierRef = supplierRef;
	}

	public String getOtherRef() {
		return otherRef;
	}

	public void setOtherRef(String otherRef) {
		this.otherRef = otherRef;
	}

	public String getDispatchDocNo() {
		return dispatchDocNo;
	}

	public void setDispatchDocNo(String dispatchDocNo) {
		this.dispatchDocNo = dispatchDocNo;
	}

	public String getDeliveryNoteDate() {
		return deliveryNoteDate;
	}

	public void setDeliveryNoteDate(String deliveryNoteDate) {
		this.deliveryNoteDate = deliveryNoteDate;
	}

	public String getDispatchThroug() {
		return dispatchThroug;
	}

	public void setDispatchThroug(String dispatchThroug) {
		this.dispatchThroug = dispatchThroug;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDeliveryTerms() {
		return deliveryTerms;
	}

	public void setDeliveryTerms(String deliveryTerms) {
		this.deliveryTerms = deliveryTerms;
	}

	public String getAmountChargeableInwords() {
		return amountChargeableInwords;
	}

	public void setAmountChargeableInwords(String amountChargeableInwords) {
		this.amountChargeableInwords = amountChargeableInwords;
	}

	public PurchaseOrder getPurchaseOrderDetail() {
		return purchaseOrderDetail;
	}

	public void setPurchaseOrderDetail(PurchaseOrder purchaseOrderDetail) {
		this.purchaseOrderDetail = purchaseOrderDetail;
	}

	public String getTaxamountChargeableInwords() {
		return taxamountChargeableInwords;
	}

	public void setTaxamountChargeableInwords(String taxamountChargeableInwords) {
		this.taxamountChargeableInwords = taxamountChargeableInwords;
	}

	@Override
	public String toString() {
		return "InvoiceDetail [invoiceId=" + invoiceId + ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate
				+ ", invoiceFile=" + invoiceFile+ ", addresss=" + address
				+ ", year=" + year + ", taxes=" + taxes + ", totalPaidAmt=" + totalAmountWithTaxes + ", deliveryNote="
				+ deliveryNote + ", paymentMode=" + paymentMode + ", supplierRef=" + supplierRef + ", otherRef="
				+ otherRef + ", dispatchDocNo=" + dispatchDocNo + ", deliveryNoteDate=" + deliveryNoteDate
				+ ", dispatchThroug=" + dispatchThroug + ", destination=" + destination + ", deliveryTerms="
				+ deliveryTerms + ", amountChargeableInwords=" + amountChargeableInwords + "]";
	}

}
