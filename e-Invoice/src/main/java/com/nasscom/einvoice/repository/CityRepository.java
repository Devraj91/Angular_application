package com.nasscom.einvoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

	public City findByName(String name);	
	public City findById(Long id);
	public List<City> findAll();
	public City findByNameIgnoreCase(String name);
	
}
