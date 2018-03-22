package com.nasscom.einvoice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import com.nasscom.einvoice.artifacts.TaxConfigArtifact;
@Configuration
public class XmlConfiguration {

	@Bean
	public Unmarshaller unmarshaller() {
		Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
		unMarshaller.setClassesToBeBound(new Class[] {
				TaxConfigArtifact.class
		});
		return unMarshaller;
	}
}
