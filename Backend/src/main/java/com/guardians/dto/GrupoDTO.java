package com.guardians.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.guardians.model.Grupo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class GrupoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public GrupoDTO(Grupo grupo) {
		this.id = grupo.getId();
		this.nome = grupo.getNome();
		
		grupo.getUsuarios().forEach(user -> {
			usuarios.add(new UsuarioDTO(user));
		});
		
	}
	
	private Long id;
	private String nome;
	private Set<UsuarioDTO> usuarios = new HashSet<>();
	
}
