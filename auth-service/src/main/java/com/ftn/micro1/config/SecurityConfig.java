package com.ftn.micro1.config;

import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ftn.micro1.security.CheckTokenFilter;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@Configuration
@EnableWebSecurity 	
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	

@Bean
public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
    DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
    System.setProperty("javax.net.ssl.keyStore", "client.p12");
    System.setProperty("javax.net.ssl.keyStorePassword", "client");
    System.setProperty("javax.net.ssl.trustStore", "client.p12");
    System.setProperty("javax.net.ssl.trustStorePassword", "client");
    EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
    builder.withClientName("client");
    builder.withSystemSSLConfiguration();
    builder.withMaxTotalConnections(10);
    builder.withMaxConnectionsPerHost(10);
    args.setEurekaJerseyClient(builder.build());
    return args;
}


	@Bean
	public CheckTokenFilter authenticationFilter() throws Exception {
	CheckTokenFilter authenticationFilter = new CheckTokenFilter();

    authenticationFilter.setAuthenticationManager(authenticationManagerBean());
    return authenticationFilter;
	}
	
	 @Bean
	 @Override
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	 }
	 
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    

	

	@Override
  	protected void configure(HttpSecurity http) throws Exception {
		
		http
				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()
				.antMatchers("/test/**").permitAll().and()
				
				
				.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
				

				
		http		.csrf().disable();
		
	}
	
}
