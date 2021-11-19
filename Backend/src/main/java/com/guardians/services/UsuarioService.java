package com.guardians.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guardians.dto.AcessoComSenhaDTO;
import com.guardians.dto.ConfiguracaoDTO;
import com.guardians.dto.EnderecoDTO;
import com.guardians.dto.UsuarioDTO;
import com.guardians.dto.UsuarioInfoPessoalDTO;
import com.guardians.dto.UsuarioSemIdDTO;
import com.guardians.model.Acesso;
import com.guardians.model.Configuracao;
import com.guardians.model.Endereco;
import com.guardians.model.Grupo;
import com.guardians.model.Usuario;
import com.guardians.repositories.GrupoRepository;
import com.guardians.repositories.UsuarioRepository;
import com.guardians.services.exceptions.DataIntegrityException;
import com.guardians.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public UsuarioDTO findById(Long id) {
		Usuario obj = safeGetById(id);
		return new UsuarioDTO(obj);
	}
	
	@Transactional(rollbackFor = DataIntegrityException.class)
	public UsuarioDTO save(UsuarioSemIdDTO usuario) {

		Usuario obj = check(usuario);
		
		//Encripta senha
		obj.getAcesso().setSenha(encoder.encode(obj.getAcesso().getSenha()));
		
		try {
			repository.save(obj);	
		}catch(Exception e) {
			throw new DataIntegrityException("Nome de usuário e email devem ser únicos.");
		}	
		
		return new UsuarioDTO(obj);
	}
	
	@Transactional(readOnly = false)
	public UsuarioInfoPessoalDTO update(UsuarioInfoPessoalDTO usuario, Long id) {
		
		Usuario obj = safeGetById(id);
		
		if(repository.getByEmail(usuario.getEmail()) != null && !usuario.getEmail().equals(obj.getEmail()))
			throw new DataIntegrityException("E-mail já cadastrado.");
		
		Usuario guardiao = null;
		if(usuario.getGuardiaoId() != null) {
			guardiao = safeGetById(usuario.getGuardiaoId(), "Guardião de id: " + usuario.getGuardiaoId() + " não foi encontrado.");
		}
		
		obj.setNome((usuario.getNome() != null)
				? usuario.getNome()
				: obj.getNome());
		
		obj.setSobrenome((usuario.getSobrenome() != null)
				? usuario.getSobrenome()
				: obj.getSobrenome());
			
		obj.setIdade((usuario.getIdade() != null)
				? usuario.getIdade()
				: obj.getIdade());
		
		obj.setEmail((usuario.getEmail() != null)
				? usuario.getEmail()
				: obj.getEmail());
		
//		obj.setGuardiao((guardiao != null)
//				? guardiao
//				: obj.getGuardiao());
		
		obj.setGuardiao(guardiao);
		
		repository.save(obj);
		
		return new UsuarioInfoPessoalDTO(obj);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		
		Usuario obj = safeGetById(id);

		List<Grupo> grupos = grupoRepository.findAll()
				.stream()
				.filter(g -> g.getUsuarios().contains(obj))
				.collect(Collectors.toList());

		//Remove dos grupos
		for(Grupo grupo : grupos) {
			grupo.getUsuarios().remove(obj);
			grupoRepository.save(grupo);
			
			if(grupo.getUsuarios().size() == 0) {
				grupoRepository.delete(grupo);
				System.out.println("Cascadely deleted " + grupo.toString());
			}
		}
		
		//Desativa o usuário como guardião de outros usuários
		if(obj.getProtegidos().size() != 0) {
			for(Usuario usuario : obj.getProtegidos()) {
				usuario.setGuardiao(null);
				repository.save(usuario);
			}
		}
		
		repository.delete(obj);
		
	}
	
	public Set<String> addTelefone(Long id, String telefone) {
		Usuario usuario = safeGetById(id);
		
		if(usuario.getTelefones().contains(telefone))
			throw new DataIntegrityException("O usuário já possui o telefone informado.");
		
		usuario.getTelefones().add(telefone);
		repository.save(usuario);
		
		return usuario.getTelefones();
	}
	
	public Set<String> removeTelefone(Long id, String telefone){
		
		Usuario usuario = safeGetById(id);
		
		if(usuario.getTelefones().contains(telefone)) {
			usuario.getTelefones().remove(telefone);
			repository.save(usuario);
		}else {
			throw new ObjectNotFoundException("O usuário de id: " + id + " não possui o telefone informado.");
		}
		
		return usuario.getTelefones();
		
		
	}
	
	private Usuario check(UsuarioSemIdDTO usuario) {
		EnderecoDTO enderecoDto = usuario.getEndereco();
		AcessoComSenhaDTO acessoDto = usuario.getAcesso();
		Set<String> telefonesDto = usuario.getTelefones();
		
		if(enderecoDto != null) {
			if(enderecoDto.getBairro() == null
				|| enderecoDto.getCep() == null
				|| enderecoDto.getCidade() == null
				|| enderecoDto.getEstado() == null
				|| enderecoDto.getLogradouro() == null
				|| enderecoDto.getNumero() == null
			) {
				throw new DataIntegrityException("O usuário precisa ter um endereço com todos os campos preenchidos.");
			}
		}else {
			throw new DataIntegrityException("O usuário precisa ter um endereço.");
		}
		
		
		if(acessoDto != null) {
			if(acessoDto.getNomeUsuario() == null || acessoDto.getSenha() == null) {
				throw new DataIntegrityException("O usuário precisa ter um nome de usuário e senha.");
			}else {
				if(repository.getByUsername(acessoDto.getNomeUsuario()) != null)
					throw new DataIntegrityException("Nome de usuário já esta cadastrado.");
			}
		}else {
			throw new DataIntegrityException("O usuário precisa ter um nome de usuário e senha.");
		}
		
		
		
		if(telefonesDto == null || telefonesDto.size() == 0) {
			throw new DataIntegrityException("O usuário precisa ter ao menos um telefone para contato.");
		}
		
		
		if(usuario.getNome() == null
			|| usuario.getSobrenome() == null
			|| usuario.getIdade() == null
			|| usuario.getEmail() == null
		) {
			throw new DataIntegrityException("O usuário precisa todos os dados pessoais preenchidos.");
		}else {
			if(repository.getByEmail(usuario.getEmail()) != null)
				throw new DataIntegrityException("E-mail já cadastrado.");
		}
		
		
		Configuracao config;
		if(usuario.getConfiguracao() == null) {
			config = new Configuracao("Preciso de ajuda.", true, true, true, true, false);
		}else {
			ConfiguracaoDTO dto = usuario.getConfiguracao(); 
			config = new Configuracao(dto.getPreMensagem(), dto.getTelefoneVisivel(), dto.getEnderecoVisivel(), dto.getIdadeVisivel(), dto.getCompartilharLocalizacao(), dto.getEnviarParaGrupos());
		}
		
		Usuario guardiao = null;
		if(usuario.getGuardiaoId() != null) {
			guardiao = safeGetById(usuario.getGuardiaoId(), "Guardião de id: " + usuario.getGuardiaoId() + " não foi encontrado.");
		
		}
		
		Usuario obj = new Usuario(
				usuario.getNome(), 
				usuario.getSobrenome(), 
				usuario.getIdade(), 
				usuario.getEmail()
		);
		
		obj.getTelefones().addAll(telefonesDto);
		obj.setGuardiao(guardiao);
		
		Acesso acesso = new Acesso(
				acessoDto.getNomeUsuario(),
				acessoDto.getSenha(),
				acessoDto.getDica1(),
				acessoDto.getDica2()
		);
		
		Endereco endereco = new Endereco(
				enderecoDto.getCep(),
				enderecoDto.getEstado(),
				enderecoDto.getCidade(), 
				enderecoDto.getBairro(), 
				enderecoDto.getLogradouro(), 
				enderecoDto.getNumero()
		);
		
		endereco.setComplemento(enderecoDto.getComplemento());
				
		acesso.setUsuario(obj);
		endereco.setUsuario(obj);
		config.setUsuario(obj);
		
		obj.setAcesso(acesso);
		obj.setEndereco(endereco);
		obj.setConfiguracao(config);
		
		return obj;
		
	}
 	
	public UsuarioDTO setNotificationToken(String token, Long id) {
		Usuario usuario = safeGetById(id);
		usuario.setNotificationToken(token);
		usuario = repository.save(usuario);
		
		return new UsuarioDTO(usuario);		
	}
	
	public UsuarioDTO clearNotificationToken(Long id) {
		return setNotificationToken(null, id);		
	}
	
	private Usuario safeGetById(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName())
				);
		return usuario;
	}
	
	private Usuario safeGetById(Long id, String msg) {
		Usuario usuario = repository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(
						msg)
				);
		return usuario;
	}
	
}
