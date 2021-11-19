package com.guardians.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@ToString(exclude = {"protegidos", "logs", "guardiao"})

@Entity
@Table(name = "Usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
	@SequenceGenerator(name = "usuario_seq", sequenceName = "usuarios_sequence")
	@Setter private Long id;
	
	@ManyToOne
	@JoinColumn(nullable = true, name = "guardiao")
	@Setter private Usuario guardiao;
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JoinColumn(name = "acesso_id")
	@Setter private Acesso acesso;
	
	@NonNull @Setter private String nome;
	@NonNull @Setter private String sobrenome;
	@NonNull @Setter private Integer idade;
	
	@Column(unique = true)
	@NonNull @Setter private String email;
	
	@Setter private Boolean deletado = false;
	
	@Setter
	@Column(unique=true)
	private String notificationToken;
	
	@OneToMany(mappedBy = "guardiao")
	private Set<Usuario> protegidos = new HashSet<>();
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	@Setter private Endereco endereco;
	
	@ElementCollection
	@CollectionTable(
			name = "telefones",
			joinColumns = @JoinColumn(name = "usuario_id")
			)
	private Set<String> telefones = new HashSet<>();
	
	@ManyToMany(mappedBy = "usuarios")
	private Set<Grupo> grupos = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private Set<Notificacao> logs = new HashSet<>();
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	@Setter private Configuracao configuracao;
	
}
