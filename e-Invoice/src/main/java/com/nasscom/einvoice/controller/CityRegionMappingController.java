package com.nasscom.einvoice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.nasscom.einvoice.entity.CityRegionMapping;
import com.nasscom.einvoice.exception.PageNotFoundException;
import com.nasscom.einvoice.service.CityRegionMappingService;

@RestController
@RequestMapping("/cityregionmapping")
public class CityRegionMappingController {
	@Autowired
	CityRegionMappingService cityRegionMappingService;
	
	private static final Logger logger = LoggerFactory.getLogger(CityRegionMappingController.class);
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createCityRegionMapping(@RequestBody CityRegionMapping cityRegionMapping) {
		String message="";
		try {
			if(cityRegionMappingService.getBranchByName(cityRegionMapping.getBranch().getName()) != null) {
				cityRegionMapping.setBranch(cityRegionMappingService.getBranchByName(cityRegionMapping.getBranch().getName()));
			} else {
				throw new Exception("Given Branch is not valid");
			}
			cityRegionMappingService.createCityRegionMapping(cityRegionMapping);
			message="Created";
			logger.debug("City Created");
		}catch(Exception e) {
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updateCityRegionMapping(@RequestBody CityRegionMapping cityRegionMapping) {
		String message="";
		try {
			if(cityRegionMappingService.getBranchByName(cityRegionMapping.getBranch().getName()) != null) {
				cityRegionMapping.setBranch(cityRegionMappingService.getBranchByName(cityRegionMapping.getBranch().getName()));
			} else {
				throw new Exception("Given Branch is not valid");
			}
			cityRegionMappingService.updateCityRegionMapping(cityRegionMapping);
			message="Updated";
			logger.debug("City Updated");
		}catch(Exception e) {
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCityRegionMappingService(@PathVariable("id") Long id) {
		String message="";
		try {
			cityRegionMappingService.deleteCityRegionMapping(id);
			message="Deleted";
		}catch(Exception e) {
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get",params = { "page", "size" }, method = RequestMethod.GET)
	public Page<CityRegionMapping> getCityRegionMappingService(@RequestParam("page") int page, @RequestParam("size") int size)
			throws PageNotFoundException {
		Page<CityRegionMapping> resultPage = cityRegionMappingService.findPaginated(page, size);
		if (page > resultPage.getTotalPages()) {
			throw new PageNotFoundException("No-More-record-found");
		}
		return resultPage;
	}
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<CityRegionMapping> getAllCityRegionMappingService() {
		return cityRegionMappingService.getAllCityRegionMapping();

	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public CityRegionMapping getCityRegionMappingService(@PathVariable("id") Long id) {
		return cityRegionMappingService.getCityRegionMapping(id);
	}
}
