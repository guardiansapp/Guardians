package com.guardians.security.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardians.security.UserDetailsImp;
import com.guardians.security.jwt.JWTUtil;

@Component
public class SecurityUtil {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public static UserDetailsImp authenticated() {
		try {
			UserDetailsImp user = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user;
		}catch(Exception e) {
			return null;
		}
	}
	
	//Retorna um json para enviar como resposta quando o token jwt for solicitado
	public static String tokenJson(String token, JWTUtil util) {
		
		Map<String, String> respJson = new HashMap<>();
		respJson.put("Authorization", "Bearer " + token);
		respJson.put("expiration", String.valueOf(util.getExpiration()));
		
		try {
			String resp = new ObjectMapper().writeValueAsString(respJson);
			
			return resp;
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	//Retorna um novo token
	public ResponseEntity<String> refreshToken(HttpServletResponse response){
		UserDetailsImp user = SecurityUtil.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		
		response.setContentType("application/json");
		
		return ResponseEntity.ok(tokenJson(token, jwtUtil));
	}
	
}

