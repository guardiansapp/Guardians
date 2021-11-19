package com.guardians.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guardians.dto.EnderecoComIdDTO;
import com.guardians.dto.EnderecoDTO;
import com.guardians.model.Endereco;
import com.guardians.repositories.EnderecoRepository;
import com.guardians.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;
	
	public EnderecoComIdDTO getById(Long id) {
		EnderecoComIdDTO endereco = new EnderecoComIdDTO(safeGetById(id));
		return endereco;
	}
	
	public EnderecoComIdDTO update(Long id, EnderecoDTO endereco) {
		
		Endereco obj = safeGetById(id);
		
		obj.setEstado(
				(endereco.getEstado() != null)
					? endereco.getEstado()
					: obj.getEstado()
				);
		
		obj.setCidade(
				(endereco.getCidade() != null)
					? endereco.getCidade()
					: obj.getCidade()
				);
		
		obj.setBairro(
				(endereco.getBairro() != null)
					? endereco.getBairro()
					: obj.getBairro()
				);
		
		obj.setLogradouro(
				(endereco.getLogradouro() != null)
					? endereco.getLogradouro()
					: obj.getLogradouro()
				);
		
		obj.setCEP(
				(endereco.getCep() != null)
					? endereco.getCep()
					: obj.getCEP()
				);
		
		obj.setNumero(
				(endereco.getNumero() != null)
					? endereco.getNumero()
					: obj.getNumero()
				);
		
		obj.setComplemento(
				(endereco.getComplemento() != null)
					? endereco.getComplemento()
					: obj.getComplemento()
				);
		
		repository.save(obj);
		
		return new EnderecoComIdDTO(obj);
		
	}
	
	public Endereco safeGetById(Long id) {
		Endereco endereco = repository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName())
				);
		return endereco;
	}
}
