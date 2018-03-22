package com.nasscom.einvoice.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.nasscom.einvoice.constants.DisplayMessages;
import com.nasscom.einvoice.entity.TaxConfig;
import com.nasscom.einvoice.exception.PageNotFoundException;
import com.nasscom.einvoice.service.TaxConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/tax")
public class TaxConfigController {
	@Autowired
	TaxConfigService taxConfigService;
	private static final Logger logger = LoggerFactory.getLogger(TaxConfigController.class);
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TaxConfig> createTax(@RequestBody TaxConfig config) {
		return new ResponseEntity<>(taxConfigService.createTax(config),HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updateTax(@RequestBody TaxConfig config) {
		taxConfigService.updateTax(config);
		return new ResponseEntity<>("{\"message\":\"Updated\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTax(@PathVariable("id") Long id) {
		String message="";
		try {
		taxConfigService.deleteTax(id);message="Deleted";
		}catch(Exception e) {
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/get",params = { "page", "size" }, method = RequestMethod.GET)
	public Page<TaxConfig> getTaxes(@RequestParam("page") int page, @RequestParam("size") int size)
			throws PageNotFoundException {
		Page<TaxConfig> resultPage = taxConfigService.findPaginated(page, size);
		if (page > resultPage.getTotalPages()) {
			throw new PageNotFoundException("No-More-record-found");
		}
		return resultPage;
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<TaxConfig> getAllTaxes() throws PageNotFoundException {
		return taxConfigService.findAll();

	}
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public TaxConfig getTaxeConfig(@PathVariable("id") Long id) throws PageNotFoundException {
		return taxConfigService.find(id);

	}
	
	@RequestMapping(value = "/taxconfigfileupload", method = RequestMethod.POST, produces = "text/plain", consumes = "multipart/form-data")
    public String taxConfigFileUpload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty() || (file.getOriginalFilename() == null) || !(file.getOriginalFilename().endsWith(".xlsx") || (file.getOriginalFilename().endsWith(".xls")))) {
           return DisplayMessages.INVALID_FILE;
        	}
		try {           
            String result=taxConfigService.createFileUploadTaxConfigInDB(file);
            return (result.equals("success")?DisplayMessages.OKSTATUS:DisplayMessages.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
        	logger.debug(e.getMessage());
            return DisplayMessages.INTERNAL_SERVER_ERROR;
        }		
       }
}
