package com.ftn.controller;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.annotation.PostConstruct;

import com.ftn.model.SubjectSoftware;
import com.ftn.modelDTO.CertificateDTO;
import com.ftn.repository.CertificateRepository;
import com.ftn.repository.SubjectSoftwareRepository;
import com.ftn.service.CertificateStatusService;
import com.ftn.service.SubjectSoftwareService;
import com.ftn.configuration.CertificateGenerator;
import com.ftn.keystore.KeyStoreReader;
import com.ftn.keystore.KeyStoreWriter;
import com.ftn.model.CertificateModel;
import com.ftn.model.IssuerData;
import com.ftn.model.SubjectData;
@RestController
@RequestMapping(value = "/api/security")
public class SecurityAdminControler {
	
	@Autowired 
	private SubjectSoftwareService ssService;
	@Autowired 
	private CertificateStatusService statusService;
	
	@Autowired 
	private SubjectSoftwareRepository repos;
	
	@Autowired 
	private CertificateRepository certRepos;
	
	private KeyStoreWriter keyStoreWriter = new KeyStoreWriter() ;
	private KeyStoreReader keyStoreReader = new KeyStoreReader() ;
	private KeyPair keyPairIssuer;
	
	/*
	@PostConstruct
	public void init()
	{
		keyStoreWriter = new KeyStoreWriter();
		String globalPass = "someString";
		keyStoreWriter.loadKeyStore(null, globalPass.toCharArray());
		keyStoreWriter.saveKeyStore("./files/keystore.jks", globalPass.toCharArray());
		keyPairIssuer = generateKeyPair();
	}
	*/
	
