package com.nasscom.einvoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.CcRequestTxn;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.repository.CcRequestTxnRepository;

@Service
public class CcRequestTxnService {
	@Autowired
	CcRequestTxnRepository ccRequestTxnRepository;
	
	public CcRequestTxn createCcRequestTxn(CcRequestTxn ccRequestTxn) {
		return ccRequestTxnRepository.save(ccRequestTxn);
	}
	
	public Member getMember(Long orderId) {
		return ccRequestTxnRepository.findByOrderId(orderId).getMember();
	}

}
