package com.guardians.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.guardians.model.Notificacao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString

public class NotificacaoDTO {

	private Long id;
	private String timestamp;
	private String mensagem;
	private Double latitude;
	private Double longitude;
	private String usuario_nome;
	private Boolean recebida;
	
	private Long usuario_id;
	private Long guardiao_id;
	private Set<Long> grupos_id;
	
	public NotificacaoDTO(Notificacao notificacao) {
		
		this.id = notificacao.getId();
		this.timestamp = notificacao.getTimestamp().toString();
		this.mensagem = notificacao.getMensagem();
		this.latitude = notificacao.getLatitude();
		this.longitude = notificacao.getLongitude();
		this.usuario_nome = notificacao.getUsuario().getNome() + " " + notificacao.getUsuario().getSobrenome();

		this.usuario_id = notificacao.getUsuario().getId();
		this.grupos_id = notificacao.getGrupos()
				.stream().map(g -> g.getId().getGrupo().getId())
				.collect(Collectors.toSet());
		
		this.guardiao_id = (notificacao.getUsuario().getGuardiao() != null)
				? notificacao.getUsuario().getGuardiao().getId()
				: null;
		
	}
	
}
