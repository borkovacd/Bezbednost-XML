package com.ftn.micro3.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class CheckTokenFilter extends UsernamePasswordAuthenticationFilter {
	
	@Autowired
	private TokenUtils tokenUtils;

	public CheckTokenFilter() {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		RestTemplate restTemplate = new RestTemplate();
	    
		String token = httpRequest.getHeader("token");
		System.out.println("ovo je token acc " + token);
		
		if(token != null) {

			token = token.substring(1,token.length()-1).toString();
			
			Map<String, String> params = new HashMap<>();
			params.put("token", token);
			
			//HttpHeaders headers = new HttpHeaders();
			//headers.set("token", token);
			
			String as = restTemplate.getForObject("https://localhost:8086/user/getUsername/{token}", String.class, params);

			String auth2 =  restTemplate.getForObject("https://localhost:8086/user/getPermissions/{token}", String.class, params);
			
			System.out.println("Dobio sam username: " + as);
			
			Set<SimpleGrantedAuthority> author = new HashSet<>();
			
			if(as!= "" && auth2!="") {
				
				String[] g = auth2.split(",");
				
				for(String a : g) {
					System.out.println("str: "+ a);
					author.add(new SimpleGrantedAuthority(a));
				}
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(as,  null, author);
			
			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
			SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} else {
			System.out.println("Token je null");
		}
		
		chain.doFilter(request, response);
	}
	
}
