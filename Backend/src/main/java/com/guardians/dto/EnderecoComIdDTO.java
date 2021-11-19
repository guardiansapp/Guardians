package com.guardians.dto;

import java.io.Serializable;

import com.guardians.model.Endereco;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class EnderecoComIdDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public EnderecoComIdDTO(Endereco endereco) {
		this.id = endereco.getId();
		this.estado = endereco.getEstado();
		this.cidade = endereco.getCidade();
		this.bairro = endereco.getBairro();
		this.logradouro = endereco.getLogradouro();
		this.cep = endereco.getCEP();
		this.numero = endereco.getNumero();
		this.complemento = endereco.getComplemento();
	}
	
	private Long id;
	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String cep;
	private String numero;
	private String complemento;
	
}
