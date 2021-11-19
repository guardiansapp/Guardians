package com.guardians.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guardians.security.util.SecurityUtil;

@RestController
@RequestMapping(value = "/login")
public class SecurityResource {

	@Autowired
	private SecurityUtil util;
	
	@RequestMapping(method = RequestMethod.POST, value = "/refresh")
	public ResponseEntity<String> refreshToken(HttpServletResponse response){
		return util.refreshToken(response);
	}
	
}
