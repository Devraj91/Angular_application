package com.nasscom.einvoice.artifacts;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "taxConfigArtifacts")
public class TaxConfigArtifact {
	private String taxName = "taxName";
	private String rate = "rate";
	private City city;

	@XmlElement(name = "taxName")
	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	@XmlElement(name = "rate")
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@XmlElement(name = "city")
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public static class City {
		private String name = "New Delhi";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
