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
@Table(name = "address")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "address_id")
	private Long id;
	@Column(name = "street")
	private String street;
    @OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", nullable = true, updatable = true)
	private City city;
	@Column(name = "pin")
	private String pin;
	@Column(name = "phone")
	private String phone;
	@Column(name = "fax")
	private String fax;

	public enum Type {
		MEMBER, INVOICE
	}

	public Address(String street, City city, String pin, String phone, String fax, Type type) {
		this.street = street;
		this.city = city;
		this.pin = pin;
		this.phone = phone;
		this.fax = fax;
		this.type = type;
	}

	@Column(name = "type")
	private Type type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Address(City city) {
		super();
		this.city = city;
	}
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Address() {
		super();
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", pin=" + pin + ", phone=" + phone
				+ ", fax=" + fax + ", type=" + type + "]";
	}

}