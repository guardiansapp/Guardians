package com.guardians.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.guardians.model.enums.Profile;

import lombok.Getter;

@Getter

public class UserDetailsImp implements UserDetails{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String password;
	Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImp() {
		
	}
	
	public UserDetailsImp(Long id, String username, String password, Set<Profile> profiles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = profiles
				.stream()
				.map(p -> new SimpleGrantedAuthority(p.getDesc()))
				.collect(Collectors.toList());
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean hasRole(Profile profile) {
		return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDesc()));
	}

}
