package com.nasscom.einvoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.nasscom.einvoice.entity.CityRegionMapping;

@Repository
public interface CityRegionMappingRepository extends JpaRepository<CityRegionMapping, Long>{
	
	public CityRegionMapping findByCityName(String Name);
	public CityRegionMapping findByCityId(Long Id);
	public List<CityRegionMapping> findByBranchId(Long Id);

}
