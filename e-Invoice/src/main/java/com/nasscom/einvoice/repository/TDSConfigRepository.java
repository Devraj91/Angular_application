package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.TDSConfig;

@Repository
public interface TDSConfigRepository extends JpaRepository<TDSConfig, Long>{

}
