package com.ftn.zuul;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

import com.netflix.discovery.DiscoveryClient;


@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulServerApp {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApp.class, args);
	}

	

}
