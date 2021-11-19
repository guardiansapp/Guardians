package com.guardians.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guardians.dto.NotificacaoDTO;
import com.guardians.dto.NotificacaoGrupoDTO;
import com.guardians.dto.NotificacaoGuardiaoDTO;
import com.guardians.dto.NotificacaoParaSalvarDTO;
import com.guardians.model.Grupo;
import com.guardians.model.Notificacao;
import com.guardians.model.NotificacaoGrupo;
import com.guardians.model.NotificacaoGuardiao;
import com.guardians.model.Usuario;
import com.guardians.repositories.NotificacaoRepository;
import com.guardians.repositories.UsuarioRepository;
import com.guardians.services.exceptions.ObjectNotFoundException;

@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SendNotificationService sendService;

	public NotificacaoDTO save(Long usuario_id, NotificacaoParaSalvarDTO notificacao) {

		Usuario usuario = safeGetUsuarioById(usuario_id);

		Notificacao obj = new Notificacao(notificacao.getMensagem(), usuario, notificacao.getLatitude(),
				notificacao.getLongitude());

		if (notificacao.getMensagem() == null) {
			obj.setMensagem(safeGetUsuarioById(usuario_id).getConfiguracao().getPreMensagem());
		}

		repository.save(obj);

		send(obj);

		return new NotificacaoDTO(obj);
	}

	public void confirmGrupo(Long grupoId, Long notificacaoId, Long usuarioId) {

		Notificacao notificacao = safeGetById(notificacaoId);

		NotificacaoGrupo grupo = notificacao.getGrupos().stream()
				.filter(g -> g.getId().getGrupo().getId() == grupoId && g.getId().getUsuario().getId() == usuarioId)
				.findFirst().orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + grupoId + ", Tipo: " + Grupo.class.getName()));

		notificacao.getGrupos()
				.removeIf(g -> g.getId().getGrupo().getId() == grupoId && g.getId().getUsuario().getId() == usuarioId);

		grupo.setRecebida(true);

		notificacao.getGrupos().add(grupo);

		repository.save(notificacao);

	}

	public void confirmGuardiao(Long notificacaoId) {

		Notificacao notificacao = safeGetById(notificacaoId);

		NotificacaoGuardiao guardiao = notificacao.getNotificacaoGuardiao();

		if (guardiao == null) {
			throw new ObjectNotFoundException("O usuário que enviou a notificação não tem guardião.");
		}

		guardiao.setRecebida(true);

		notificacao.setNotificacaoGuardiao(guardiao);

		repository.save(notificacao);

	}

	public Set<NotificacaoGrupoDTO> getPendingGrupos(Long usuarioId) {
		// Verifica se o usuário existe
		safeGetUsuarioById(usuarioId);
		
		Set<NotificacaoGrupo> ng = repository.getNaoRecebidas();
		ng = ng.stream().filter(p -> p.getId().getUsuario().getId() == usuarioId).collect(Collectors.toSet());
		
		Set<NotificacaoGrupoDTO> ncg = new HashSet<>();
		for (NotificacaoGrupo notificacaoGrupo : ng) {
			ncg.add(new NotificacaoGrupoDTO(notificacaoGrupo));
		}
		return ncg;

	}

	public List<NotificacaoGuardiaoDTO> getPendingGuardioes(Long usuarioId) {
		// Verifica se o usuário existe
		safeGetUsuarioById(usuarioId);
		List<NotificacaoGuardiao> ng = repository.getNaoRecebidasGuardioes();
		ng = ng.stream().filter(p -> p.getGuardiao().getId() == usuarioId).collect(Collectors.toList());
		return ng.stream().map(n -> new NotificacaoGuardiaoDTO(n)).collect(Collectors.toList());
	}
	
	public List<NotificacaoGuardiaoDTO> getGuardioes(Long usuarioId) {

		Usuario user = safeGetUsuarioById(usuarioId);
		List<NotificacaoGuardiao> ng = repository.getAllParaGuardioes(user);
		return ng.stream().map(n -> new NotificacaoGuardiaoDTO(n)).collect(Collectors.toList());

	}
	
	public List<NotificacaoGrupoDTO> getGrupos(Long usuarioId) {

		Usuario user = safeGetUsuarioById(usuarioId);
		List<NotificacaoGrupo> ng = repository.getAll(user);
		return ng.stream().map(n -> new NotificacaoGrupoDTO(n)).collect(Collectors.toList());

	}

	
	
	
	
	public NotificacaoDTO getById(Long id) {
		Notificacao obj = safeGetById(id);
		return new NotificacaoDTO(obj);
	}

	private void send(Notificacao notificacao) {
		
		if(notificacao.getNotificacaoGuardiao() != null) {
			if(notificacao.getNotificacaoGuardiao().getGuardiao().getNotificationToken() != null) {
				sendService.send(notificacao.getNotificacaoGuardiao());
			}
		}
		
		notificacao.getGrupos().forEach(grupo -> {
			if(grupo.getId().getUsuario().getNotificationToken() != null) {
				sendService.send(grupo);
			}
		});
		
	}

	private Notificacao safeGetById(Long id) {
		Notificacao notificacao = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Notificacao.class.getName()));
		return notificacao;
	}

	private Usuario safeGetUsuarioById(Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
		return usuario;
	}

}
