package com.guardians.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.guardians.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query("SELECT u FROM Usuario u WHERE u.email = ?1")
	public Usuario getByEmail(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.acesso.nomeUsuario = ?1")
	public Usuario getByUsername(String username);
	
	
	
}
