package com.ftn;

import java.security.Security;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration

@SpringBootApplication
/*
 * Ukljucivanje podrske za upravljanje transakcijama
 * pomocu @EnableTransactionManagement anotacije
 */
@EnableTransactionManagement
@EnableJpaRepositories
public class MegaTravelApp {

	public static void main(String[] args) {
		SpringApplication.run(MegaTravelApp.class, args);
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
}
