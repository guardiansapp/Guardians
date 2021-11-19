package com.guardians.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guardians.dto.ConfiguracaoComIdDTO;
import com.guardians.dto.ConfiguracaoDTO;
import com.guardians.security.UserDetailsImp;
import com.guardians.security.util.SecurityUtil;
import com.guardians.services.ConfiguracaoService;

@RestController
@RequestMapping(value = "/configuracoes")
public class ConfiguracaoResource {

	@Autowired
	private ConfiguracaoService service;
	
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<ConfiguracaoComIdDTO> update(@RequestBody ConfiguracaoDTO config){
		UserDetailsImp user = SecurityUtil.authenticated();
		ConfiguracaoComIdDTO dto = service.update(user.getId(), config);
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ConfiguracaoComIdDTO> getById(){
		UserDetailsImp user = SecurityUtil.authenticated();
		ConfiguracaoComIdDTO dto = service.getById(user.getId());
		return ResponseEntity.ok(dto);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/grupospermitidos")
	public ResponseEntity<ConfiguracaoDTO> addGrupo(@RequestParam Long grupo){
		UserDetailsImp user = SecurityUtil.authenticated();
		ConfiguracaoDTO dto = service.addGrupo(grupo, user.getId());
		return ResponseEntity.ok(dto);
	}

	@RequestMapping(method = RequestMethod.DELETE, value="/grupospermitidos")
	public ResponseEntity<ConfiguracaoDTO> removeGrupo(@RequestParam Long grupo){
		UserDetailsImp user = SecurityUtil.authenticated();
		ConfiguracaoDTO dto = service.removeGrupo(grupo, user.getId());
		return ResponseEntity.ok(dto);
	}

}
