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

import com.nasscom.einvoice.entity.TDSConfig;
import com.nasscom.einvoice.service.TDSConfigService;

@RestController
@RequestMapping("/tds")
public class TDSConfigController {

	@Autowired
	TDSConfigService tdsConfigService;
	
	private static final Logger logger = LoggerFactory.getLogger(TDSConfigController.class);
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createTDS(@RequestBody TDSConfig tdsConfig) {
		tdsConfigService.createTDS(tdsConfig);
		logger.debug("TDS Config Created");
		return new ResponseEntity<>("{\"message\":\"Updated\"}",HttpStatus.OK);
	}
    
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updateTDS(@RequestBody TDSConfig tdsconfig) {
		tdsConfigService.updateTDS(tdsconfig);
		logger.debug("TDS Config Updated");
		return new ResponseEntity<>("{\"message\":\"Updated\"}",HttpStatus.OK);
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTDS(@PathVariable("id") Long id) {
		String message="";
		try {
			tdsConfigService.deleteTDS(id);message="Deleted";
			logger.debug("TDS Config Deleted");

		}catch(Exception e) {
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<TDSConfig> getAllTDS(){
		return tdsConfigService.getAllTDSConfig();

	}

	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public TDSConfig getTDSConfig(@PathVariable("id") Long id) {
		return tdsConfigService.getTDSConfig(id);

	}
		

}
