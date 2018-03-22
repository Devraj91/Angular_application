package com.nasscom.einvoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "email_template")
public class EmailTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "email_template_id")
	private Integer templateId;

	@Column(name = "subject")
	private String subject;

	@Column(name = "body", length=4000)
	private String body;
	
	@Column(name= "name")
	private String name;
	
	@Column(name= "designation")
	private String designation;
	
	@Column(name= "ps")
	private String ps;
	
	@Column(name = "templateType")
	private String templateType;
	
	
	@Column(name = "isExternal", columnDefinition="tinyint(1) default 1")
	private Boolean isExternal;
	
	
	@Transient
	private String email;
	
	@Transient
	private String from;
	
	@Transient
	private String category;
		
	@Transient
	private String regionName;
	
	@Transient
	private Boolean isChecked;
	
	@Transient
	private String signature;
	
	public enum EmailTemplates {
		Invoice, 
		Reminder,
		Recipt
		}
	/*************** Constructors ************************/
	public EmailTemplate() {

	}
	
	public EmailTemplate(Integer emailTemplateId, String subject, String body,
			String name, String designation, String ps, String templateType,
			Boolean isExternal, String email, String from, String category,
			String region, Boolean isChecked) {
		super();
		this.templateId = emailTemplateId;
		this.subject = subject;
		this.body = body;
		this.name = name;
		this.designation = designation;
		this.ps = ps;
		this.templateType = templateType;
		this.isExternal = isExternal;
		this.email = email;
		this.from = from;
		this.category = category;
		this.regionName = region;
		this.isChecked = isChecked;
	}

	/*************** Getters and Setters ************************/
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
		

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer emailTemplateId) {
		this.templateId = emailTemplateId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionId) {
		this.regionName = regionId;
	}

	
	public Boolean getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Boolean isExternal) {
		this.isExternal = isExternal;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public String toString() {
		return "EmailTemplate [templateId=" + templateId + ", subject=" + subject + ", body=" + body + ", name=" + name
				+ ", designation=" + designation + ", ps=" + ps + ", templateType=" + templateType + ", isExternal="
				+ isExternal + ", email=" + email + ", from=" + from + ", category=" + category + ", regionName="
				+ regionName + ", isChecked=" + isChecked + "]";
	}
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
