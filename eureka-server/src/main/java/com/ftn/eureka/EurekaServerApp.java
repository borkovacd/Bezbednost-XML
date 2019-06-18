package com.ftn.eureka;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp {
	
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApp.class, args);
	}
	
	/*
	@Bean
	public CloseableHttpClient client() throws Throwable {
		
		File keystore = ResourceUtils.getFile("eureka.p12");
		File truststore = ResourceUtils.getFile("eureka.p12");
		
		InputStream readStream = new DataInputStream(new FileInputStream(keystore));
		InputStream readStream2 = new DataInputStream(new FileInputStream(truststore));
		
		final KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(readStream, "eureka".toCharArray());
		
		try {
			readStream.close();
		} catch (final IOException e) {
			System.out.println("IOException during loading keystore");
		} final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(ks, "eureka".toCharArray());
		
		
		final KeyStore ts = KeyStore.getInstance("PKCS12");
		ks.load(readStream2, "eureka".toCharArray());
		
		try {
			readStream2.close();
		} catch (final IOException e) {
			System.out.println("IOException during loading truststore");
		} final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(ts);
		
		
		final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(ts, new TrustSelfSignedStrategy())
				.loadKeyMaterial(ks, "eureka".toCharArray()).build();
		
		final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		
		httpClientBuilder.setSslcontext(sslContext);
		
		return httpClientBuilder.build();
				
		
	}
	*/
	
	
}


