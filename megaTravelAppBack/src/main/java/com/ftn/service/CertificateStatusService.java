package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.CertificateModel;
import com.ftn.model.CertificateStatus;
import com.ftn.repository.CertificateRepository;
import com.ftn.repository.CertificateStatusRepository;

@Service
public class CertificateStatusService {

	@Autowired
	private CertificateStatusRepository certStatRepository;
	
	@Autowired
	private CertificateRepository certRepository;
	
	public void saveAfterCreated() {
		
	}
	public boolean revokeCert(Integer serialNumber,String message){
		
		CertificateStatus novi = new CertificateStatus();
		novi.setMessage(message);
		novi.setSerijskiBroj(serialNumber);
		novi.setStatus(true);
		certStatRepository.save(novi);
		
		CertificateModel certificat = certRepository.findOneBySerialNumber(serialNumber);
		certificat.setRevoked(true);
		certRepository.save(certificat);
		return true;
	}
}
