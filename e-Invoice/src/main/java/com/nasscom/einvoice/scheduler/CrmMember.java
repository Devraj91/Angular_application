package com.nasscom.einvoice.scheduler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nasscom.einvoice.entity.Address;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrmMember {
	private Long memberId;
	private String emailId;
	private String membershipID;
	private String name;
	private String mobileNo;
	private String category;
	private String contactPerson;
	//private Double calculated_subscription;
	//private Double manualSubscription;
	private LocalDateTime membershipStart;
	private LocalDateTime membershipEnd;
	/*private String membershipPlan_2014_15;
	private String membershipPlan_2015_16;
	private String membershipPlan_2016_17;
	private String membershipPlan_2017_18;
	private String membershipPlan_2018_19;*/
	private FeeStatus feeStatus;
	private Double balanceAmt;
	private Double overDraftAmt;
	private Address address;
	private Boolean IsActive = false;

	public enum FeeStatus {
		FULL, PARTIAL, OVERDRAFT
	}

	
	public CrmMember(String emailId, String membershipID, String name, String mobileNo, String category,
			String contactPerson, LocalDateTime membershipStart, LocalDateTime membershipEnd,
			String membershipPlan_2014_15, String membershipPlan_2015_16, String membershipPlan_2016_17,
			String membershipPlan_2017_18, String membershipPlan_2018_19, Address address) {
		super();
		this.emailId = emailId;
		this.membershipID = membershipID;
		this.name = name;
		this.mobileNo = mobileNo;
		this.category = category;
		this.contactPerson = contactPerson;
		this.membershipStart = membershipStart;
		this.membershipEnd = membershipEnd;
		/*this.membershipPlan_2014_15 = membershipPlan_2014_15;
		this.membershipPlan_2015_16 = membershipPlan_2015_16;
		this.membershipPlan_2016_17 = membershipPlan_2016_17;
		this.membershipPlan_2017_18 = membershipPlan_2017_18;
		this.membershipPlan_2018_19 = membershipPlan_2018_19;*/
		this.address = address;
	}

	public CrmMember(String membershipID, String name) {
		super();
		this.membershipID = membershipID;
		this.name = name;
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

	/*public Double getCalculated_subscription() {
		return calculated_subscription;
	}

	public void setCalculated_subscription(Double calculated_subscription) {
		this.calculated_subscription = calculated_subscription;
	}

	public Double getManualSubscription() {
		return manualSubscription;
	}

	public void setManualSubscription(Double manualSubscription) {
		this.manualSubscription = manualSubscription;
	}*/

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
	

	/*public String getMembershipPlan_2014_15() {
		return membershipPlan_2014_15;
	}

	public void setMembershipPlan_2014_15(String membershipPlan_2014_15) {
		this.membershipPlan_2014_15 = membershipPlan_2014_15;
	}

	public String getMembershipPlan_2015_16() {
		return membershipPlan_2015_16;
	}

	public void setMembershipPlan_2015_16(String membershipPlan_2015_16) {
		this.membershipPlan_2015_16 = membershipPlan_2015_16;
	}

	public String getMembershipPlan_2016_17() {
		return membershipPlan_2016_17;
	}

	public void setMembershipPlan_2016_17(String membershipPlan_2016_17) {
		this.membershipPlan_2016_17 = membershipPlan_2016_17;
	}

	public String getMembershipPlan_2017_18() {
		return membershipPlan_2017_18;
	}

	public void setMembershipPlan_2017_18(String membershipPlan_2017_18) {
		this.membershipPlan_2017_18 = membershipPlan_2017_18;
	}

	public String getMembershipPlan_2018_19() {
		return membershipPlan_2018_19;
	}

	public void setMembershipPlan_2018_19(String membershipPlan_2018_19) {
		this.membershipPlan_2018_19 = membershipPlan_2018_19;
	}*/

	public FeeStatus getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(FeeStatus feeStatus) {
		this.feeStatus = feeStatus;
	}

	public Double getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(Double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public Double getOverDraftAmt() {
		return overDraftAmt;
	}

	public void setOverDraftAmt(Double overDraftAmt) {
		this.overDraftAmt = overDraftAmt;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getIsActive() {
		return IsActive;
	}

	public void setIsActive(Boolean isActive) {
		IsActive = isActive;
	}

	public CrmMember() {
		super();
	}

	
	@Override
	public String toString() {
		return "CrmMember [memberId=" + memberId + ", emailId=" + emailId + ", membershipID=" + membershipID + ", name="
				+ name + ", mobileNo=" + mobileNo + ", category=" + category + ", contactPerson=" + contactPerson
				+ ", membershipStart=" + membershipStart + ", membershipEnd=" + membershipEnd + ", feeStatus="
				+ feeStatus + ", balanceAmt=" + balanceAmt + ", overDraftAmt=" + overDraftAmt + ", address=" + address
				+ ", IsActive=" + IsActive + "]";
	}


}
