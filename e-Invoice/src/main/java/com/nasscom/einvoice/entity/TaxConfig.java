package com.nasscom.einvoice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tax_config")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "taxconfig_id")
	private Long id;
	@Column(name = "tax_name")
	private String taxName;
	@Column(name = "rate")
	private Double rate;
	@OneToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name = "city_id",referencedColumnName = "city_id", updatable = false)
	@Fetch(value = FetchMode.JOIN)
	private City city;
	@Column(name = "tax_applicable")
	private String taxApplicable;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public TaxConfig(String taxName, Double rate) {
		super();
		this.taxName = taxName;
		this.rate = rate;
	}

	public TaxConfig() {
		super();
	}

	public String getTaxApplicable() {
		return taxApplicable;
	}

	public void setTaxApplicable(String taxApplicable) {
		this.taxApplicable = taxApplicable;
	}

	@Override
	public String toString() {
		return "TaxConfig [id=" + id + ", taxName=" + taxName + ", rate=" + rate + ", city=" + city.getName() + ", taxApplicable="
				+ taxApplicable + "]";
	}

}
