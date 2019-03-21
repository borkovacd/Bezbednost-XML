package com.ftn.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.modelDTO.CertificateDTO;

@RestController
public class SecurityAdminControler {
	
	@RequestMapping(value="createCertificate")
	public void createSertficate(@RequestBody CertificateDTO cdto) {
		
	}
}
