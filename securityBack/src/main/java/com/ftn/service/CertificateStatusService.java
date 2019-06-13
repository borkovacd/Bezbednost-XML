package com.ftn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.CertificateModel;
import com.ftn.model.CertificateStatus;
import com.ftn.model.SubjectSoftware;
import com.ftn.repository.CertificateRepository;
import com.ftn.repository.CertificateStatusRepository;
import com.ftn.repository.SubjectSoftwareRepository;

@Service
public class CertificateStatusService {

	@Autowired
	private CertificateStatusRepository certStatRepository;
	
	@Autowired
	private CertificateRepository certRepository;
	
	@Autowired
	private SubjectSoftwareRepository subSoftRep;
	
	public void saveAfterCreated() {
		
	}
	public boolean revokeCert(Integer serialNumber,String message){
		
		CertificateStatus novi = new CertificateStatus();
		novi.setMessage(message);
		novi.setSerijskiBroj(serialNumber);
		novi.setStatus(true);
		certStatRepository.save(novi);
		
		CertificateModel certificat = certRepository.findOneBySerialNumber(serialNumber);
		
		String email = certificat.getSubSoft().getEmail();
		
		SubjectSoftware ss = subSoftRep.findByEmail(email);
		
		ss.setHasCert(false);
		
		subSoftRep.save(ss);
		
		certificat.setRevoked(true);
		certRepository.save(certificat);
		
		List<CertificateModel> allCertificate = certRepository.findAll();
		
		for(CertificateModel cert : allCertificate){
			if(cert.getIssuerSoft().getId() == ss.getId()){
				System.out.println(""+ss.getId() + ", "+cert.getIssuerSoft().getId());
				cert.setRevoked(true);
				
				CertificateStatus novi1 = new CertificateStatus();
				novi1.setMessage("Issuer's certificate has been revoked.");
				novi1.setSerijskiBroj(serialNumber);
				novi1.setStatus(true);
				certStatRepository.save(novi1);
				
				String email1 = cert.getSubSoft().getEmail();
				
				SubjectSoftware s = subSoftRep.findByEmail(email1);
				
				s.setHasCert(false);
				
				subSoftRep.save(s);
				
			}
		}
		return true;
	}
}
