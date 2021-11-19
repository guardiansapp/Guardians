package com.guardians.services.entities;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.guardians.model.Usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "usuario")
@ToString

public class UsuarioNotificacao {

	private SseEmitter emitter;
	private Usuario usuario;
	
	public UsuarioNotificacao(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
