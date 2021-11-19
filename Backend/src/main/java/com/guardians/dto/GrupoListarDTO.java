package com.guardians.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.guardians.model.Grupo;
import com.guardians.model.NotificacaoGrupo;
import com.guardians.model.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class GrupoListarDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public GrupoListarDTO(Grupo grupo, Usuario usuario) {
		this.id = grupo.getId();
		this.nome = grupo.getNome();
		
		List<NotificacaoGrupo> notificacoes = grupo.getNotificacoes().stream().collect(Collectors.toList());
		List<NotificacaoGrupo> nots = new ArrayList<>();
		
		nots = notificacoes.stream().filter(ng -> ng.getId().getUsuario().getId().equals(usuario.getId())).collect(Collectors.toList());
		
		this.totalNotificacoes = nots.size();
		this.notificacoesNaoLidas = nots
			.stream()
			.filter((n) -> !n.getRecebida())
			.collect(Collectors.toSet())
			.size();
		
	}
	
	private Long id;
	private String nome;
	private Integer totalNotificacoes;
	private Integer notificacoesNaoLidas;
	
	
}
