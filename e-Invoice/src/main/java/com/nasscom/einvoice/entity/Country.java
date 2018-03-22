package com.nasscom.einvoice.entity;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
//@Table(name = "country")
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "country_id")
	private Long id;
	@Column
	private String name="India";
	@Column
	private String sortname="IN";
	@Column
	private Integer phonecode=91;

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

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public Integer getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(Integer phonecode) {
		this.phonecode = phonecode;
	}

}
