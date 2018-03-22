package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.ModeOfPayment;


@Repository
public interface ModeOfPaymentRepository extends JpaRepository<ModeOfPayment, Long>{

	public ModeOfPayment findByMethod(String method);
}
