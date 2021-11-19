package com.guardians.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.guardians.model.PKs.GruposPermitidosPK;

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
@EqualsAndHashCode(of = "id")
@ToString

@Entity
@Table(name = "Grupos_permitidos")
public class GruposPermitidos {

	@EmbeddedId
	private GruposPermitidosPK id;
	
	public GruposPermitidos(Usuario usuario, Grupo grupo) {
		this.id = new GruposPermitidosPK();
		this.id.setConfiguracao(usuario.getConfiguracao());
		this.id.setGrupo(grupo);
	}
	
	public GruposPermitidos(Configuracao configuracao, Grupo grupo) {
		this.id = new GruposPermitidosPK();
		this.id.setConfiguracao(configuracao);
		this.id.setGrupo(grupo);
	}
	
}
