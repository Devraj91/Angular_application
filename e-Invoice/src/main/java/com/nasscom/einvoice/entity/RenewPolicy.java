package com.nasscom.einvoice.entity;

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

import org.joda.time.DateTime;
@Entity
@Table(name = "policy")
public class RenewPolicy extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "policy_id")
	private Long id;
	@Column(name = "policy_name")
	private String policyName;
	@Column(name = "start_date")
	private DateTime startDate;
	@Column(name = "end_date")
	private DateTime endDate;
	@Column(name = "rate")
	private Double rate;
	@OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id", nullable = false, updatable = false)
	private City city;

}
