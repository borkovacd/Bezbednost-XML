package com.ftn.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.ftn.zuul.config.FilterZuul;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulServerApp {

//	@Bean
  //  public FilterZuul filter() {
   //   return new FilterZuul();
   // }
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApp.class, args);
	}

	

}
