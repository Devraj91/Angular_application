package com.nasscom.einvoice.entity;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "member")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "membership_id",unique = true)
	private String membershipID;

	@Column(name = "name")
	private String name;

	@Column(name = "mobile_No")
	private String mobileNo;

	@Column(name = "category")
	private String category;

	@Column(name = "contact_Person")
	private String contactPerson;

//	@Column(name = "calculated_subscription")
//	private Double calculatedSubscription;
//
//	@Column(name = "manual_subscription")
//	private Double manualSubscription;

	@Column(name = "membership_Start")
	private LocalDateTime membershipStart;

	@Column(name = "membership_End")
	private LocalDateTime membershipEnd;
	
	@Column(name = "cc_emails")
	private String[] ccEmails;

	@Column(name = "fee_status")
	private FeeStatus feeStatus;

	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.ALL })
	@JoinColumn(name = "address_id", nullable = true, updatable = true)
	@Fetch(value = FetchMode.JOIN)
	private Address address;

	@Column(name = "isactive")
	private Boolean isActive = false;
	
	@Column(name = "gstin")
	private String gstin ;
	@Transient
	private Double paidAmt;
	@Transient
	private Double invoiceAmt;
	@Transient
	private Double balanceAmt;
	
	public enum FeeStatus {
		FULL, PARTIAL, OVERDRAFT
	}
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMembershipID() {
		return membershipID;
	}

	public void setMembershipID(String membershipID) {
		this.membershipID = membershipID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

//	public Double getCalculatedSubscription() {
//		return calculatedSubscription;
//	}
//
//	public void setCalculatedSubscription(Double calculatedSubscription) {
//		this.calculatedSubscription = calculatedSubscription;
//	}

//	public Double getManualSubscription() {
//		return manualSubscription;
//	}
//
//	public void setManualSubscription(Double manualSubscription) {
//		this.manualSubscription = manualSubscription;
//	}

	public LocalDateTime getMembershipStart() {
		return membershipStart;
	}

	public void setMembershipStart(LocalDateTime membershipStart) {
		this.membershipStart = membershipStart;
	}

	public LocalDateTime getMembershipEnd() {
		return membershipEnd;
	}

	public void setMembershipEnd(LocalDateTime membershipEnd) {
		this.membershipEnd = membershipEnd;
	}

	public String[] getCcEmails() {
		return ccEmails;
	}

	public void setCcEmails(String[] ccEmails) {
		this.ccEmails = ccEmails;
	}

	public FeeStatus getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(FeeStatus feeStatus) {
		this.feeStatus = feeStatus;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public Member(String emailId, String membershipID, String name, String mobileNo, String category,
			String contactPerson, LocalDateTime membershipStart, LocalDateTime membershipEnd, Address address) {
		super();
		this.emailId = emailId;
		this.membershipID = membershipID;
		this.name = name;
		this.mobileNo = mobileNo;
		this.category = category;
		this.contactPerson = contactPerson;
		this.membershipStart = membershipStart;
		this.membershipEnd = membershipEnd;
		this.address = address;
	}

	public Member() {
		super();
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", emailId=" + emailId + ", membershipID=" + membershipID + ", name="
				+ name + ", mobileNo=" + mobileNo + ", category=" + category + ", contactPerson=" + contactPerson
				+ ", membershipStart=" + membershipStart + ", membershipEnd=" + membershipEnd + ", feeStatus="
				+ feeStatus + ", address=" + address + ", isActive=" + isActive + "]";
	}
	
	
	public Double getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(Double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public Double getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(Double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	
}
