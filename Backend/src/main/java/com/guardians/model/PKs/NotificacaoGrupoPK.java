package com.guardians.model.PKs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.guardians.model.Grupo;
import com.guardians.model.Notificacao;
import com.guardians.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Embeddable
public class NotificacaoGrupoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Notificacao notificacao;
	
	@ManyToOne
	private Grupo grupo;
	
	@ManyToOne
	private Usuario usuario;
}
