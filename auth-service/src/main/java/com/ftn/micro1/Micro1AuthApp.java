package com.ftn.micro1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Micro1AuthApp {
	
	public static void main(String[] args) {
		SpringApplication.run(Micro1AuthApp.class, args);
	}

}
