package com.guardians.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	@Getter
	private Long expiration;
	
	public String generateToken(String username) {
		String jwtToken = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();

		return jwtToken;
	}

	public boolean tokenValido(String token) {
		//Armazena as reemvidicações do token
		//(usuario e tempo de expiração)
		
		Claims claims = getClaims(token);
		
		if(claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if(username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch(Exception e) {
			return null;
		}
		
	}

	public String getUsername(String token) {
		
		Claims claims = getClaims(token);
		
		if(claims != null) {
			return claims.getSubject();
		}
		
		return null;
	}
	
}
