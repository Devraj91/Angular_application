package com.nasscom.einvoice.controller;

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

import com.nasscom.einvoice.entity.CategoryFee;
import com.nasscom.einvoice.exception.PageNotFoundException;
import com.nasscom.einvoice.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/categoryfee")
public class CategoryFeeController {
	@Autowired
	CategoryService categoryService;
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryFeeController.class);
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> createCategoryFee(@RequestBody CategoryFee categoryFee) {
		return new ResponseEntity<>(categoryService.createCategoryFee(categoryFee),HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updateCategoryFee(@RequestBody CategoryFee categoryFee) {
		categoryService.updateCategoryFee(categoryFee);
		logger.debug("CategoryFee Updated");
		return new ResponseEntity<>("{\"message\":\"Updated\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCategoryFee(@PathVariable("id") Long id) {
		String message="";
		try {
		categoryService.deleteCategoryFee(id);message="Deleted";
		}catch(Exception e) {
			message=e.getMessage();
		}		
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/get",params = { "page", "size" }, method = RequestMethod.GET)
	public Page<CategoryFee> getCategoryFees(@RequestParam("page") int page, @RequestParam("size") int size)
			throws PageNotFoundException {
		Page<CategoryFee> resultPage = categoryService.findPaginated(page, size);
		if (page > resultPage.getTotalPages()) {
			throw new PageNotFoundException("No-More-record-found");
		}
		return resultPage;
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public List<CategoryFee> getAllCategoryFees() throws PageNotFoundException {
		return categoryService.findAll();

	}
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public CategoryFee getCategoryFee(@PathVariable("id") Long id) throws PageNotFoundException {
		return categoryService.find(id);
	}
}
