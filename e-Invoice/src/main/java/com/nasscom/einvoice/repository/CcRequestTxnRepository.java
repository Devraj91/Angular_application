package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.CcRequestTxn;

@Repository
public interface CcRequestTxnRepository extends JpaRepository<CcRequestTxn, Long>{
	
	CcRequestTxn findByOrderId(Long orderId);
}
