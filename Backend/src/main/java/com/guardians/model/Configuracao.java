package com.guardians.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "usuario")
@ToString(exclude = {"usuario", "grupoNotificacoes"})

@Entity
@Table(name = "Configuracoes")
public class Configuracao {
	
	@Id
	private Long id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@Column(nullable = false)
	@NonNull private String preMensagem;
	
	@Column(nullable = false, name = "telefone_visivel")
	@NonNull private Boolean telefoneVisivel;
	
	@Column(nullable = false, name = "endereco_visivel")
	@NonNull private Boolean enderecoVisivel;
	
	@Column(nullable = false, name = "idade_visivel")
	@NonNull private Boolean idadeVisivel;
	
	@Column(nullable = false, name = "compartilhar_localizacao")
	@NonNull private Boolean compartilharLocalizacao;
	
	@Column(nullable = false)
	@NonNull private Boolean enviarParaGrupos;
	
	@OneToMany(mappedBy = "id.configuracao", cascade = CascadeType.ALL)
	private Set<GruposPermitidos> grupoNotificacoes = new HashSet<>();
	
}
