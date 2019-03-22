package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.repository.CertificateStatusRepository;

@Service
public class CertificateStatusService {

	@Autowired
	private CertificateStatusRepository certStatRepository;
	
	public void saveAfterCreated() {
		
	}
}
