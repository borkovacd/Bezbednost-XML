package com.ftn.micro2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class Micro2AccApp 

{

		@Bean
		public RestTemplate template() throws Exception{
		RestTemplate template = new RestTemplate();
		return template;
		}

		@Bean
		public void checkLogged()
		{
			
			SecurityContextHolder sc;
	
		}
		
	public static void main(String[] args) {
		SpringApplication.run(Micro2AccApp.class, args);
	}
}
