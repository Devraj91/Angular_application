package com.nasscom.einvoice.pdf;

public class CalculatedTax {

	private String taxName;
	private Double rate;
	private double taxCharge;

	public CalculatedTax(String taxName, Double rate,double taxCharge) {
		super();
		this.taxName = taxName;
		this.rate = rate;
		this.taxCharge=taxCharge;
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

	public double getTaxCharge() {
		return taxCharge;
	}

	public void setTaxCharge(double taxCharge) {
		this.taxCharge = taxCharge;
	}

}
