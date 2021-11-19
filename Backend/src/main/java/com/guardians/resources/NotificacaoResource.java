package com.guardians.resources;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guardians.dto.NotificacaoDTO;
import com.guardians.dto.NotificacaoGrupoDTO;
import com.guardians.dto.NotificacaoGuardiaoDTO;
import com.guardians.dto.NotificacaoParaSalvarDTO;
import com.guardians.security.UserDetailsImp;
import com.guardians.security.util.SecurityUtil;
import com.guardians.services.NotificacaoService;

@RestController
@RequestMapping(value = "/notificacoes")
public class NotificacaoResource {

	@Autowired
	private NotificacaoService service;

	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public ResponseEntity<NotificacaoDTO> getById(@PathVariable Long id){
		NotificacaoDTO dto = service.getById(id);
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<NotificacaoDTO> save(@RequestBody NotificacaoParaSalvarDTO notificacao){
		UserDetailsImp user = SecurityUtil.authenticated();		
		NotificacaoDTO dto = service.save(user.getId(), notificacao);
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/confirma/grupos")
	public ResponseEntity<Void> confirmGrupo(@RequestParam Long notificacaoId, @RequestParam Long grupoId){
		UserDetailsImp user = SecurityUtil.authenticated();
		service.confirmGrupo(grupoId, notificacaoId, user.getId());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/confirma/guardioes")
	public ResponseEntity<Void> confirmGuardiao(@RequestParam Long notificacaoId){
		service.confirmGuardiao(notificacaoId);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/pendentes/grupos")
	public ResponseEntity<Set<NotificacaoGrupoDTO>> getPendingGrupos(){
		UserDetailsImp user = SecurityUtil.authenticated();
		Set<NotificacaoGrupoDTO> pending = service.getPendingGrupos(user.getId());
		return ResponseEntity.ok(pending);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pendentes/guardioes")
	public ResponseEntity<List<NotificacaoGuardiaoDTO>> getPendingGuardioes(){
		UserDetailsImp user = SecurityUtil.authenticated();
		List<NotificacaoGuardiaoDTO> pending = service.getPendingGuardioes(user.getId());
		return ResponseEntity.ok(pending);
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value="/all/guardioes")
	public ResponseEntity<List<NotificacaoGuardiaoDTO>> getAllFromGuardioes(){
		UserDetailsImp user = SecurityUtil.authenticated();
		List<NotificacaoGuardiaoDTO> all = service.getGuardioes(user.getId());
		return ResponseEntity.ok(all);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/all/grupos")
	public ResponseEntity<List<NotificacaoGrupoDTO>> getAllFromGrupos(){
		UserDetailsImp user = SecurityUtil.authenticated();
		List<NotificacaoGrupoDTO> all = service.getGrupos(user.getId());
		return ResponseEntity.ok(all);
	}
	
}
