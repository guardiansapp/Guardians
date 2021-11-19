package com.guardians.model.PKs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.guardians.model.Configuracao;
import com.guardians.model.Grupo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"configuracao", "grupo"})

@Embeddable
public class GruposPermitidosPK implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Configuracao configuracao;
	
	@ManyToOne
	private Grupo grupo;
	
	
	
}
