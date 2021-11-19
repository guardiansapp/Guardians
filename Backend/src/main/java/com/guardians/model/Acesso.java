package com.guardians.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.guardians.model.enums.Profile;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "usuario")
@ToString(exclude = "usuario")


@Entity
@Table(name = "Acessos")
public class Acesso {

	@Id
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "usuario_id")
	@Setter
	private Usuario usuario;
	
	@Column(name = "nome_usuario", nullable = false, unique = true)
	@Setter
	@NonNull private String nomeUsuario;
	
	@Column(nullable = false)
	@NonNull
	@Setter private String senha;
	
	@NonNull
	@Setter
	private String dica1;
	
	@NonNull
	@Setter
	private String dica2;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_profiles")
	private Set<Integer> profiles = new HashSet<>();
	
	
	public Acesso(String nomeUsuario, String senha, String dica1, String dica2) {
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
		this.dica1 = dica1;
		this.dica2 = dica2;
		addProfile(Profile.DEFAULT);
	}
	
	public Acesso() {
		addProfile(Profile.DEFAULT);
	}
	
	public Set<Profile> getProfiles(){
		return profiles
				.stream()
				.map(p -> Profile.toEnum(p))
				.collect(Collectors.toSet());
	}
	
	public void addProfile(Profile profile) {
		profiles.add(profile.getCod());
	}
	
}
