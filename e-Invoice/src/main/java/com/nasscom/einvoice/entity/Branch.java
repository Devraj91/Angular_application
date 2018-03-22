package com.nasscom.einvoice.entity;


import java.io.Serializable;

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

@Entity
@Table(name = "branch")
public class Branch implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "branch_id")
	private Long id;
	@Column
	private String name;
	@Column
	private String gstNo;
	@Column
	private String pan;
	@Column
	private String street;
	@Column
	private Integer pin;
	@Column
	private String phone;
	@Column
	private String fax;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id",nullable = true,updatable = true)
	private City city;
	
	@OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.MERGE)
	@JoinColumn(name = "sender_id", nullable = false, updatable = true)
	private SenderDetail senderDetail;
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}


	public SenderDetail getSenderDetail() {
		return senderDetail;
	}

	public void setSenderDetail(SenderDetail senderDetail) {
		this.senderDetail = senderDetail;
	}

	@Override
	public String toString() {
		return "Branch [id=" + id + ", name=" + name + ", gstNo=" + gstNo + ", pan=" + pan
				+ ", address=" + street + " " + city + " " + pin + ", senderDetail=" + senderDetail + "]";
	}

}
