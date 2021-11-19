package com.guardians.dto;

import java.io.Serializable;

import com.guardians.model.Acesso;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class AcessoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public AcessoDTO(Acesso acesso){
		this.nomeUsuario = acesso.getNomeUsuario();
		this.dica1 = acesso.getDica1();
		this.dica2 = acesso.getDica2();
	}
	
	private String nomeUsuario;
	private String dica1;
	private String dica2;
	
}
