package com.nasscom.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.CityRegionMapping;
import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.repository.CityRegionMappingRepository;
import com.nasscom.einvoice.repository.BranchRepository;

@Service
public class CityRegionMappingService {
	@Autowired
	CityRegionMappingRepository cityRegionMappingRepository;
	@Autowired
	BranchRepository branchRepository;
	
	public CityRegionMapping createCityRegionMapping(CityRegionMapping cityRegionMapping) {
		return cityRegionMappingRepository.save(cityRegionMapping);
	}
	
	public CityRegionMapping updateCityRegionMapping(CityRegionMapping cityRegionMapping) {
		return cityRegionMappingRepository.save(cityRegionMapping);
	}
	
	public void deleteCityRegionMapping(Long id) {
		cityRegionMappingRepository.delete(id);;
	}
	
	public List<CityRegionMapping> getAllCityRegionMapping() {
		return cityRegionMappingRepository.findAll();
	}
	
	public CityRegionMapping getCityRegionMapping(Long id) {
		return cityRegionMappingRepository.findOne(id);
	}
	
	public Page<CityRegionMapping> findPaginated(int page, int size) {
		return cityRegionMappingRepository.findAll(new PageRequest(page,size));
	}
	
	public Branch getBranchByName(String name) {
		return branchRepository.findByName(name);
	}
}
