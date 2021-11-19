package com.guardians.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.guardians.model.Acesso;
import com.guardians.repositories.AcessoRepository;


@Service
public class UserDetailsServiceImp implements UserDetailsService{

	@Autowired
	private AcessoRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Acesso user = repository.findByNomeUsuario(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new UserDetailsImp(user.getId(), user.getNomeUsuario(), user.getSenha(), user.getProfiles());
	}

}
