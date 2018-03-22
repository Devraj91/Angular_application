package com.nasscom.einvoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "po_id")
	private Long id;
	@Column(name = "poNo")
	private String poNo="";	
	@Column(name = "year")
	private int year;
	public PurchaseOrder()
	{
		
	}	
	
	public PurchaseOrder(String poNo,Integer year)
	{
		this.poNo=poNo;
		this.year=year;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	}
