package com.guardians.dto;

import com.guardians.model.NotificacaoGrupo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString

public class NotificacaoGrupoDTO {

	private Long notificacao_id;
	private String timestamp;
	private String mensagem;
	private Double latitude;
	private Double longitude;
	
	private String usuario_nome;
	private Long grupos_id;
	private String grupo_nome;
	
	private Boolean recebida;
	
	public NotificacaoGrupoDTO(NotificacaoGrupo notificacao) {
		
		this.notificacao_id = notificacao.getId().getNotificacao().getId();
		this.timestamp = notificacao.getId().getNotificacao().getTimestamp().toString();
		this.mensagem = notificacao.getId().getNotificacao().getMensagem();
		this.latitude = notificacao.getId().getNotificacao().getLatitude();
		this.longitude = notificacao.getId().getNotificacao().getLongitude();

		this.usuario_nome = notificacao.getId().getNotificacao().getUsuario().getNome() + " " + notificacao.getId().getNotificacao().getUsuario().getSobrenome();
		this.grupos_id = notificacao.getId().getGrupo().getId();
		this.grupo_nome = notificacao.getId().getGrupo().getNome();		
		
		this.recebida = notificacao.getRecebida();
	}
	
}
