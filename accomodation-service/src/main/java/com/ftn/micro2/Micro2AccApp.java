package com.ftn.micro2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Micro2AccApp 
{
	public static void main(String[] args) {
		SpringApplication.run(Micro2AccApp.class, args);
	}
}