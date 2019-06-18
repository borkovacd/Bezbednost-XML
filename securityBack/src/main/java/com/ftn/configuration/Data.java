package com.ftn.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
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
import com.ftn.repository.CertificateRepository;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.SubjectSoftwareRepository;
import com.ftn.repository.UserRepository;
import com.ftn.service.RoleService;

@Component
public class Data implements ApplicationRunner {
	
	private KeyStore keyStore;
	private KeyStore keyStoreAgent;
	
	@Autowired
	private SubjectSoftwareRepository subSoftRep;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CertificateRepository certRepository;
	
	private KeyStoreReader keyStoreReader = new KeyStoreReader() ;
	private KeyStoreWriter keyStoreWriter = new KeyStoreWriter() ;


	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			keyStoreAgent = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} 
		
		File f = new File("./files/keystoreSecurity.p12");
		File f2 = new File("");

		String parent =  f2.getAbsoluteFile().getParent();
		String parent2 = f.getAbsoluteFile().getParent();

		File fAgent = new File(parent + "/agentBack/keystoreAgent.p12");
		
		String agentPath = parent + "\\agentBack\\keystoreAgent.p12";
		
		String mainBackPath = parent + "\\securityBack\\files\\keystoreSecurity.p12";
		
		String authPath = parent + "\\auth-service\\authKeystore.p12";

		String zuulPath = parent + "\\zuul-server\\zuulKeystore.p12";

		String eurekaPath = parent + "\\eureka-server\\eurekaKeystore.p12";
		 
		System.out.println("Ovo je agent path: " + agentPath);
		
		if(f.exists() && !f.isDirectory()) { 
		    System.out.println("POSTOJI");
		    
		    if(fAgent.exists()) {
		    	System.out.println("postoji agent keystore");
		    } else {
		    	System.out.println("nne postoji agent keystore");
		    }
		    
		    ArrayList<Certificate> lanacSertifikata = new ArrayList<Certificate>();
		    
		    ArrayList<Certificate> agentLanacSertifikata = new ArrayList<Certificate>();
		    
		    ArrayList<Certificate> mainLanacSertifikata = new ArrayList<Certificate>();
		    
		    try {
				lanacSertifikata = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore("./files/keystoreSecurity.p12").getCertificateChain("someString")));
				//agentLanacSertifikata = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore(agentPath).getCertificateChain("someString")));
				
			} catch (KeyStoreException e) {
				System.out.println("Eror se desio");
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
		    	
		    	cm.setRevoked(false);
		    	
		    	String serial = c.getSerialNumber().toString();
		    	
		    	cm.setSerialNumber(Integer.parseInt(serial));
		    	
		    	certRepository.save(cm);
		    	
		    }
		    
		    
		    if (keyStoreReader.getKeyStore(agentPath).getCertificateChain("someString2") == null ) {
				System.out.println("prazan je agent");
				
				// kreirati sertifikat za agenta
				
				String str = "someString"; 
				char[] password = str.toCharArray();
				
				//keyStoreAgent.load(agentPath, password);
				
				//
				PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("./files/keystoreSecurity.p12", str, str, str);
				
				//System.out.println("privatni kljuc je:" + privateKeyIssuer);
				
				ArrayList<Certificate> lanacSertifikata2 = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore("./files/keystoreSecurity.p12").getCertificateChain("someString")));
				
				PublicKey publicKey = lanacSertifikata.get(0).getPublicKey(); 
				
				SubjectData subjectData33 = generateSubjectData2(privateKeyIssuer, publicKey);
				
				IssuerData issuerData33 = generateIssuerData2(privateKeyIssuer, (X509Certificate) lanacSertifikata2.get(0));
				
				//keyStoreWriter.loadKeyStore(agentPath, password);
				
				CertificateGenerator cg = new CertificateGenerator();
				
				// izgenerisi sertifikat za subject-a od issuer-a
				X509Certificate cert = cg.generateCertificate(subjectData33, issuerData33);
				
				Certificate certificate = cert;
				
				String pass = "someString2";
				
				keyStoreAgent.load(new FileInputStream(agentPath), password);
				
				keyStoreAgent.setKeyEntry(pass, privateKeyIssuer, "someString".toCharArray(), new Certificate[] {certificate});
				
				keyStoreAgent.store(new FileOutputStream(agentPath), password);
				
				
			} else {
				System.out.println("nije prazan agent");
				
				try {
					agentLanacSertifikata = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore(agentPath).getCertificateChain("someString2")));
					
				} catch (KeyStoreException e) {
					System.out.println("Eror se desio");
					e.printStackTrace();
				}
				

			    for(int i=0; i<agentLanacSertifikata.size(); i++){
			    	
			    	X509Certificate c = (X509Certificate) agentLanacSertifikata.get(i);
			    	
			    	X500Name x500nameIssuer = new JcaX509CertificateHolder(c).getIssuer();
			    	
			    	X500Name x500nameSubject = new JcaX509CertificateHolder(c).getSubject();
			    	
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

			    	cm.setRevoked(false);
			    	cm.setSerialNumber(Integer.parseInt(serial));
			    	
			    	certRepository.save(cm);
			    	
			    	subSoftRep.save(subject);
				
			    }
			}
		    
		    
		    
		    // za glavni
		    
		    if (keyStoreReader.getKeyStore(mainBackPath).getCertificateChain("someString") == null ) {
				System.out.println("prazan glavni");
				
				// kreirati sertifikat za agenta
				
				String str = "someString"; 
				char[] password = str.toCharArray();
				
				//keyStoreAgent.load(agentPath, password);
				
				//
				PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("./files/keystoreSecurity.p12", str, str, str);
				
				//System.out.println("privatni kljuc je:" + privateKeyIssuer);
				
				ArrayList<Certificate> lanacSertifikata2 = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore("./files/keystoreSecurity.p12").getCertificateChain("someString")));
				
				PublicKey publicKey = lanacSertifikata.get(0).getPublicKey(); 
				
				SubjectData subjectData33 = generateSubjectData2Main(privateKeyIssuer, publicKey);
				
				IssuerData issuerData33 = generateIssuerData2(privateKeyIssuer, (X509Certificate) lanacSertifikata2.get(0));
				
				//keyStoreWriter.loadKeyStore(agentPath, password);
				
				CertificateGenerator cg = new CertificateGenerator();
				
				// izgenerisi sertifikat za subject-a od issuer-a
				X509Certificate cert = cg.generateCertificate(subjectData33, issuerData33);
				
				Certificate certificate = cert;
				
				String pass = "someString";
				
				keyStoreAgent.load(new FileInputStream(mainBackPath), password);
				
				keyStoreAgent.setKeyEntry(pass, privateKeyIssuer, "someString".toCharArray(), new Certificate[] {certificate});
				
				keyStoreAgent.store(new FileOutputStream(mainBackPath), password);
				
				
			} else {
				System.out.println("nije prazan glavni");
				
				try {
					mainLanacSertifikata = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore(mainBackPath).getCertificateChain("someString")));
					
				} catch (KeyStoreException e) {
					System.out.println("Eror se desio");
					e.printStackTrace();
				}
				

			    for(int i=0; i<mainLanacSertifikata.size(); i++){
			    	
			    	X509Certificate c = (X509Certificate) mainLanacSertifikata.get(i);
			    	
			    	X500Name x500nameIssuer = new JcaX509CertificateHolder(c).getIssuer();
			    	
			    	X500Name x500nameSubject = new JcaX509CertificateHolder(c).getSubject();
			    	
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
			    	

			    	cm.setRevoked(false);
			    	
			    	certRepository.save(cm);
			    	
				
			    }
			}
		    
		    
		    if (keyStoreReader.getKeyStore(authPath).getCertificateChain("someString") == null ) {
				System.out.println("prazan auth");
				
				// kreirati sertifikat za agenta
				
				String str = "someString"; 
				char[] password = str.toCharArray();
				
				//keyStoreAgent.load(agentPath, password);
				
				//
				PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("./files/keystoreSecurity.p12", str, str, str);
				
				//System.out.println("privatni kljuc je:" + privateKeyIssuer);
				
				ArrayList<Certificate> lanacSertifikata2 = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore("./files/keystoreSecurity.p12").getCertificateChain("someString")));
				
				PublicKey publicKey = lanacSertifikata.get(0).getPublicKey(); 
				
				SubjectData subjectData33 = generateSubjectData2Main(privateKeyIssuer, publicKey);
				
				IssuerData issuerData33 = generateIssuerData2(privateKeyIssuer, (X509Certificate) lanacSertifikata2.get(0));
				
				//keyStoreWriter.loadKeyStore(agentPath, password);
				
				CertificateGenerator cg = new CertificateGenerator();
				
				// izgenerisi sertifikat za subject-a od issuer-a
				X509Certificate cert = cg.generateCertificate(subjectData33, issuerData33);
				
				Certificate certificate = cert;
				
				String pass = "someString";
				
				keyStoreAgent.load(new FileInputStream(authPath), password);
				
				keyStoreAgent.setKeyEntry(pass, privateKeyIssuer, "someString".toCharArray(), new Certificate[] {certificate});
				
				keyStoreAgent.store(new FileOutputStream(authPath), password);
				
				
			} else {
				System.out.println("nije prazan auth");
			}
		    
		    if (keyStoreReader.getKeyStore(zuulPath).getCertificateChain("someString") == null ) {
				System.out.println("prazan zuul");
				
				// kreirati sertifikat za agenta
				
				String str = "someString"; 
				char[] password = str.toCharArray();
				
				//keyStoreAgent.load(agentPath, password);
				
				//
				PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("./files/keystoreSecurity.p12", str, str, str);
				
				//System.out.println("privatni kljuc je:" + privateKeyIssuer);
				
				ArrayList<Certificate> lanacSertifikata2 = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore("./files/keystoreSecurity.p12").getCertificateChain("someString")));
				
				PublicKey publicKey = lanacSertifikata.get(0).getPublicKey(); 
				
				SubjectData subjectData33 = generateSubjectData2Main(privateKeyIssuer, publicKey);
				
				IssuerData issuerData33 = generateIssuerData2(privateKeyIssuer, (X509Certificate) lanacSertifikata2.get(0));
				
				//keyStoreWriter.loadKeyStore(agentPath, password);
				
				CertificateGenerator cg = new CertificateGenerator();
				
				// izgenerisi sertifikat za subject-a od issuer-a
				X509Certificate cert = cg.generateCertificate(subjectData33, issuerData33);
				
				Certificate certificate = cert;
				
				String pass = "someString";
				
				keyStoreAgent.load(new FileInputStream(zuulPath), password);
				
				keyStoreAgent.setKeyEntry(pass, privateKeyIssuer, "someString".toCharArray(), new Certificate[] {certificate});
				
				keyStoreAgent.store(new FileOutputStream(zuulPath), password);
				
				
			} else {
				System.out.println("nije prazan zuul");
			}
		    
		    if (keyStoreReader.getKeyStore(eurekaPath).getCertificateChain("someString") == null ) {
				System.out.println("prazan eureka");
				
				// kreirati sertifikat za agenta
				
				String str = "someString"; 
				char[] password = str.toCharArray();
				
				//keyStoreAgent.load(agentPath, password);
				
				//
				PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("./files/keystoreSecurity.p12", str, str, str);
				
				//System.out.println("privatni kljuc je:" + privateKeyIssuer);
				
				ArrayList<Certificate> lanacSertifikata2 = new ArrayList<Certificate>(Arrays.asList(keyStoreReader.getKeyStore("./files/keystoreSecurity.p12").getCertificateChain("someString")));
				
				PublicKey publicKey = lanacSertifikata.get(0).getPublicKey(); 
				
				SubjectData subjectData33 = generateSubjectData2Main(privateKeyIssuer, publicKey);
				
				IssuerData issuerData33 = generateIssuerData2(privateKeyIssuer, (X509Certificate) lanacSertifikata2.get(0));
				
				//keyStoreWriter.loadKeyStore(agentPath, password);
				
				CertificateGenerator cg = new CertificateGenerator();
				
				// izgenerisi sertifikat za subject-a od issuer-a
				X509Certificate cert = cg.generateCertificate(subjectData33, issuerData33);
				
				Certificate certificate = cert;
				
				String pass = "someString";
				
				keyStoreAgent.load(new FileInputStream(eurekaPath), password);
				
				keyStoreAgent.setKeyEntry(pass, privateKeyIssuer, "someString".toCharArray(), new Certificate[] {certificate});
				
				keyStoreAgent.store(new FileOutputStream(eurekaPath), password);
				
				
			} else {
				System.out.println("nije prazan zuul");
			}
	}

}
	
	public SubjectData generateSubjectData2(PrivateKey privKey, PublicKey publKey)
	{
		try {
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			
			SubjectSoftware ss = subSoftRep.findByEmail("MTAgent@gmail.com");
			
			String sn = "1556994940";
			
			
		// oznaka softvera, drzava i email adresa
			builder.addRDN(BCStyle.CN, "AgentModuleCertificate");
			builder.addRDN(BCStyle.O, ss.getSoftwareId());
			builder.addRDN(BCStyle.OU, ss.getCity());
			builder.addRDN(BCStyle.C, ss.getState());
			builder.addRDN(BCStyle.EmailAddress, ss.getEmail());
			builder.addRDN(BCStyle.UID, ss.getId().toString());
						
			// datumi
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2019-06-05");
			Date endDate = iso8601Formater.parse("2020-06-05");
			
			System.out.println("uspeo");

			//Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
			
			return new SubjectData(publKey, privKey, builder.build(), sn, startDate, endDate);
		}
		
		catch(ParseException e)
		{
			
		}
		
		return null ;
		
	}
	
	public SubjectData generateSubjectData2Main(PrivateKey privKey, PublicKey publKey)
	{
		try {
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			
			SubjectSoftware ss = subSoftRep.findByEmail("localhost@gmail.com");
			
			String sn = "1886933942";
			
			
		// oznaka softvera, drzava i email adresa
			builder.addRDN(BCStyle.CN, "localhost");
			builder.addRDN(BCStyle.O, ss.getSoftwareId());
			builder.addRDN(BCStyle.OU, ss.getCity());
			builder.addRDN(BCStyle.C, ss.getState());
			builder.addRDN(BCStyle.EmailAddress, ss.getEmail());
			builder.addRDN(BCStyle.UID, ss.getId().toString());
						
			// datumi
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2019-06-05");
			Date endDate = iso8601Formater.parse("2020-06-05");
			
			System.out.println("uspeo");

			//Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
			
			return new SubjectData(publKey, privKey, builder.build(), sn, startDate, endDate);
		}
		
		catch(ParseException e)
		{
			
		}
		
		return null ;
		
	}
	
	// izdavalac sertifikata za agenta
	// ISPRAVLJENO
	public IssuerData generateIssuerData2(PrivateKey issuerKey, X509Certificate certificate) throws CertificateEncodingException 
	{
		X500Name x500nameIssuer = new JcaX509CertificateHolder(certificate).getIssuer();
		
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, x500nameIssuer.getRDNs(BCStyle.CN)[0].getFirst().getValue());
		builder.addRDN(BCStyle.O, x500nameIssuer.getRDNs(BCStyle.O)[0].getFirst().getValue());
		builder.addRDN(BCStyle.OU, x500nameIssuer.getRDNs(BCStyle.OU)[0].getFirst().getValue());
		builder.addRDN(BCStyle.C, x500nameIssuer.getRDNs(BCStyle.C)[0].getFirst().getValue());
		builder.addRDN(BCStyle.E, x500nameIssuer.getRDNs(BCStyle.EmailAddress)[0].getFirst().getValue());
		//builder.addRDN(BCStyle.UID, x500nameIssuer.getRDNs(BCStyle.UID)[0]);

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}
	
}
