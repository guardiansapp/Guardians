package com.guardians.security;

import org.springframework.stereotype.Component;

@Component
public class SecurityRoutes {

	//Tem acesso publico total
	protected static final String[] PUBLIC_MATCHERS = {
		"/h2-console/**", "/index.html", 
	};
	
	//Tem acesso publico apenas para leitura (GET)
	protected static final String[] PUBLIC_MATCHERS_GET = {
		"/notificacoes/usuario-submit/**",
		"/acessos/nomeusuario"
	};
	
	//Tem acesso publico apenas para escrita (POST)
	protected static final String[] PUBLIC_MATCHERS_POST = {
		"/usuarios"
	};
	
}
