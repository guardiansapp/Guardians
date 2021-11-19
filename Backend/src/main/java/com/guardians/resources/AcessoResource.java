package com.guardians.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guardians.dto.AcessoDTO;
import com.guardians.security.UserDetailsImp;
import com.guardians.security.util.SecurityUtil;
import com.guardians.services.AcessoService;

@RestController
@RequestMapping(value = "/acessos")
public class AcessoResource {

	@Autowired
	public AcessoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<AcessoDTO> getById() {
		UserDetailsImp user = SecurityUtil.authenticated();
		AcessoDTO dto = service.getById(user.getId());
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/nomeusuario")
	public ResponseEntity<AcessoDTO> updateNomeUsuario(@RequestBody AcessoDTO acesso){
		UserDetailsImp user = SecurityUtil.authenticated();
		return ResponseEntity.ok(service.updateNomeUsuario(acesso, user.getId()));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/senha")
	public ResponseEntity<AcessoDTO> updateSenha(@RequestParam String senha){
		UserDetailsImp user = SecurityUtil.authenticated();
		return ResponseEntity.ok(service.updateSenha(senha, user.getId()));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/nomeusuario")
	public ResponseEntity<AcessoDTO> getByUsername(@RequestParam String username){
		return ResponseEntity.ok(service.getByNomeUsuario(username));
	}
	
}
