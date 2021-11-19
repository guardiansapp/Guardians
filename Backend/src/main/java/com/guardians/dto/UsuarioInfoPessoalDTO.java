package com.guardians.dto;

import java.io.Serializable;

import com.guardians.model.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString

public class UsuarioInfoPessoalDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public UsuarioInfoPessoalDTO(Usuario usuario) {
		this.guardiaoId = (usuario.getGuardiao() != null) ? usuario.getGuardiao().getId() : null;
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		this.idade = usuario.getIdade();
		this.email = usuario.getEmail();
		this.notification_token = usuario.getNotificationToken();
	}
	
	private Long guardiaoId;
	private String nome;
	private String sobrenome;
	private Integer idade;
	private String email;
	private String notification_token;
	
}
