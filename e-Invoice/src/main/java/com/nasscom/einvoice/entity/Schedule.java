package com.nasscom.einvoice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schedule")
public class Schedule {
	public enum Type {
		Invoice,Member, Reminder
	}

	public enum RecurringType {
		Daily, Weekly, Monthly
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "schedule_id")
	private Long id;
	@Column(name = "type",unique=true)
	private Type type;
	@Column(name = "schedule_date",unique=true)
	private LocalDateTime date;
	@Column(name = "rate")
	private Integer rate;
	@Column(name = "isOn")
	private boolean isOn;
	@Column(name = "isRecurring")
	private boolean isRecurring;
	@Column(name = "recurringType")
	private RecurringType recurringType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public RecurringType getRecurringType() {
		return recurringType;
	}

	public void setRecurringType(RecurringType recurringType) {
		this.recurringType = recurringType;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public boolean getIsOn() {
		return isOn;
	}

	public void setIsOn(boolean isOn) {
		this.isOn = isOn;
	}

	public boolean getIsRecurring() {
		return isRecurring;
	}

	public void setIsRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", type=" + type + ", recurringType=" + recurringType + ", date=" + date
				+ ", rate=" + rate + ", isOn=" + isOn + ", isRecurring=" + isRecurring + "]";
	}

}
