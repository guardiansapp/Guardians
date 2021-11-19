package com.guardians.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.guardians.model.PKs.NotificacaoGrupoPK;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")


@Entity
@Table(name = "Notificacoes_Grupos")
public class NotificacaoGrupo {
	
	@EmbeddedId
	private NotificacaoGrupoPK id;
	
	private Boolean recebida = false;
	
	public NotificacaoGrupo(Notificacao notificacao, Grupo grupo, Usuario usuario	) {
		this.id = new NotificacaoGrupoPK(notificacao, grupo, usuario);
	}
	
}
