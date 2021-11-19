package com.guardians.dto;

import com.guardians.model.Notificacao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString

public class NotificacaoParaSalvarDTO {

	private String mensagem;
	private Double latitude;
	private Double longitude;
	
	public NotificacaoParaSalvarDTO(Notificacao notificacao) {
		
		this.mensagem = notificacao.getMensagem();
		this.latitude = notificacao.getLatitude();
		this.longitude = notificacao.getLongitude();
		
	}
	
}
