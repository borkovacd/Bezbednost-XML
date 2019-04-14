package com.ftn.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ftn.keystore.KeyStoreReader;
import com.ftn.keystore.KeyStoreWriter;
import com.ftn.model.CertificateModel;
import com.ftn.model.IssuerData;
import com.ftn.model.SubjectData;
import com.ftn.model.SubjectSoftware;
import com.ftn.model.User;
import com.ftn.repository.CertificateRepository;
import com.ftn.repository.SubjectSoftwareRepository;
import com.ftn.repository.UserRepository;


// pri pokretanju treba namestiti podatak o root-u odnosno issuer-u i o divizijama
// tj. subjectima  i u keystore inicijalno da bude privatni kljuc issuer-a, i njegov
// sertifikat(koji je sam sebi dodelio)

@Component
public class Data implements ApplicationRunner {
	
	private KeyStore keyStore;
	
	@Autowired
	private SubjectSoftwareRepository subSoftRep;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CertificateRepository certRepository;
	
	private KeyStoreReader keyStoreReader = new KeyStoreReader() ;
	
	public Data() {
		Security.addProvider(new BouncyCastleProvider());
	}
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		

		loadSubjectSoftware();
		loadUser();
		
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		
		File f = new File("./files/keystore.jks");
		if(f.exists() && !f.isDirectory()) { 
		    System.out.println("POSTOJI");
		    
		    ArrayList<Certificate> lanacSertifikata = new ArrayList<Certificate>();
		    
		    try {
				lanacSertifikata = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore().getCertificateChain("alias1")));
				
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}
		    
		    for(int i=0; i<lanacSertifikata.size(); i++){
		    	
		    	X509Certificate c = (X509Certificate) lanacSertifikata.get(i);
		    	
		    	System.out.println("Issuer\n");
		    	System.out.println(c.getIssuerDN().getName());
		    	System.out.println("\n\n");
		    	
		    	
		    	System.out.println("Subject\n");
		    	System.out.println(c.getSubjectX500Principal().getName());
		    	System.out.println("\n\n");
		    	
		    	System.out.println(c.getNotAfter());
		    	System.out.println(c.getNotBefore());
		    	
		    	
		    	X500Name x500nameIssuer = new JcaX509CertificateHolder(c).getSubject();
		    	
		    	X500Name x500nameSubject = new JcaX509CertificateHolder(c).getIssuer();
		    	
		    	RDN emailIss = x500nameIssuer.getRDNs(BCStyle.EmailAddress)[0];
		    	
		    	String email_Iss2 =  emailIss.getFirst().getValue().toString();
		    	
		    	
		    	RDN emailSub = x500nameSubject.getRDNs(BCStyle.EmailAddress)[0];
		    	
		    	String email_Sub2 =  emailSub.getFirst().getValue().toString();
		    	
		    	

		    	SubjectSoftware issuer = subSoftRep.findByEmail(email_Iss2);
		    	SubjectSoftware subject = subSoftRep.findByEmail(email_Sub2);
		    	
		    	CertificateModel cm = new CertificateModel();
		    	
		    	cm.setIssuerSoft(issuer);
		    	cm.setSubSoft(subject);
		    	
		    	cm.setStartDate(c.getNotBefore());
		    	cm.setEndDate(c.getNotAfter());
		    	
		    	String serial = c.getSerialNumber().toString();
		    	
		    	cm.setSerialNumber(Integer.parseInt(serial));
		    	
		    	certRepository.save(cm);
		    	
		    }
		    
		    
		} else {
			System.out.println("NE POSTOJI");
			String str = "someString"; 
			char[] password = str.toCharArray();
			
			keyStore.load(null, password);
			
			// kreiran keystore, inicijalno kreirati jedan sertifikat koji on sam sebi dodeljuje
			
			try {
				SubjectData subjectData = generateSubjectData();
				
				KeyPair keyPairIssuer = generateKeyPair();
				IssuerData issuerData = generateIssuerData(keyPairIssuer.getPrivate());
			    
				//Generise se sertifikat za subjekta, potpisan od strane issuer-a
				CertificateGenerator cg = new CertificateGenerator();
				
				// izgenerisi sertifikat za subject-a od issuer-a
				X509Certificate cert = cg.generateCertificate(subjectData, issuerData);
				
				Certificate certificate = cert;
				
				String alias = "alias1";
				
				keyStore.setKeyEntry(alias, issuerData.getPrivateKey(), password, new Certificate[] {certificate});
				
				CertificateModel cm = new CertificateModel();
				
				SubjectSoftware ss = subSoftRep.findByEmail("MTRoot@gmail.com");
				
				cm.setSerialNumber(Integer.parseInt(subjectData.getSerialNumber()));
				
				cm.setStartDate(subjectData.getStartDate());
				cm.setEndDate(subjectData.getEndDate());
				
				// root sam sebi izdaje sertifikat
				cm.setIssuerSoft(ss);
				cm.setSubSoft(ss);
				
				cm.setRevoked(false);
				
				certRepository.save(cm);
				
				System.out.println(keyStore.size());
				keyStore.store(new FileOutputStream("./files/keystore.jks"), password);
				
				
				System.out.println("\n===== Podaci o izdavacu sertifikata =====");
				System.out.println(cert.getIssuerX500Principal().getName());
				System.out.println("\n===== Podaci o vlasniku sertifikata =====");
				System.out.println(cert.getSubjectX500Principal().getName());
				System.out.println("\n===== Sertifikat =====");
				System.out.println("-------------------------------------------------------");
				System.out.println(cert);
				System.out.println("-------------------------------------------------------");
				
				//Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom javnog kljuca izdavaoca
				cert.verify(keyPairIssuer.getPublic());
				System.out.println("\nValidacija uspesna :)");
				
				//Ovde se desava exception, jer se validacija vrsi putem drugog kljuca
				KeyPair anotherPair = generateKeyPair();
				cert.verify(anotherPair.getPublic());
			} catch(CertificateException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			} catch (SignatureException e) {
				System.out.println("\nValidacija neuspesna :(");
				e.printStackTrace();
			}
		}
		
		
	}
	private void loadUser(){
		
		User u0 = new User();
		u0.setEmail("MTRoot@gmail.com");
		u0.setPassword("rroott");
		userRepository.save(u0);
		
		User u1 = new User();
		u1.setEmail("MegaTravelLondon@gmail.com");
		u1.setPassword("lon");
		userRepository.save(u1);
		
		User u2 = new User();
		u2.setEmail("MegaTravelHongKong@gmail.com");
		u2.setPassword("hong");
		userRepository.save(u2);
		
		User u3 = new User();
		u3.setEmail("MegaTravelBoston@gmail.com");
		u3.setPassword("bost");
		userRepository.save(u3);
		
	}
	
	private void loadSubjectSoftware() {
		
		SubjectSoftware ss0 = new SubjectSoftware();
		
		ss0.setState("/");
		ss0.setCity("/");
		ss0.setSoftwareId("/");
		ss0.setEmail("MTRoot@gmail.com");
		ss0.setHasCert(true);

		subSoftRep.save(ss0);
		
		
		SubjectSoftware ss = new SubjectSoftware();
		
		ss.setState("Engleska");
		ss.setCity("London");
		ss.setSoftwareId("S1");
		ss.setEmail("MegaTravelLondon@gmail.com");
		
		subSoftRep.save(ss);
		
		SubjectSoftware ss2 = new SubjectSoftware();
		
		ss2.setState("Kina");
		ss2.setCity("Hong Kong");
		ss2.setSoftwareId("S2");
		ss2.setEmail("MegaTravelHongKong@gmail.com");
		
		subSoftRep.save(ss2);
		
		SubjectSoftware ss3 = new SubjectSoftware();
		
		ss3.setState("USA");
		ss3.setCity("Boston");
		ss3.setSoftwareId("S3");
		ss3.setEmail("MegaTravelBoston@gmail.com");
		
		subSoftRep.save(ss3);
				
	}


	// izdavac sertifikata
	private IssuerData generateIssuerData(PrivateKey issuerKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, "Security Admin");
	    builder.addRDN(BCStyle.O, "MegaTravel");
	    builder.addRDN(BCStyle.OU, "MegaTravelRoot");
	    builder.addRDN(BCStyle.C, "RS");
	    builder.addRDN(BCStyle.E, "MTRoot@gmail.com");
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, "654321");

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}

	private SubjectData generateSubjectData() {
		try {
			KeyPair keyPairSubject = generateKeyPair();
			
			//Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");
			
			//Serijski broj sertifikata
			String sn="1";
			
			//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, "SecurityAdmin");
		    builder.addRDN(BCStyle.O, "MegaTravel");
		    builder.addRDN(BCStyle.OU, "MegaTravelRoot");
		    builder.addRDN(BCStyle.C, "RS");
		    builder.addRDN(BCStyle.E, "MTRoot@gmail.com");
		    
		    //UID (USER ID) je ID korisnika
		    builder.addRDN(BCStyle.UID, "654321");
		    
		    //Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
		    return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private KeyPair generateKeyPair() {
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
	
	

}
