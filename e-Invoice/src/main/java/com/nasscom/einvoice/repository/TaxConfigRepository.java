package com.nasscom.einvoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.TaxConfig;
import com.nasscom.einvoice.entity.User;

@RepositoryRestResource
public interface TaxConfigRepository extends JpaRepository<TaxConfig, Long>, JpaSpecificationExecutor<User>{

	List<TaxConfig> findByCity(City city);

	TaxConfig findByTaxName(String taxName);
	
	
}
