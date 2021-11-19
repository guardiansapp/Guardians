package com.guardians.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardians.dto.CredentialsDTO;
import com.guardians.security.UserDetailsImp;
import com.guardians.security.util.SecurityUtil;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;	
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response
												) throws AuthenticationException {		
		
		try {
			
		//Instanciando um credentialsDTO a partir da requisição de login
		CredentialsDTO cred = new ObjectMapper()
				.readValue(request.getInputStream(), CredentialsDTO.class);

		//Instancia o token para autenticação
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				cred.getUsername(), 
				cred.getPassword(),
				new ArrayList<>()
		);
		
		//Autentica o usuário
		Authentication auth = this.authenticationManager.authenticate(authToken);
		
		return auth;
		
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,
											Authentication authResult
											) throws IOException, ServletException {
		
		//authResult = retorno do attemptAuthentication()
		String username = ((UserDetailsImp) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		
		//Adiciona o token ao corpo da resposta
		String resp = SecurityUtil.tokenJson(token, jwtUtil);
		
		response.setStatus(200);
		response.setContentType("application/json");
		response.getWriter().append(resp);
	}


	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, 
											  HttpServletResponse response,
											  AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(401);
		response.setContentType("application/json");
		
		Map<String, String> respJson = new HashMap<>();
		respJson.put("timestamp", String.valueOf(new Date().getTime()));
		respJson.put("status", "401");
		respJson.put("error", "Unauthenticated");
		respJson.put("message", "Username or password is invalid");
		respJson.put("path", "/login");
		
		String resp = new ObjectMapper().writeValueAsString(respJson);
	
		response.getWriter().append(resp);
	}	
	
	
	
}
