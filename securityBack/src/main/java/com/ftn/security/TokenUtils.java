package com.ftn.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ftn.controller.SecurityAdminControler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {
	
	private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

	@Value("thesecretkeyforthetoken6526")
	private String SIGNING_KEY;
	
	@Value("18000")
	private Long expiration;

	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
	}
	
	public long getExpiredIn() {
		return expiration;
	}
	
	private Claims getAllClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(SIGNING_KEY)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("TOK_ERR");
		}
		return claims;
	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
			log.error("TOK_ERR");
		}
		return username;
	}

}
