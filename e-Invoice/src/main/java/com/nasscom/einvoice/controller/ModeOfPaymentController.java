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

import com.nasscom.einvoice.entity.ModeOfPayment;
import com.nasscom.einvoice.service.ModeOfPaymentService;

@RestController
@RequestMapping("/modeofpayment")
public class ModeOfPaymentController {

	@Autowired
	ModeOfPaymentService modeOfPaymentService;
	
	private static final Logger logger = LoggerFactory.getLogger(ModeOfPaymentController.class);
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createModeOfPayment(@RequestBody ModeOfPayment modeOfPayment) {
		modeOfPaymentService.createModeOfPayment(modeOfPayment);
		logger.debug("Mode Of Payment Created");
		return new ResponseEntity<>("{\"message\":\"Updated\"}",HttpStatus.OK);
	}
    
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updateModeOfPayment(@RequestBody ModeOfPayment modeOfPayment) {
		modeOfPaymentService.updateModeOfPayment(modeOfPayment);
		logger.debug("Mode Of Payment Updated");
		return new ResponseEntity<>("{\"message\":\"Updated\"}",HttpStatus.OK);
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteModeOfPayment(@PathVariable("id") Long id) {
		String message="";
		try {
			modeOfPaymentService.deleteModeOfPayment(id);message="Deleted";
			logger.debug("Mode Of Payment Deleted");

		}catch(Exception e) {
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<ModeOfPayment> getAllModesOfPayment(){
		return modeOfPaymentService.getAllModesOfPayment();

	}

	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ModeOfPayment getModeOfPayment(@PathVariable("id") Long id) {
		return modeOfPaymentService.getModeOfPayment(id);

	}
		

}
