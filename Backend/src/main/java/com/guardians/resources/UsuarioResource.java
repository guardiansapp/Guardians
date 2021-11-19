package com.guardians.resources;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guardians.dto.NotificacaoTokenDTO;
import com.guardians.dto.UsuarioDTO;
import com.guardians.dto.UsuarioInfoPessoalDTO;
import com.guardians.dto.UsuarioSemIdDTO;
import com.guardians.security.UserDetailsImp;
import com.guardians.security.util.SecurityUtil;
import com.guardians.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<UsuarioDTO> findById() {
		UserDetailsImp user = SecurityUtil.authenticated();
		UsuarioDTO dto = service.findById(user.getId());
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioSemIdDTO usuario){
		UsuarioDTO dto = service.save(usuario);
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<UsuarioInfoPessoalDTO> update(@RequestBody UsuarioInfoPessoalDTO usuario){
		UserDetailsImp user = SecurityUtil.authenticated();
		UsuarioInfoPessoalDTO dto = service.update(usuario, user.getId());
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<String> delete() {
		UserDetailsImp user = SecurityUtil.authenticated();
		service.delete(user.getId());
		return ResponseEntity.ok("Usuário deletado");
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/telefones")
	public ResponseEntity<Set<String>> AddPhone(@RequestParam String telefone){
		UserDetailsImp user = SecurityUtil.authenticated();
		Set<String> telefones = service.addTelefone(user.getId(), telefone);
		return ResponseEntity.ok(telefones);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/telefones")
	public ResponseEntity<Set<String>> removePhone(@RequestParam String telefone){
		UserDetailsImp user = SecurityUtil.authenticated();
		Set<String> telefones = service.removeTelefone(user.getId(), telefone);
		return ResponseEntity.ok(telefones);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/notificationtoken")
	public ResponseEntity<UsuarioDTO> setNotificacaoToken(@RequestBody NotificacaoTokenDTO token){
		UserDetailsImp user = SecurityUtil.authenticated();
		UsuarioDTO usuario = service.setNotificationToken(token.getToken(), user.getId());
		return ResponseEntity.ok(usuario);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/notificationtoken")
	public ResponseEntity<UsuarioDTO> clearNotificacaoToken(){
		UserDetailsImp user = SecurityUtil.authenticated();
		UsuarioDTO usuario = service.clearNotificationToken(user.getId());
		return ResponseEntity.ok(usuario);
	}
	
}