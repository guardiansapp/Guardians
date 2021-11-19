package com.guardians.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.guardians.model.Configuracao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public class ConfiguracaoComIdDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public ConfiguracaoComIdDTO(Configuracao configuracao) {
		this.id = configuracao.getId();
		this.preMensagem = configuracao.getPreMensagem();
		this.telefoneVisivel = configuracao.getTelefoneVisivel();
		this.enderecoVisivel = configuracao.getEnderecoVisivel();
		this.idadeVisivel = configuracao.getIdadeVisivel();
		this.enviarParaGrupos = configuracao.getEnviarParaGrupos();
		this.compartilharLocalizacao = configuracao.getCompartilharLocalizacao();
		
		configuracao.getGrupoNotificacoes().forEach(gn -> gruposParaEnviarIds.add(gn.getId().getGrupo().getId()));
	}
	
	private Long id;
	private String preMensagem;
	private Boolean telefoneVisivel;
	private Boolean enderecoVisivel;
	private Boolean idadeVisivel;
	private Boolean enviarParaGrupos;
	private Boolean compartilharLocalizacao;
	
	private Set<Long> gruposParaEnviarIds = new HashSet<>();
}
