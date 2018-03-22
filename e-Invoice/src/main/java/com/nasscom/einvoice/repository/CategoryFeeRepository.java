package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.CategoryFee;

@Repository
public interface CategoryFeeRepository extends JpaRepository<CategoryFee, Long>{

	CategoryFee findByCategory(String category);

}
