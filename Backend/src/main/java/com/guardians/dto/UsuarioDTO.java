package com.guardians.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.guardians.model.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString

public class UsuarioDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.guardiaoId = (usuario.getGuardiao() != null) ? usuario.getGuardiao().getId() : null;
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		this.idade = usuario.getIdade();
		this.email = usuario.getEmail();
		this.notification_token = usuario.getNotificationToken();
		
		try {
			this.nome_guardiao = usuario.getGuardiao().getNome() + " " + usuario.getGuardiao().getSobrenome();
		}catch(Exception e) {
			this.nome_guardiao = null;
		}
		
		this.telefones.addAll(usuario.getTelefones());
		
		this.acesso = new AcessoDTO(usuario.getAcesso());
		this.configuracao = new ConfiguracaoDTO(usuario.getConfiguracao());
		this.endereco = new EnderecoDTO(usuario.getEndereco());
		
		grupos_id = usuario.getGrupos().stream().map(g -> g.getId()).collect(Collectors.toSet());
	}
	
	private Long id;
	private Long guardiaoId;
	private String nome;
	private String sobrenome;
	private Integer idade;
	private String email;
	private String nome_guardiao;
	private String notification_token;
	
	private Set<String> telefones = new HashSet<>();
	
	private AcessoDTO acesso;
	private ConfiguracaoDTO configuracao;
	private EnderecoDTO endereco;
	
	private Set<Long> grupos_id;
	
}
