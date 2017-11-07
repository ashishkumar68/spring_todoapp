package com.todoapp.controller.api.auth;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value="/api/")
public class TokenController {
	
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public @ResponseBody String generateTokenAction(String username, String password) {
		return Jwts
				.builder()
				.setSubject("Tom")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 60000))
				.signWith(SignatureAlgorithm.HS256, "jvbjvbsvsavvfverq").compact();
	}
	
	@RequestMapping(value="/auth/verification", method=RequestMethod.GET)
	public @ResponseBody Claims validateTokenAction(String jwt) {
		Claims claims = Jwts.parser()         
			       .setSigningKey("jvbjvbsvsavvfverq")
			       .parseClaimsJws(jwt).getBody();
		return claims;
	}
}
