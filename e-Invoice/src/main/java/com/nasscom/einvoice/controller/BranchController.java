package com.nasscom.einvoice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.service.BranchService;

@RestController
@RequestMapping("/branch")
public class BranchController {
	@Autowired
	BranchService branchService;
	
	private static final Logger logger = LoggerFactory.getLogger(BranchController.class);
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createBranch(@RequestBody Branch branch) {
		String message="";
		try {
			if(branchService.getByCityName(branch.getCity().getName()) != null) {
				branch.setCity(branchService.getByCityName(branch.getCity().getName()));
			} else {
				throw new Exception("Given city is not valid");
			}
			branchService.createBranch(branch);
			message="Created";
			logger.debug("Branch Created");
		}catch(Exception e) {
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updateBranch(@RequestBody Branch branch) {
		String message="";
		try {
			if(branchService.getByCityName(branch.getCity().getName()) != null) {
				branch.setCity(branchService.getByCityName(branch.getCity().getName()));
			} else {
				throw new Exception("Given city is not valid");
			}
			branchService.updateBranch(branch);
			message="Updated";
			logger.debug("Branch Updated");
		}catch(Exception e) {
			message=e.getMessage();
		}	
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBranch(@PathVariable("id") Long id) {
		String message="";
		try {
			branchService.deleteBranch(id);
			message="Deleted";
		}catch(Exception e) {
			e.printStackTrace();
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<Branch> getAllBranches() {
		return branchService.getAllBranches();

	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public Branch getBranch(@PathVariable("id") Long id) {
		return branchService.getBranch(id);
	}
	
	
}
