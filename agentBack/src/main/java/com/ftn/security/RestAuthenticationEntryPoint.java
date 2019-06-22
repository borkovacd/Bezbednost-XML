package com.ftn.security;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;



@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{
	private static final Logger log = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

	
	//Metoda koja se izvrsava ukoliko neautorizovani korisnik pokusa da pristupi zasticenom REST servisu
		//Metoda vraca 401 Unauthorized response, ukoliko postoji Login Page u aplikaciji, pozeljno je da se korisnik redirektuje na tu stranicu
	    @Override
	    public void commence(HttpServletRequest request,
	                         HttpServletResponse response,
	                         AuthenticationException authException) throws IOException {
	     //   log.warn(LoggerUtils.getSMarker(),"SECURITY_EVENT Unauthorised access attempt to {} from  adress{}",request.getRequestURL(),request.getRemoteAddr());

	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	    }
}
