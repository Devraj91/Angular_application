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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "city_region_mapping")
public class CityRegionMapping implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "city_region_mapping_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER,cascade =  {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "city_id", nullable = true, updatable = true)
	private City city;
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "Branch_id", nullable = true, updatable = true)
	private Branch branch;

	
	
	public CityRegionMapping() {
		super();
		this.city = null;
		this.branch = null;
	}
	
	public CityRegionMapping(City city, Branch branch) {
		super();
		this.city = city;
		this.branch = branch;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	

}