	@RequestMapping(value="/createCertificate/{email}",	method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public void createSertficate(@RequestBody CertificateDTO cdto, @PathVariable String email) throws KeyStoreException 
	{
		
		SubjectSoftware ss = repos.findByCity(cdto.getCity()); // SUBJECT
		SubjectSoftware iss = repos.findByEmail(email); // ISSUER
		
		
		if (iss.isHasCert() == true) {
		Long idSubject = ss.getId();
		Long idIssuer = iss.getId();
		
		ss.setHasCert(true);
		
		repos.save(ss);
		
		String str = "someString"; 
		char[] password = str.toCharArray();
		
		// ucitan keystore.jks, preko passworda
		keyStoreWriter.loadKeyStore("./files/keystore.jks", password);
		
				
		SubjectData subjectData = generateSubjectData(ss, cdto);
		//KeyStoreReader keyStoreReader = new KeyStoreReader();
		
		String issuerPass = "certificatePass" + iss.getId(); // password je oblika certificatePass123456789
		PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("./files/keystore.jks", str, issuerPass, issuerPass);
				
		System.out.println("Privatni kljuc je: " + privateKeyIssuer);
		
		IssuerData issuerData = generateIssuerData(privateKeyIssuer, iss);
		
		// generisanje sertifikata
		CertificateGenerator cg = new CertificateGenerator();

		X509Certificate cert = cg.generateCertificate(subjectData, issuerData);
		
		Certificate certificate = cert;
		// sertifikat je napravljen
		
		
		// Dodavanje u bazu
		//*********************************************
		
		CertificateModel cm = new CertificateModel();
    	
    	cm.setIssuerSoft(iss);
    	cm.setSubSoft(ss);
    	
    	cm.setStartDate(cert.getNotBefore());
    	cm.setEndDate(cert.getNotAfter());
    	
    	String serial = cert.getSerialNumber().toString();
    	
    	cm.setSerialNumber(Integer.parseInt(serial));
    	
    	cm.setRevoked(false);
    	
    	certRepos.save(cm);
		
    	// *********************************************
    	
    	// upis u globalni keystore
		String certificatePass = "certificatePass"  + ss.getId(); // oblika subjectPass123456789
		keyStoreWriter.write(certificatePass, subjectData.getPrivateKey(), certificatePass.toCharArray(), cert);
		
		String globalPass = "someString";
		keyStoreWriter.saveKeyStore("./files/keystore.jks", globalPass.toCharArray());
		
		// upis u njegov keystore
		KeyStoreWriter keyStoreWriterNovi = new KeyStoreWriter();
		keyStoreWriterNovi.loadKeyStore(null, ss.getId().toString().toCharArray());
		
		keyStoreWriterNovi.saveKeyStore("./files/localKeyStore" + ss.getId() + ".jks", ss.getId().toString().toCharArray());
		
		String localAllias = "myCertificate";
		
		keyStoreWriterNovi.write(localAllias, subjectData.getPrivateKey(), localAllias.toCharArray(), cert);
		keyStoreWriterNovi.saveKeyStore(".files/localKeyStore" + ss.getId().toString() + ".jks", ss.getId().toString().toCharArray());
		}
		
		else
		{
			System.out.println("Izabrani izdavalac nema sertifikat, pa ni ne moze da ga izda!");
		}
		
	}
	
	/**
	 * String alias = "alias1";
		
		// u [] smestim lanac sertifikata iz keyStore
		Certificate[] lanacS = keyStoreReader.getKeyStore().getCertificateChain("alias1");
		
		// izvrsim kastovanje u listu
		ArrayList<Certificate>lanacSertifikata = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore().getCertificateChain("alias1")));
		// dodam trenutni sertifikat u listu
		lanacSertifikata.add(certificate);
		System.out.println("duzina novog: " + lanacSertifikata.size());
		
		Certificate[] arr= new Certificate[lanacSertifikata.size()];

		for(int i=0; i<lanacSertifikata.size(); i++) {
			arr[i] = lanacSertifikata.get(i);
		}
		
		lanacS = lanacSertifikata.toArray(new Certificate[lanacSertifikata.size()]);
				
		// upis u keyStore
		keyStoreReader.getKeyStore().setKeyEntry(alias, issuerData.getPrivateKey(), password, arr);
		
		keyStoreWriter.write2(alias, issuerData.getPrivateKey(), password, arr);
		// cuvanje keyStore
		keyStoreWriter.saveKeyStore(password);
	 * @param string1
	 * @param string2
	 * @return
	 */
	
	@RequestMapping(value="/communicate/{string1}/{string2}",	method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean checkCommunication(@PathVariable String string1, @PathVariable String string2) {
		
		SubjectSoftware soft1 = ssService.getSoftwareByEmail(string1); 
		SubjectSoftware soft2 = ssService.getSoftware(string2);
		
		if(soft1.isHasCert() && soft2.isHasCert()) { //ako oba imaju sertifikate
			
			boolean revoked = false;
			boolean expired = false;
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			
			Date current_date = calendar.getTime();
			
			ArrayList<CertificateModel> lanacSertifikata = new ArrayList<CertificateModel>();
			lanacSertifikata = (ArrayList<CertificateModel>) certRepos.findAll();
		
			for(int i=0; i<lanacSertifikata.size(); i++) {
				String userEmail = lanacSertifikata.get(i).getIssuerSoft().getEmail();
				if(userEmail.equals(string1)) {
					CertificateModel certificate = lanacSertifikata.get(i);
					revoked = certificate.isRevoked();
					Date certificateEndDate = certificate.getEndDate();
					if(current_date.after(certificateEndDate)) {
						expired = true;
					}
				}
				if(userEmail.equals(string2)) {
					CertificateModel certificate = lanacSertifikata.get(i);
					revoked = certificate.isRevoked();
					Date certificateEndDate = certificate.getEndDate();
					if(current_date.after(certificateEndDate)) {
						expired = true;
					}
				}
			}
			
			if(expired == false || revoked == false) {
				return true;
			} else {
				return false;
			}
			
		}
		return false;
		
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
	
	@RequestMapping(value="/getAllSubjectSoftwares/{email}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public ArrayList<SubjectSoftware> getAllSubjectSoftwares(@PathVariable String email) {

		ArrayList<SubjectSoftware> ssList = new ArrayList<SubjectSoftware>();
		ssList = ssService.getSoftwares();

		ArrayList<SubjectSoftware> ssList2 = new ArrayList<SubjectSoftware>();
		
		for(int i=0; i<ssList.size(); i++) {
			if(!ssList.get(i).getEmail().equals(email)) {
				//System.out.println("1 - " + ssList.get(i).getEmail() + " 2 - " + email);
				if(!ssList.get(i).getEmail().equals("MTRoot@gmail.com"))
					ssList2.add(ssList.get(i));
			}
		}

		return ssList2;	
		
	}
	
	@RequestMapping(value="/getCertificates/{email}",	method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public ArrayList<CertificateModel> getCeritificates(@PathVariable String email) {
		
		 ArrayList<CertificateModel> lanacSertifikata = new ArrayList<CertificateModel>();
		 

		 ArrayList<CertificateModel> lanacSertifikataNova = new ArrayList<CertificateModel>();
	
		 lanacSertifikata = (ArrayList<CertificateModel>) certRepos.findAll();
	
		
		for(int i=0; i<lanacSertifikata.size(); i++) {
			
			String userEmail = lanacSertifikata.get(i).getIssuerSoft().getEmail();
			
			if(userEmail.equals(email)) {
				lanacSertifikataNova.add(lanacSertifikata.get(i));
				System.out.println(lanacSertifikata.get(i).isRevoked());
			}
			
		}
		
		return lanacSertifikataNova;
		 
	}
	
	@RequestMapping(value="/getAllCertificates/{email}",	method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public ArrayList<CertificateModel> getAllCeritificates(@PathVariable String email) {
		
		 ArrayList<CertificateModel> lanacSertifikata = new ArrayList<CertificateModel>();
		 
		 lanacSertifikata = (ArrayList<CertificateModel>) certRepos.findAll();
		
		return lanacSertifikata;
		 
	}
	
	@RequestMapping(value="/revokeCertificate/{serialNumber}/{message}",	method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean revokeCeritificate(@PathVariable Integer serialNumber ,@PathVariable String message) {
		System.out.println("Poruka koja je stigla "+ message);
		boolean pomocni = false;
		pomocni = statusService.revokeCert(serialNumber, message);
		
		return pomocni;
	}
		
	
	// dodatne metode
	
	// metoda 1 - generisanje para kljuceva
	public KeyPair generateKeyPair() {
        try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			
			// SecureRandom.getInstance - PARAMETRI -->
			// The name of the pseudo-random number generation (PRNG) algorithm supplied by the SUN provider. 
			// This algorithm uses SHA-1 as the foundation of the PRNG. It computes the SHA-1 hash over a true-random
			// seed value concatenated with a 64-bit counter which is incremented by 1 for each operation. 
			// From the 160-bit SHA-1 output, only 64 bits are used.
			
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			
			// 2048 - The maximum key size that the provider supports for the cryptographic service.
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	// metoda 2 - generisanje podataka subjekta, na osnovu dto
	// ISPRAVLJENO
	public SubjectData generateSubjectData(SubjectSoftware ss, CertificateDTO cdto)
	{
		try {
			KeyPair keyPairSubject = generateKeyPair();
		
			String sn = ss.getId().toString();
			System.out.println(sn);
			
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		
			
		// oznaka softvera, drzava i email adresa
			builder.addRDN(BCStyle.CN, "Security");
			builder.addRDN(BCStyle.O, ss.getSoftwareId());
			builder.addRDN(BCStyle.OU, ss.getCity());
			builder.addRDN(BCStyle.C, ss.getState());
			builder.addRDN(BCStyle.E, ss.getEmail());
			builder.addRDN(BCStyle.UID, ss.getId().toString());
						
			// datumi
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse(cdto.getStartDate());
			Date endDate = iso8601Formater.parse(cdto.getEndDate());
			
			System.out.println("uspeo");

			//Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
			
			return new SubjectData(keyPairSubject.getPublic(), keyPairSubject.getPrivate(), builder.build(), sn, startDate, endDate);
		}
		
		catch(ParseException e)
		{
			
		}
		
		return null ;
		

	}
	
	// izdavalac sertifikata
	// ISPRAVLJENO
	public IssuerData generateIssuerData(PrivateKey issuerKey, SubjectSoftware ss) 
	{
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, "Security");
		builder.addRDN(BCStyle.O, ss.getSoftwareId());
		builder.addRDN(BCStyle.OU, ss.getCity());
		builder.addRDN(BCStyle.C, ss.getState());
		builder.addRDN(BCStyle.E, ss.getEmail());
		builder.addRDN(BCStyle.UID, ss.getId().toString());

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}
	
	
	
	
	
	
}