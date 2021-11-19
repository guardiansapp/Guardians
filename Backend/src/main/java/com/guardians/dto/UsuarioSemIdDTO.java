package com.guardians.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.guardians.model.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class UsuarioSemIdDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public UsuarioSemIdDTO(Usuario usuario) {
		this.guardiaoId = (usuario.getGuardiao() != null) ? usuario.getGuardiao().getId() : null;
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		this.idade = usuario.getIdade();
		this.email = usuario.getEmail();
		
		this.telefones.addAll(usuario.getTelefones());
		
		this.acesso = new AcessoComSenhaDTO(usuario.getAcesso());
		this.configuracao = new ConfiguracaoDTO(usuario.getConfiguracao());
		this.endereco = new EnderecoDTO(usuario.getEndereco());
		
		grupos_id = usuario.getGrupos().stream().map(g -> g.getId()).collect(Collectors.toSet());
	}
	
	private Long guardiaoId;
	private String nome;
	private String sobrenome;
	private Integer idade;
	private String email;

	private Set<String> telefones = new HashSet<>();
	
	private AcessoComSenhaDTO acesso;
	private ConfiguracaoDTO configuracao;
	private EnderecoDTO endereco;
	
	private Set<Long> grupos_id;
	
}
