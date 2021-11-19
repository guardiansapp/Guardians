package com.guardians.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guardians.dto.ConfiguracaoComIdDTO;
import com.guardians.dto.ConfiguracaoDTO;
import com.guardians.model.Configuracao;
import com.guardians.model.Grupo;
import com.guardians.model.GruposPermitidos;
import com.guardians.model.Usuario;
import com.guardians.model.PKs.GruposPermitidosPK;
import com.guardians.repositories.ConfiguracaoRepository;
import com.guardians.repositories.GruposPermitidosRepository;
import com.guardians.repositories.GrupoRepository;
import com.guardians.repositories.UsuarioRepository;
import com.guardians.services.exceptions.DataIntegrityException;
import com.guardians.services.exceptions.ObjectNotFoundException;

@Service
public class ConfiguracaoService {

	@Autowired
	private ConfiguracaoRepository repository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GruposPermitidosRepository grupoNotificacaoRepository;
	
	@Transactional(readOnly = false, rollbackFor = ObjectNotFoundException.class)
	public ConfiguracaoComIdDTO update(Long id, ConfiguracaoDTO config) {
		
		Configuracao configuracao = safeGetById(id);
		
		configuracao.setEnderecoVisivel(
				(config.getEnderecoVisivel() != null)
					? config.getEnderecoVisivel() 
					: configuracao.getEnderecoVisivel()
				);
		
		configuracao.setIdadeVisivel(
				(config.getIdadeVisivel() != null)
					? config.getIdadeVisivel() 
					: configuracao.getIdadeVisivel()
				);
		
		configuracao.setTelefoneVisivel(
				(config.getTelefoneVisivel() != null)
					? config.getTelefoneVisivel() 
					: configuracao.getTelefoneVisivel()
				);
		
		configuracao.setPreMensagem(
				(config.getPreMensagem() != null)
					? config.getPreMensagem()
					: configuracao.getPreMensagem()
				);	
		
		configuracao.setEnviarParaGrupos(
				(config.getEnviarParaGrupos() != null)
					? config.getEnviarParaGrupos()
					: configuracao.getEnviarParaGrupos()
				);	
		
		configuracao.setCompartilharLocalizacao(
				(config.getCompartilharLocalizacao() != null)
					? config.getCompartilharLocalizacao()
					: configuracao.getCompartilharLocalizacao()
				);	
	
		repository.save(configuracao);
		
		return new ConfiguracaoComIdDTO(configuracao);
	}
	
	@Transactional(readOnly = true)
	public ConfiguracaoComIdDTO getById(Long id) {
		
		Configuracao config = safeGetById(id);
		return new ConfiguracaoComIdDTO(config);
	}
	
	public ConfiguracaoDTO addGrupo(Long grupoId, Long usuarioId) {
		
		Grupo grupo = safeGetGrupoById(grupoId);
		Configuracao configuracao = safeGetById(usuarioId);
		Usuario usuario = safeGetUsuarioById(usuarioId);
		
		if(!grupo.getUsuarios().contains(usuario)) 
			throw new DataIntegrityException("O usuário não pertence a o grupo especificado.");
		
		GruposPermitidos obj = new GruposPermitidos(new GruposPermitidosPK(configuracao, grupo));
		
		if(configuracao.getGrupoNotificacoes().contains(obj))
			throw new DataIntegrityException("O usuário já habilitou o envio de notificações para esse grupo");
		
		grupoNotificacaoRepository.save(obj);
		configuracao.getGrupoNotificacoes().add(obj);
		
		return new ConfiguracaoDTO(repository.save(configuracao));
	}

	public ConfiguracaoDTO removeGrupo(Long grupoId, Long usuarioId) {
		
		Grupo grupo = safeGetGrupoById(grupoId);
		Configuracao configuracao = safeGetById(usuarioId);
				
		GruposPermitidos obj = new GruposPermitidos(new GruposPermitidosPK(configuracao, grupo));
		
		if(!configuracao.getGrupoNotificacoes().contains(obj))
			throw new ObjectNotFoundException("O usuário não está com esse grupo habilitado.");
		
		configuracao.getGrupoNotificacoes().remove(obj);
		
		Configuracao configObj = repository.save(configuracao);
		grupoNotificacaoRepository.delete(obj);
		
		return new ConfiguracaoDTO(configObj);
	}
	
	private Configuracao safeGetById(Long id) {
		return repository.findById(id).orElseThrow(
			() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Configuracao.class.getName())
		);
	}
	
	private Usuario safeGetUsuarioById(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
			() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Configuracao.class.getName())
		);
	}
	
	private Grupo safeGetGrupoById(Long id) {
		return grupoRepository.findById(id).orElseThrow(
			() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Grupo.class.getName())
		);
	}
	
}
