package com.nasscom.einvoice.entity;

public class CRMMember
{
	 String fieldName;
	 String fieldValue;
	 
	public CRMMember()
	{
		
	}
	public CRMMember(String fieldValue, String formFieldName) {
		//super();
		this.fieldValue = fieldValue;
		this.fieldName = formFieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}
	
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	@Override
	public String toString() {
		return "CRMMember [fieldName=" + fieldName + ", fieldValue=" + fieldValue + "]";
	}
	}