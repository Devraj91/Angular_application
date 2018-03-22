package com.nasscom.einvoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sender_detail")
public class SenderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "sender_id")
	private Long senderId;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "designation")
	private String designation;

	public SenderDetail() {

	}

	public SenderDetail(Long senderId, String name, String email, String designation) {
		super();
		this.senderId = senderId;
		this.name = name;
		this.email = email;
		this.designation = designation;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return "SenderDetail [senderId=" + senderId + ", name=" + name + ", email=" + email + ", designation="
				+ designation + "]";
	}

}
