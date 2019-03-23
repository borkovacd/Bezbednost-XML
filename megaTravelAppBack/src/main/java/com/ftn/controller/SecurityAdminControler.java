package com.ftn.controller;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.Date;
import java.util.Random;

import com.ftn.model.SubjectSoftware;
import com.ftn.modelDTO.CertificateDTO;
import com.ftn.service.SubjectSoftwareService;
import com.ftn.configuration.CertificateGenerator;
import com.ftn.keystore.KeyStoreReader;
import com.ftn.keystore.KeyStoreWriter;
import com.ftn.model.IssuerData;
import com.ftn.model.SubjectData;
@RestController
@RequestMapping(value = "/api/security")
public class SecurityAdminControler {
	
	@Autowired 
	private SubjectSoftwareService ssService;
	
	private KeyStoreWriter keyStoreWriter = new KeyStoreWriter() ;
	private KeyStoreReader keyStoreReader = new KeyStoreReader() ;
	
	@RequestMapping(value="/createCertificate",	method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:4200")
	public void createSertficate(@RequestBody CertificateDTO cdto) throws KeyStoreException 
	{
		
		SubjectSoftware ss = ssService.getSoftware(cdto.getCity()); // entiteti u bazi, divizije
		System.out.println(cdto.getCity());
		System.out.println("ovo je taj: " + ss.getSoftwareId());
		
		String str = "someString"; 
		char[] password = str.toCharArray();
		
		// ucitan keystore.jks, preko passworda
		keyStoreWriter.loadKeyStore(password);
		
		SubjectData subjectData = generateSubjectData(ss, cdto);
		KeyPair keyPairIssuer = generateKeyPair();
		IssuerData issuerData = generateIssuerData(keyPairIssuer.getPrivate());
		
		// generisanje sertifikata
		CertificateGenerator cg = new CertificateGenerator();

		System.out.println(subjectData.getX500name());
		
		System.out.println(subjectData.getPublicKey());
		System.out.println(subjectData.getStartDate());
		System.out.println(subjectData.getEndDate());
		X509Certificate cert = cg.generateCertificate(subjectData, issuerData);
		
		Certificate certificate = cert;
		// sertifikat je napravljen
		
		String alias = "alias1";
		
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
		
		System.out.println("trenutna: " + arr.length);

	//	String[] stockArr = new String[stockList.size()];
	//	stockArr = stockList.toArray(stockArr);
		
		// konverzija liste u []
		//lanacS = (Certificate[])(lanacSertifikata).toArray();
		
		lanacS = lanacSertifikata.toArray(new Certificate[lanacSertifikata.size()]);
		
		//keyStoreWriter.write(alias, issuerData.getPrivateKey(), password, certificate);
		
		// upis u keyStore
		keyStoreReader.getKeyStore().setKeyEntry(alias, issuerData.getPrivateKey(), password, arr);
		
		keyStoreWriter.write2(alias, issuerData.getPrivateKey(), password, arr);
		// cuvanje keyStore
		keyStoreWriter.saveKeyStore(password);
		
	System.out.println("KRAJ THE END: " + keyStoreReader.getKeyStore().getCertificateChain(alias).length);

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
	public SubjectData generateSubjectData(SubjectSoftware ss, CertificateDTO cdto)
	{
		try {
			KeyPair keyPairSubject = generateKeyPair();
		
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		
			Random random = new Random();
			Integer integ = random.nextInt(9);
			String sn = integ.toString();
			
			sn = "2";
			
			System.out.println(sn);
			
		// oznaka softvera, drzava i email adresa
			builder.addRDN(BCStyle.UNIQUE_IDENTIFIER, ss.getSoftwareId());
			builder.addRDN(BCStyle.PLACE_OF_BIRTH, ss.getCity());
			builder.addRDN(BCStyle.COUNTRY_OF_RESIDENCE, ss.getState());
			builder.addRDN(BCStyle.EmailAddress, ss.getEmail());
			
			System.out.println(cdto.getStartDate());
			
			// datumi
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse(cdto.getStartDate());
			Date endDate = iso8601Formater.parse(cdto.getEndDate());
			
			System.out.println("uspeo");
			
	    
			return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
		}
		
		catch(ParseException e)
		{
			
		}
		
		return null ;
		

	}
	
	// izdavac sertifikata
	public IssuerData generateIssuerData(PrivateKey issuerKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, "Security Admin");
	    builder.addRDN(BCStyle.O, "MegaTravel");
	    builder.addRDN(BCStyle.OU, "MegaTravelRoot");
	    builder.addRDN(BCStyle.C, "RS");
	    builder.addRDN(BCStyle.E, "megaTravel@gmail.com");
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, "654321");

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}
	
	
	
	
}