package com.ftn.controller;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.ftn.model.SubjectSoftware;
import com.ftn.model.UserToken;
import com.ftn.modelDTO.CertificateDTO;
import com.ftn.repository.CertificateRepository;
import com.ftn.repository.CommunicationRelationshipRepository;
import com.ftn.repository.SubjectSoftwareRepository;
import com.ftn.security.LoggerUtils;
import com.ftn.security.TokenUtils;
import com.ftn.service.CertificateStatusService;
import com.ftn.service.SubjectSoftwareService;
import com.ftn.configuration.CertificateGenerator;
import com.ftn.keystore.KeyStoreReader;
import com.ftn.keystore.KeyStoreWriter;
import com.ftn.model.CertificateModel;
import com.ftn.model.CommunicationRelationship;
import com.ftn.model.IssuerData;
import com.ftn.model.SubjectData;

@RestController
@RequestMapping(value = "/api/security")
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityAdminControler {
	private static final Logger log = LoggerFactory.getLogger(SecurityAdminControler.class);
	@Autowired
	private SubjectSoftwareService ssService;
	@Autowired
	private CertificateStatusService statusService;

	@Autowired
	private SubjectSoftwareRepository repos;
	
	@Autowired
	private CommunicationRelationshipRepository communicationRelationshipRepository;

	@Autowired
	private CertificateRepository certRepos;

	@Autowired
	private TokenUtils tokenUtils;

	private KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
	private KeyStoreReader keyStoreReader = new KeyStoreReader();
	private KeyPair keyPairIssuer;

	/*
	 * @PostConstruct public void init() { keyStoreWriter = new
	 * KeyStoreWriter(); String globalPass = "someString";
	 * keyStoreWriter.loadKeyStore(null, globalPass.toCharArray());
	 * keyStoreWriter.saveKeyStore("./files/keystore.jks",
	 * globalPass.toCharArray()); keyPairIssuer = generateKeyPair(); }
	 */

	@PreAuthorize("hasAuthority('CREATE_CERT')")
	@RequestMapping(value = "/createCertificate/{token}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void createSertficate(@RequestBody CertificateDTO cdto, @PathVariable String token)
			throws Exception {
		
		SubjectSoftware ss = repos.findByCity(cdto.getCity()); // SUBJECT
		
		token = token.substring(1,token.length()-1).toString();
		
		String email = tokenUtils.getUserSecurity(token).getUsername();
		
		SubjectSoftware iss = repos.findByEmail(email); // ISSUER
		log.info("User id: {} CREACA",iss.getId());
		System.out.println(iss.getEmail());
		

		// Izdavalac sertifikata mora imati sertifikat!
		if (iss.isHasCert() == true) 
		{

			Long idSubject = ss.getId();
			Long idIssuer = iss.getId();

			ss.setHasCert(true);

			repos.save(ss);

			String str = "someString";
			char[] password = str.toCharArray();

			// ucitan keystore.jks, preko passworda
			keyStoreWriter.loadKeyStore("./files/keystoreSecurity.p12", password);

			SubjectData subjectData = generateSubjectData(ss, cdto);
			// KeyStoreReader keyStoreReader = new KeyStoreReader();

			String issuerPass = "certificatePass" + iss.getId(); // password je
																	// oblika
																	// certificatePass123456789
			String alias = "alias1";
			
			if (iss.getId() == 1)
			{
				System.out.println("Izdavalac je Root!");
				issuerPass = "someString" ;
				
				PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("./files/keystoreSecurity.p12", str, issuerPass, issuerPass);

				System.out.println("Privatni kljuc je: " + privateKeyIssuer);

				IssuerData issuerData = generateIssuerData(privateKeyIssuer, iss);

				// generisanje sertifikata
				CertificateGenerator cg = new CertificateGenerator();

				X509Certificate cert = cg.generateCertificate(subjectData, issuerData);

				Certificate certificate = cert;
				// sertifikat je napravljen

				// Dodavanje u bazu
				// *********************************************

				CertificateModel cm = new CertificateModel();

				cm.setIssuerSoft(iss);
				cm.setSubSoft(ss);

				cm.setStartDate(cert.getNotBefore());
				cm.setEndDate(cert.getNotAfter());

				String serial = cert.getSerialNumber().toString();

				cm.setSerialNumber(Integer.parseInt(serial));

				cm.setRevoked(false);

				certRepos.save(cm);
				log.info(LoggerUtils.getSMarker(), "SECURITY_EVENT User id:{} CREACASUCCESS ", 
						iss.getId());
				log.info(LoggerUtils.getNMarker(), "NEPOR_EVENT User id:{}  CREASUCCESS",
						iss.getId());

				// *********************************************

				// upis u globalni keystore
				String certificatePass = "certificatePass" + ss.getId(); // oblika
																			// subjectPass123456789
				keyStoreWriter.write(certificatePass, subjectData.getPrivateKey(), certificatePass.toCharArray(), cert);

				String globalPass = "someString";
				keyStoreWriter.saveKeyStore("./files/keystoreSecurity.p12", str.toCharArray());

				// upis u njegov keystore
				KeyStoreWriter keyStoreWriterNovi = new KeyStoreWriter();
				keyStoreWriterNovi.loadKeyStore(null, ss.getId().toString().toCharArray());

				keyStoreWriterNovi.saveKeyStore("./files/localKeyStore" + ss.getId() + ".p12",
						ss.getId().toString().toCharArray());

				String localAllias = "myCertificate";

				keyStoreWriterNovi.write(localAllias, subjectData.getPrivateKey(), localAllias.toCharArray(), cert);
				keyStoreWriterNovi.saveKeyStore(".files/localKeyStore" + ss.getId().toString() + ".p12",
						ss.getId().toString().toCharArray());
				log.info("CRECASUCCESS");

			}
			
			else // ukoliko ne izdaje ROOT
			{
				System.out.println("Izdavalac nije root!");
				issuerPass = "certificatePass" + iss.getId(); // password je
				// oblika
				// certificatePass123456789
				alias = "alias1";
				str = "someString";
				
				PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("./files/keystoreSecurity.p12", str, issuerPass, issuerPass);

				System.out.println("Privatni kljuc je: " + privateKeyIssuer);

				IssuerData issuerData = generateIssuerData(privateKeyIssuer, iss);

				// generisanje sertifikata
				CertificateGenerator cg = new CertificateGenerator();

				X509Certificate cert = cg.generateCertificate(subjectData, issuerData);

				Certificate certificate = cert;
				// sertifikat je napravljen

				// Dodavanje u bazu
				// *********************************************

				CertificateModel cm = new CertificateModel();

				cm.setIssuerSoft(iss);
				cm.setSubSoft(ss);

				cm.setStartDate(cert.getNotBefore());
				cm.setEndDate(cert.getNotAfter());

				String serial = cert.getSerialNumber().toString();

				cm.setSerialNumber(Integer.parseInt(serial));

				cm.setRevoked(false);

				certRepos.save(cm);
				log.info(LoggerUtils.getSMarker(), "SECURITY_EVENT User id: {}  CREACASUCCESS", 
						iss.getId());
				log.info(LoggerUtils.getNMarker(), "NEPOR_EVENT User id:{}  CREACASUCCESS", 
						iss.getId());

				// *********************************************

				// upis u globalni keystore
				String certificatePass = "certificatePass" + ss.getId(); // oblika
																			// subjectPass123456789
				keyStoreWriter.write(certificatePass, subjectData.getPrivateKey(), certificatePass.toCharArray(), cert);

				String globalPass = "someString";
				keyStoreWriter.saveKeyStore("./files/keystoreSecurity.p12", str.toCharArray());

				// upis u njegov keystore
				KeyStoreWriter keyStoreWriterNovi = new KeyStoreWriter();
				keyStoreWriterNovi.loadKeyStore(null, ss.getId().toString().toCharArray());

				keyStoreWriterNovi.saveKeyStore("./files/localKeyStore" + ss.getId() + ".p12",
						ss.getId().toString().toCharArray());

				String localAllias = "myCertificate";

				keyStoreWriterNovi.write(localAllias, subjectData.getPrivateKey(), localAllias.toCharArray(), cert);
				keyStoreWriterNovi.saveKeyStore(".files/localKeyStore" + ss.getId().toString() + ".p12",
						ss.getId().toString().toCharArray());
				log.info("CRECASUCESS");

			}
		}

		else {
			System.out.println("Izabrani izdavalac nema sertifikat, pa ne moze ni da ga izda!");
			log.error(LoggerUtils.getNMarker(), "NEPOR_EVENT User id: {} CREACAERROR  ", iss.getId());

		}
		

	}

	/**
	 * String alias = "alias1";
	 * 
	 * // u [] smestim lanac sertifikata iz keyStore Certificate[] lanacS =
	 * keyStoreReader.getKeyStore().getCertificateChain("alias1");
	 * 
	 * // izvrsim kastovanje u listu ArrayList<Certificate>lanacSertifikata =
	 * new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore().
	 * getCertificateChain("alias1"))); // dodam trenutni sertifikat u listu
	 * lanacSertifikata.add(certificate); System.out.println("duzina novog: " +
	 * lanacSertifikata.size());
	 * 
	 * Certificate[] arr= new Certificate[lanacSertifikata.size()];
	 * 
	 * for(int i=0; i<lanacSertifikata.size(); i++) { arr[i] =
	 * lanacSertifikata.get(i); }
	 * 
	 * lanacS = lanacSertifikata.toArray(new
	 * Certificate[lanacSertifikata.size()]);
	 * 
	 * // upis u keyStore keyStoreReader.getKeyStore().setKeyEntry(alias,
	 * issuerData.getPrivateKey(), password, arr);
	 * 
	 * keyStoreWriter.write2(alias, issuerData.getPrivateKey(), password, arr);
	 * // cuvanje keyStore keyStoreWriter.saveKeyStore(password);
	 * 
	 * @param string1
	 * @param string2
	 * @return
	 */

	
	@RequestMapping(value = "/communicate/{string1}/{string2}", method = RequestMethod.GET)
	public Integer checkCommunication(@PathVariable String string1, @PathVariable String string2) {
		log.debug("CKCOMM");
		
		string1 = string1.substring(1, string1.length()-1).toString();
		
		String email = null;
		try {
			email = tokenUtils.getUserSecurity(string1).getUsername();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SubjectSoftware soft1 = ssService.getSoftwareByEmail(email);
		SubjectSoftware soft2 = ssService.getSoftware(string2);

		if (soft1.isHasCert() && soft2.isHasCert()) { // ako oba imaju
														// sertifikate
			
			boolean alreadyCommunicating = false;
			
			boolean revoked = false;
			boolean expired = false;
			boolean notAcitve = false;

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);

			Date current_date = calendar.getTime();

			ArrayList<CertificateModel> lanacSertifikata = new ArrayList<CertificateModel>();
			lanacSertifikata = (ArrayList<CertificateModel>) certRepos.findAll();
			
			CertificateModel cert1 = null;
			CertificateModel cert2 = null;

			for (int i = 0; i < lanacSertifikata.size(); i++) {
				String userEmail = lanacSertifikata.get(i).getSubSoft().getEmail();
				System.out.println("UserEmail: " + userEmail);
				System.out.println("Email1: " + email);
				System.out.println("Email2: " + soft2.getEmail());
				if (userEmail.equals(email)) {
					CertificateModel certificate = lanacSertifikata.get(i);
					cert1 = lanacSertifikata.get(i);
					revoked = certificate.isRevoked();
					Date certificateEndDate = certificate.getEndDate();
					if (current_date.after(certificateEndDate)) {
						expired = true;
					}
					Date certificateStartDate = certificate.getStartDate();
					if(current_date.before(certificateStartDate)) {
						notAcitve = true;
					}
				}
				if (userEmail.equals(soft2.getEmail())) {
					CertificateModel certificate = lanacSertifikata.get(i);
					cert2 = lanacSertifikata.get(i);
					revoked = certificate.isRevoked();
					Date certificateEndDate = certificate.getEndDate();
					if (current_date.after(certificateEndDate)) {
						expired = true;
					}
					Date certificateStartDate = certificate.getStartDate();
					if(current_date.before(certificateStartDate)) {
						notAcitve = true;
					}
				}
			}
			
			ArrayList<CommunicationRelationship> communicatingList = (ArrayList<CommunicationRelationship>) communicationRelationshipRepository.findAll();
			for (int i = 0; i < communicatingList.size(); i++) {
				if((communicatingList.get(i).getCertificateCommunication1() == cert1 || communicatingList.get(i).getCertificateCommunication1() == cert2) 
				  && (communicatingList.get(i).getCertificateCommunication2() == cert1 || communicatingList.get(i).getCertificateCommunication2() == cert2)) {
					alreadyCommunicating = true;
				}
			
			}

			if (expired == false && revoked == false && notAcitve == false) {
				
				if(alreadyCommunicating == true) {
					
					return 0;
					
				} else {
					log.info("CKSUCCESS");
					log.info(LoggerUtils.getNMarker(), "NEPOR_EVENT , "
							+ "CKSUCCESS between {} and {}", soft1.getId(),
							soft2.getId());
				
					CommunicationRelationship cr = new CommunicationRelationship();
					cr.setCertificateCommunication1(cert1);
					cr.setCertificateCommunication2(cert2);
				
					communicationRelationshipRepository.save(cr);
				

					return 1;
				}
			} else {
				log.error("CKFAIL");
				return 2;
			}

		}
		log.info(LoggerUtils.getNMarker(), "NEPOR_EVENT , COMMUNFAIL , CA not found between {} ", soft1.getId(),
				soft2.getId());

		return 2;

	}

	@PreAuthorize("hasAuthority('READ_SUB_SOFT')")
	@RequestMapping(value = "/getSubjectSoftware", method = RequestMethod.GET)
	public ArrayList<SubjectSoftware> getSubjectSoftware() {
		log.debug("SS");

		ArrayList<SubjectSoftware> ssList = new ArrayList<SubjectSoftware>();

		ssList = ssService.getSoftwares();

		ArrayList<SubjectSoftware> ssList2 = new ArrayList<SubjectSoftware>();

		for (int i = 0; i < ssList.size(); i++) {
			if (ssList.get(i).isHasCert() == false && !ssList.get(i).getEmail().equals("MTRoot@gmail.com")) {
				ssList2.add(ssList.get(i));
			}
		}
		log.info("SSSUCCCESS");

		return ssList2;
	}
	

	@PreAuthorize("hasAuthority('READ_SUB_SOFT')")
	@RequestMapping(value = "/getAllSubjectSoftwares/{email}", method = RequestMethod.GET)
	public ArrayList<SubjectSoftware> getAllSubjectSoftwares(@PathVariable String email) throws Exception {
		log.info("GETSS");
		ArrayList<SubjectSoftware> ssList = new ArrayList<SubjectSoftware>();
		ssList = ssService.getSoftwares();
		
		String etwas = email.substring(1, email.length()-1).toString();
		
		String emailString = tokenUtils.getUserSecurity(etwas).getUsername();
		
		SubjectSoftware soft1 = ssService.getSoftwareByEmail(emailString);

		ArrayList<SubjectSoftware> ssList2 = new ArrayList<SubjectSoftware>();

		for (int i = 0; i < ssList.size(); i++) {
			if (!ssList.get(i).getEmail().equals(emailString)) {
				// System.out.println("1 - " + ssList.get(i).getEmail() + " 2 -
				// " + email);
				if (!ssList.get(i).getEmail().equals("MTRoot@gmail.com") && !ssList.get(i).getEmail().contentEquals("MTAgent@gmail.com") && !ssList.get(i).getEmail().contentEquals("localhost@gmail.com")) 
					ssList2.add(ssList.get(i));
				
			}
		}

		return ssList2;

	}
	
	
	@PreAuthorize("hasAuthority('READ_CERT')")
	@RequestMapping(value = "/getCertificates/{token}", method = RequestMethod.GET)
	public ArrayList<CertificateModel> getCeritificates(@PathVariable String token, Authentication auth) throws Exception {
		
		log.info("GETCA");
		System.out.println("Usao da ispise sertifikate");
		System.out.println("Token je: " + token);
		
		token = token.substring(1,token.length()-1).toString();
		
		String email = tokenUtils.getUserSecurity(token).getUsername();

		System.out.println("Dobio sam mejl: " + email);

		ArrayList<CertificateModel> lanacSertifikata = new ArrayList<CertificateModel>();

		ArrayList<CertificateModel> lanacSertifikataNova = new ArrayList<CertificateModel>();

		lanacSertifikata = (ArrayList<CertificateModel>) certRepos.findAll();
		
		System.out.println(lanacSertifikata.size());

		for (int i = 0; i < lanacSertifikata.size(); i++) {
			System.out.println("uso");
			String userEmail = lanacSertifikata.get(i).getIssuerSoft().getEmail();
			System.out.println(userEmail);

			if (userEmail.equals(email)) {
				lanacSertifikataNova.add(lanacSertifikata.get(i));
				System.out.println(lanacSertifikata.get(i).isRevoked());
			}

		}
		log.info(LoggerUtils.getNMarker(), "NEPOR_EVENT CASUCCESS " );

		return lanacSertifikataNova;

	}

	@PreAuthorize("hasAuthority('READ_ALLCERT')")
	@RequestMapping(value = "/getAllCertificates", method = RequestMethod.GET)
	public ArrayList<CertificateModel> getAllCeritificates() {
		
		log.info("GETACA");

		ArrayList<CertificateModel> lanacSertifikata = new ArrayList<CertificateModel>();

		lanacSertifikata = (ArrayList<CertificateModel>) certRepos.findAll();

		return lanacSertifikata;

	}

	@PreAuthorize("hasAuthority('READ_CERT')")
	@RequestMapping(value = "/getMyCertificate/{token}", method = RequestMethod.GET)
	public CertificateModel getMyCeritificate(@PathVariable String token) throws Exception {
		
		log.info("GETACA");
		
		token = token.substring(1,token.length()-1).toString();
		
		String email = tokenUtils.getUserSecurity(token).getUsername();

		ArrayList<CertificateModel> lanacSertifikata = new ArrayList<CertificateModel>();
		ArrayList<CertificateModel> lanacSertifikata2 = new ArrayList<CertificateModel>();

		lanacSertifikata = (ArrayList<CertificateModel>) certRepos.findAll();
		
		for(int i=0; i<lanacSertifikata.size(); i++){
			
			if (lanacSertifikata.get(i).getSubSoft().getEmail().equals(email)) {
				System.out.println("nasao sam moj");
				return lanacSertifikata.get(i);
			}
			
		}
		
		return null;

	}

	@PreAuthorize("hasAuthority('REVOKE_CERT')")
	@GetMapping(value = "/revokeCertificate/{serialNumber}/{message}/{token}")
	public boolean revokeCeritificate(@PathVariable Integer serialNumber, @PathVariable String message, @PathVariable String token) {
		System.out.println("Poruka koja je stigla " + message);
		
		System.out.println("token je sledeci: " + token);
		log.info("User id: {}  REVCA",token);
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
			// The name of the pseudo-random number generation (PRNG) algorithm
			// supplied by the SUN provider.
			// This algorithm uses SHA-1 as the foundation of the PRNG. It
			// computes the SHA-1 hash over a true-random
			// seed value concatenated with a 64-bit counter which is
			// incremented by 1 for each operation.
			// From the 160-bit SHA-1 output, only 64 bits are used.

			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

			// 2048 - The maximum key size that the provider supports for the
			// cryptographic service.
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
	public SubjectData generateSubjectData(SubjectSoftware ss, CertificateDTO cdto) {
		try {
			KeyPair keyPairSubject = generateKeyPair();

			Random rand = new Random();
			
			int sn = rand.nextInt(10000);
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

			// Kreiraju se podaci za sertifikat, sto ukljucuje:
			// - javni kljuc koji se vezuje za sertifikat
			// - podatke o vlasniku
			// - serijski broj sertifikata
			// - od kada do kada vazi sertifikat

			
			String serial = String.valueOf(sn);
			
			return new SubjectData(keyPairSubject.getPublic(), keyPairSubject.getPrivate(), builder.build(), serial,
					startDate, endDate);
		}

		catch (ParseException e) {

		}

		return null;

	}

	// izdavalac sertifikata
	// ISPRAVLJENO
	public IssuerData generateIssuerData(PrivateKey issuerKey, SubjectSoftware ss) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, "Security");
		builder.addRDN(BCStyle.O, ss.getSoftwareId());
		builder.addRDN(BCStyle.OU, ss.getCity());
		builder.addRDN(BCStyle.C, ss.getState());
		builder.addRDN(BCStyle.E, ss.getEmail());
		builder.addRDN(BCStyle.UID, ss.getId().toString());

		// Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
		// - privatni kljuc koji ce se koristiti da potpise sertifikat koji se
		// izdaje
		// - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}

}