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

public class NotificacaoSemIdDTO {

	private String timestamp;
	private String mensagem;
	private Double latitude;
	private Double longitude;
	
	private Long usuario_id;
	private Set<Long> grupos_id;
	private Long guardiao_id;
	
	public NotificacaoSemIdDTO(Notificacao notificacao) {
		
		this.timestamp = notificacao.getTimestamp().toString();
		this.mensagem = notificacao.getMensagem();
		this.latitude = notificacao.getLatitude();
		this.longitude = notificacao.getLongitude();
		
		this.usuario_id = notificacao.getUsuario().getId();
		this.grupos_id = notificacao.getGrupos()
				.stream().map(g -> g.getId().getGrupo().getId())
				.collect(Collectors.toSet());
		
		this.guardiao_id = (notificacao.getUsuario().getGuardiao() != null)
				? notificacao.getUsuario().getGuardiao().getId()
				: null;
		
	}
	
}
