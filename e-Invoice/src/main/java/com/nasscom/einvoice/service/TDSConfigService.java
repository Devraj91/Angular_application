package com.nasscom.einvoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.TDSConfig;
import com.nasscom.einvoice.repository.TDSConfigRepository;

@Service
public class TDSConfigService {
	
	@Autowired
	TDSConfigRepository tdsRepository;

	public TDSConfig createTDS(TDSConfig TdsConfig) {
		return tdsRepository.save(TdsConfig);
	}
	
	public TDSConfig updateTDS(TDSConfig TdsConfig) {
		return tdsRepository.save(TdsConfig);
	}
	
	
	public void deleteTDS(Long id) {
		tdsRepository.delete(id);
	}


	public List<TDSConfig> getAllTDSConfig() {
		return tdsRepository.findAll();
	}

	public TDSConfig getTDSConfig(Long id) {
		return tdsRepository.findOne(id);
	}


}
