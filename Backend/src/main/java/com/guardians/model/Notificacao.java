package com.guardians.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"grupos", "usuario", "notificacaoGuardiao"})

@Entity
@Table(name = "Notificacoes")
public class Notificacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "not_seq")
	@SequenceGenerator(name = "not_seq", sequenceName = "notificacoes_sequence")
	@Setter private Long id;
	
	@NonNull @Setter private LocalDateTime timestamp;
	@NonNull @Setter private String mensagem;
	@Setter private Double latitude;
	@Setter private Double longitude;
	
	@ManyToOne
	@Setter
	@JoinColumn(name = "usuario_id")
	@NonNull private Usuario usuario;
	
	@OneToMany(mappedBy = "id.notificacao", cascade = CascadeType.ALL)
	private Set<NotificacaoGrupo> grupos = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@Setter
	private NotificacaoGuardiao notificacaoGuardiao;
	
	public Notificacao(String mensagem, Usuario usuario, Double latitude, Double longitude) {
		this.timestamp = LocalDateTime.now();
		this.mensagem = mensagem;
		this.usuario = usuario;

		if(usuario.getConfiguracao().getCompartilharLocalizacao()) {
			if(longitude != null && latitude != null) {
				this.setLatitude(latitude);
				this.setLongitude(longitude);
				
			}
		}else {
			setLatitude(null);
			setLongitude(null);
			
		}		

		if(usuario.getConfiguracao().getEnviarParaGrupos()) {
			for(GruposPermitidos grupos : usuario.getConfiguracao().getGrupoNotificacoes()) {
				for(Usuario user : grupos.getId().getGrupo().getUsuarios()) {
					if(user.equals(usuario) == false) {
						getGrupos().add(new NotificacaoGrupo(this, grupos.getId().getGrupo(), user));
					}
				}
			}	
		}
		
		if(usuario.getGuardiao() != null)
			setNotificacaoGuardiao(new NotificacaoGuardiao(this, usuario.getGuardiao(), usuario));
		
	}
	
}
