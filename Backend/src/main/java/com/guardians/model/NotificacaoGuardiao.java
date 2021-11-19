package com.guardians.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "Notificacoes_Guardiao")
public class NotificacaoGuardiao {

	@Id
	private Long id;
	
	@MapsId
	@OneToOne
	@NonNull private Notificacao notificacao;
	
	@ManyToOne
	@NonNull private Usuario guardiao;
	
	@OneToOne
	@NonNull private Usuario usuario;

	private Boolean recebida = false;
	
}
