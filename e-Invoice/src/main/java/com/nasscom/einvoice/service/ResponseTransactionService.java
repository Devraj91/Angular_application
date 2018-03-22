package com.nasscom.einvoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.ResponseTransaction;
import com.nasscom.einvoice.repository.ResponseTransactionRepository;

@Service
public class ResponseTransactionService {
	@Autowired
	ResponseTransactionRepository responseTransactionRepository;
	
	public ResponseTransaction createResponseTransaction(ResponseTransaction responseTransaction) {
		return responseTransactionRepository.save(responseTransaction);
	}

}
