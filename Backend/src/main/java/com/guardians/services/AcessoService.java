package com.guardians.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.guardians.dto.AcessoDTO;
import com.guardians.model.Acesso;
import com.guardians.repositories.AcessoRepository;
import com.guardians.services.exceptions.DataIntegrityException;
import com.guardians.services.exceptions.ObjectNotFoundException;

@Service
public class AcessoService {

	@Autowired
	private AcessoRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public AcessoDTO getById(Long id) {
		Acesso acesso = safeGetById(id);
		return new AcessoDTO(acesso);
	}
	
	
	public AcessoDTO updateNomeUsuario(AcessoDTO acesso, Long id) {
		Acesso obj = safeGetById(id);
		
		obj.setNomeUsuario(
				(acesso.getNomeUsuario() != null)
					? acesso.getNomeUsuario()
					: obj.getNomeUsuario()
				);
		
		try {
			repository.save(obj);
		}catch(Exception e) {
			throw new DataIntegrityException("Nome de usuário não está disponivel para uso, pois não é unico.");
		}
		
		return new AcessoDTO(obj);
	}
	
	public AcessoDTO updateSenha(String senha, Long id) {

		if(senha == null) {
			throw new DataIntegrityException("Senha não enviada.");
		}
		
		Acesso obj = safeGetById(id);
		
		obj.setSenha(encoder.encode(senha));
		
		repository.save(obj);
		
		return new AcessoDTO(obj);
	}
	
	public Acesso safeGetById(Long id) {
		return repository.findById(id).orElseThrow(
			() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Acesso.class.getName())
		);
	}	
	
	public AcessoDTO getByNomeUsuario(String nome) {
		Acesso acesso = repository.findByNomeUsuario(nome);
		
		if(acesso != null) {
			return new AcessoDTO(acesso);
		}else {
			throw new ObjectNotFoundException("Objeto não encontrado! nome: " + nome + ", Tipo: " + Acesso.class.getName());
		}
		
	}
}
