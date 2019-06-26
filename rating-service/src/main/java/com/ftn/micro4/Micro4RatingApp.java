package com.ftn.micro4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class Micro4RatingApp {
	
	public static void main(String[] args) {
		SpringApplication.run(Micro4RatingApp.class, args);
	}

}
