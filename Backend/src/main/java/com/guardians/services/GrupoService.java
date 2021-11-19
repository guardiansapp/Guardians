package com.guardians.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guardians.dto.GrupoDTO;
import com.guardians.dto.GrupoListarDTO;
import com.guardians.dto.GrupoSemIdDTO;
import com.guardians.model.Grupo;
import com.guardians.model.Usuario;
import com.guardians.repositories.GrupoRepository;
import com.guardians.repositories.UsuarioRepository;
import com.guardians.services.exceptions.DataIntegrityException;
import com.guardians.services.exceptions.ObjectNotFoundException;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional(readOnly = false, rollbackFor = {NullPointerException.class})
	public GrupoDTO save(GrupoSemIdDTO grupo) {
		Grupo obj = new Grupo(
				(grupo.getNome() != null) 
					? grupo.getNome() 
					: null
				);
		
		repository.save(obj);
		return new GrupoDTO(obj);
	}
	
	@Transactional(readOnly = false)
	public GrupoDTO update(Long id, GrupoSemIdDTO grupo) {
		
		Grupo obj = safeGetById(id);
		
		obj.setNome((grupo.getNome() != null)
				? grupo.getNome()
				: obj.getNome()
			);
		
		repository.save(obj);
		
		return new GrupoDTO(obj);
	}
	
	@Transactional(readOnly = false)
	public GrupoDTO addUsuario(Long usuarioId, Long grupoId) {
		
		Grupo grupo = safeGetById(grupoId);
		Usuario usuario = safeGetUsuarioById(usuarioId);
		
		if(grupo.getUsuarios().contains(usuario)) {
			throw new DataIntegrityException("Esse usuário já está no grupo.");
		}
		
		
		grupo.getUsuarios().add(usuario);
		
		repository.save(grupo);
		
		return new GrupoDTO(grupo);
		
	}
	
	public GrupoDTO removeUsuario(Long usuarioId, Long grupoId) {
		Grupo grupo = safeGetById(grupoId);
		Usuario usuario = safeGetUsuarioById(usuarioId);
		
		if(!grupo.getUsuarios().contains(usuario)) {
			throw new DataIntegrityException("Esse usuário não está no grupo.");
		}
		
		grupo.getUsuarios().remove(usuario);
		
		repository.save(grupo);
		
		return new GrupoDTO(grupo);
	}
	
	@Transactional(readOnly = true)
	public GrupoDTO findById(Long id) {
		Grupo obj = safeGetById(id);
		return new GrupoDTO(obj);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		Grupo obj = safeGetById(id);
		obj.getUsuarios().clear();
		
		repository.save(obj); //Remove todos usuários do grupo
		repository.delete(obj); //Deleta o grupo
	}
	
	private Grupo safeGetById(Long id) {
		Grupo grupo = repository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + id + ", Tipo: " + Grupo.class.getName())
				);
		return grupo;
	}
	
	private Usuario safeGetUsuarioById(Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName())
				);
		return usuario;
	}

	public List<GrupoListarDTO> findGruposByUSer(Long id) {
		
		//Verifica se o usuário existe
		Usuario usuario = safeGetUsuarioById(id);
		
		List<Long> gs = repository.getGruposByUserId(usuario.getId());
		
		return gs
				.stream()
				.map(grp -> new GrupoListarDTO(repository.getById(grp), usuario))
				.collect(Collectors.toList());
	}

	
	
}
