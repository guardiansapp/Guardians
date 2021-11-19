package com.guardians.dto;

import com.guardians.model.NotificacaoGuardiao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString

public class NotificacaoGuardiaoDTO {

	private String usuario_nome;
	private Long notificacao_id;
	private String mensagem;
	private String timestamp;
	private Double latitude;
	private Double longitude;
	private Boolean recebida;
	
	public NotificacaoGuardiaoDTO(NotificacaoGuardiao notificacao) {
		this.usuario_nome = notificacao.getUsuario().getNome() + " " + notificacao.getUsuario().getSobrenome();		
		this.notificacao_id = notificacao.getNotificacao().getId();
		this.mensagem = notificacao.getNotificacao().getMensagem();
		this.timestamp = notificacao.getNotificacao().getTimestamp().toString();
		this.latitude = notificacao.getNotificacao().getLatitude();
		this.longitude = notificacao.getNotificacao().getLongitude();
		this.recebida = notificacao.getRecebida();
	}
	
}
