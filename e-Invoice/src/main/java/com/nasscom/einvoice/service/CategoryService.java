package com.nasscom.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.CategoryFee;
import com.nasscom.einvoice.repository.CategoryFeeRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryFeeRepository categoryFeeRepository;
	@Autowired
	MemberService memberService;
	@Autowired
	InvoiceService invoiceService;

	public CategoryFee createCategoryFee(CategoryFee categoryFee) {
		categoryFeeRepository.save(categoryFee);
		invoiceService.updateInvoiceAmountByCategory();
		return categoryFee;
	}

	public void updateCategoryFee(CategoryFee categoryFee) {
		createCategoryFee(categoryFee);
		//sync member subscription
       //memberService.updateMemberSubscription(categoryFee.getCategory(), categoryFee.getFee());
	}

	public void deleteCategoryFee(Long id) {
		categoryFeeRepository.delete(id);
		
	}

	public Page<CategoryFee> findPaginated(int page, int size) {
		return categoryFeeRepository.findAll(new PageRequest(page, size));
	}

	public List<CategoryFee> findAll() {
		return categoryFeeRepository.findAll();
	}

	public CategoryFee find(Long id) {
		return categoryFeeRepository.findOne(id);
	}

}
