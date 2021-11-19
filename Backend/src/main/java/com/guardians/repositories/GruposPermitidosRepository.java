package com.guardians.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guardians.model.GruposPermitidos;
import com.guardians.model.PKs.GruposPermitidosPK;

public interface GruposPermitidosRepository extends JpaRepository<GruposPermitidos, GruposPermitidosPK>{
	
}
