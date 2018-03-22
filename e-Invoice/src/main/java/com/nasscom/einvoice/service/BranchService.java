package com.nasscom.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.repository.BranchRepository;
import com.nasscom.einvoice.repository.CityRepository;

@Service
public class BranchService {
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	CityRepository cityRepository;
	
	public Branch createBranch(Branch branch) {
		return branchRepository.save(branch);
	}
	
	public Branch updateBranch(Branch branch) {
		
		return branchRepository.save(branch);
	}
	
	public void deleteBranch(Long id) {
		branchRepository.delete(id);
	}
	
	public List<Branch> getAllBranches() {
		return branchRepository.findAll();
	}
	
	public Branch getBranch(Long id) {
		return branchRepository.findOne(id);
	}
	
	public City getByCityName(String name) {
		return cityRepository.findByName(name);
	}
	
	public Branch getByBranchName(String name) {
		return branchRepository.findByName(name);
	}
}
