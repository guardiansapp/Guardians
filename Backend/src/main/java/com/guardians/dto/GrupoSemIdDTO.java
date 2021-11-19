package com.guardians.dto;

import java.io.Serializable;

import com.guardians.model.Grupo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class GrupoSemIdDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public GrupoSemIdDTO(Grupo grupo) {
		this.nome = grupo.getNome();
	}
	
	private String nome;
}
