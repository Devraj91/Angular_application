package com.nasscom.einvoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.EmailTemplate;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long>{

	public City findByName(String name);
	List<EmailTemplate> findAll();
	EmailTemplate findByTemplateId(Integer templateId);
	EmailTemplate findByTemplateType(String template);

}
