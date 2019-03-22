package com.ftn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import com.ftn.model.SubjectSoftware;
import com.ftn.modelDTO.CertificateDTO;
import com.ftn.service.SubjectSoftwareService;

@RestController
@RequestMapping(value = "/api/security")
public class SecurityAdminControler {
	
	@Autowired 
	private SubjectSoftwareService ssService;
	
	@RequestMapping(value="createCertificate",	method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public void createSertficate(@RequestBody CertificateDTO cdto) {
		
		System.out.println(cdto.getCity());
		
	}
	
	@RequestMapping(value="/getSubjectSoftware",	method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public ArrayList<SubjectSoftware> getSubjectSoftware() {

		ArrayList<SubjectSoftware> ssList = new ArrayList<SubjectSoftware>();
		
		ssList = ssService.getSoftwares();
		

		ArrayList<SubjectSoftware> ssList2 = new ArrayList<SubjectSoftware>();
		
		
		for(int i=0; i<ssList.size(); i++) {
			if(ssList.get(i).isHasCert() == false) {
				ssList2.add(ssList.get(i));
			}
		}

		return ssList2;
		
	}
	
	@RequestMapping(value="/getCertificates",	method = RequestMethod.GET)
	public ArrayList<X509Certificate> getCeritificates() {
		
		return null;
	}
}
