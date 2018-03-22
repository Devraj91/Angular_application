package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.SenderDetail;

@Repository
public interface SenderDetailRepository extends JpaRepository<SenderDetail, Long>{

	public SenderDetail findByName(String name);
}
