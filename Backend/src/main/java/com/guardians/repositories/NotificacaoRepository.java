package com.guardians.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.guardians.model.Notificacao;
import com.guardians.model.NotificacaoGrupo;
import com.guardians.model.NotificacaoGuardiao;
import com.guardians.model.Usuario;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{
	
	@Query("SELECT obj FROM NotificacaoGrupo obj WHERE obj.recebida = false ORDER BY obj.id desc")
	public Set<NotificacaoGrupo> getNaoRecebidas();
	
	@Query("SELECT obj FROM NotificacaoGuardiao obj WHERE obj.recebida = false ORDER BY obj.id desc")
	public List<NotificacaoGuardiao> getNaoRecebidasGuardioes();
	
	@Query("SELECT obj FROM NotificacaoGuardiao obj WHERE obj.id.guardiao = ?1 ORDER BY obj.id desc")
	public List<NotificacaoGuardiao> getAllParaGuardioes(Usuario user);	
	
	@Query("SELECT obj FROM NotificacaoGrupo obj WHERE obj.id.usuario = ?1 ORDER BY obj.id desc")
	public List<NotificacaoGrupo> getAll(Usuario user);
		
}
