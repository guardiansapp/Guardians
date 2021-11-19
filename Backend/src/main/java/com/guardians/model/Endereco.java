package com.guardians.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
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
@ToString(exclude = "usuario")

@Entity
@Table(name = "Enderecos")
public class Endereco {

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@Column(nullable = false)
	@NonNull private String CEP;
	
	@Column(nullable = false)
	@NonNull private String estado;
	
	@Column(nullable = false)
	@NonNull private String cidade;
	
	@Column(nullable = false)
	@NonNull private String bairro;
	
	@Column(nullable = false)
	@NonNull private String logradouro;
	
	@Column(nullable = false)
	@NonNull private String numero;
	
	@Column(nullable = true)
	private String complemento;

}
