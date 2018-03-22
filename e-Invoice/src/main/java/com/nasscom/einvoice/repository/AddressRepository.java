package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
