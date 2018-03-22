package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.EmailDetail;

@Repository
public interface EmailDetailRepository extends JpaRepository<EmailDetail, Long>{

}
