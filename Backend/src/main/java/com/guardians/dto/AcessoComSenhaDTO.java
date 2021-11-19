package com.guardians.dto;

import java.io.Serializable;

import com.guardians.model.Acesso;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class AcessoComSenhaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public AcessoComSenhaDTO(Acesso acesso){
		this.nomeUsuario = acesso.getNomeUsuario();
		this.senha = acesso.getSenha();
		this.dica1 = acesso.getDica1();
		this.dica2 = acesso.getDica2();
	}
	
	private String nomeUsuario;
	private String senha;
	private String dica1;
	private String dica2;
	
}
