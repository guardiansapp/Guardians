package com.guardians.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"usuarios", "notificacoes"})

@Entity
@Table(name = "Grupos")
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_seq")
	@SequenceGenerator(name = "grupo_seq", sequenceName = "grupos_sequence")
	@Setter private Long id;
	
	@Column(nullable = false)
	@NonNull @Setter private String nome;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "Usuarios_Grupos",
			inverseJoinColumns = @JoinColumn(name = "usuario_id"),
			joinColumns = @JoinColumn(name = "grupo_id")
			)
	private Set<Usuario> usuarios = new HashSet<>();
	
	@OneToMany(mappedBy = "id.grupo", cascade = CascadeType.REMOVE)
	private Set<NotificacaoGrupo> notificacoes = new HashSet<>();
	
}
