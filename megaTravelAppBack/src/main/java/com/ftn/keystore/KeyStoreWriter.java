package com.ftn.keystore;

import java.io.FileInputStream;

import java.security.cert.X509Certificate;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;

public class KeyStoreWriter {
	//KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
	//Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
	// - Sertifikati koji ukljucuju javni kljuc
	// - Privatni kljucevi
	// - Tajni kljucevi, koji se koriste u simetricnima siframa
	private KeyStore keyStore;
	
	public KeyStoreWriter() {
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}
	
	// otvaranje postojeceg keyStore (ukoliko ne postoji, kreiraj novi)
	// Metoda kao parametar prima samo password, a fileName je zakucan na "./files/keystore.jks"
	public void loadKeyStore(char[] password) 
	{
		System.out.println("DDDDD");
		String fileName = "./files/keystore.jks";
		
				try {
					keyStore.load(new FileInputStream(fileName), password);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CertificateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("pozz");
			
	}
	
	// cuvanje postojeceg keyStore, za zadati password
	// Metoda kao parametar prima samo password, a fileName je zakucan na "./files/keystore.jks"
	public void saveKeyStore(char[] password) 
	{
		String fileName = "./files/keystore.jks";
		try {
			keyStore.store(new FileOutputStream(fileName), password);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// upis u otvoreni keyStore
	public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) 
	{
		try {
			keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] {certificate});
			
			
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
	
	public void write2(String alias, PrivateKey privateKey, char[] password, Certificate[] chain) 
	{
		try {
			keyStore.setKeyEntry(alias, privateKey, password, chain);
		
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
}
