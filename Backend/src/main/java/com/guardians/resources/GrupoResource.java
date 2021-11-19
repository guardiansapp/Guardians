package com.guardians.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guardians.dto.GrupoDTO;
import com.guardians.dto.GrupoListarDTO;
import com.guardians.dto.GrupoSemIdDTO;
import com.guardians.security.UserDetailsImp;
import com.guardians.security.util.SecurityUtil;
import com.guardians.services.GrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoResource {

	@Autowired
	private GrupoService service;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GrupoDTO> save (@RequestBody GrupoSemIdDTO grupo){
		return ResponseEntity.ok(service.save(grupo));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<GrupoDTO> update (@RequestBody GrupoSemIdDTO grupo, @PathVariable Long id){
		return ResponseEntity.ok(service.update(id, grupo));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<GrupoDTO> getById(@PathVariable Long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/usuarios")
	public ResponseEntity<List<GrupoListarDTO>> getAllByUser(){
		UserDetailsImp user = SecurityUtil.authenticated();
		return ResponseEntity.ok(service.findGruposByUSer(user.getId()));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "entrar/{grupoId}")
	public ResponseEntity<GrupoDTO> joinGroup(@PathVariable Long grupoId, @RequestParam Long userId){
		return ResponseEntity.ok(service.addUsuario(userId, grupoId));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "sair/{grupoId}")
	public ResponseEntity<GrupoDTO> leaveGroup(@PathVariable Long grupoId){
		UserDetailsImp user = SecurityUtil.authenticated();
		return ResponseEntity.ok(service.removeUsuario(user.getId(), grupoId));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
