package com.guardians.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.guardians.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	@Query(value = 
			  "SELECT g.id "
			+ "FROM USUARIOS u INNER JOIN USUARIOS_GRUPOS ug "
			+ "ON u.ID = ug.USUARIO_ID "
			+ "INNER JOIN GRUPOS G "
			+ "ON  ug.GRUPO_ID = g.ID "
			+ "WHERE ug.USUARIO_ID = ?1"
		, nativeQuery = true)
	public List<Long> getGruposByUserId(Long id);
	
}
