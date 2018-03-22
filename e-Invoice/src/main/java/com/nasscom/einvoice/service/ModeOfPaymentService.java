package com.nasscom.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.ModeOfPayment;
import com.nasscom.einvoice.repository.ModeOfPaymentRepository;

@Service
public class ModeOfPaymentService {
	
	@Autowired
	ModeOfPaymentRepository modeOfPaymentRepository;

	public ModeOfPayment createModeOfPayment(ModeOfPayment modeOfPayment) {
		return modeOfPaymentRepository.save(modeOfPayment);
	}
	
	public ModeOfPayment updateModeOfPayment(ModeOfPayment modeOfPayment) {
		return modeOfPaymentRepository.save(modeOfPayment);
	}
	
	
	public void deleteModeOfPayment(Long id) {
		modeOfPaymentRepository.delete(id);
	}


	public List<ModeOfPayment> getAllModesOfPayment() {
		return modeOfPaymentRepository.findAll();
	}

	public ModeOfPayment getModeOfPayment(Long id) {
		return modeOfPaymentRepository.findOne(id);
	}


}
