package com.guardians.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guardians.dto.EnderecoComIdDTO;
import com.guardians.dto.EnderecoDTO;
import com.guardians.security.UserDetailsImp;
import com.guardians.security.util.SecurityUtil;
import com.guardians.services.EnderecoService;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoResource {
	
	@Autowired
	private EnderecoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EnderecoComIdDTO> findById(){
		UserDetailsImp user = SecurityUtil.authenticated();
		EnderecoComIdDTO dto = service.getById(user.getId());
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<EnderecoComIdDTO> update(@RequestBody EnderecoDTO endereco){
		UserDetailsImp user = SecurityUtil.authenticated();
		EnderecoComIdDTO dto = service.update(user.getId(), endereco);
		return ResponseEntity.ok(dto);
		
	}
}
