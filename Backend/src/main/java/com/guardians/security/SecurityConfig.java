package com.guardians.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.guardians.security.jwt.JWTAuthenticationFilter;
import com.guardians.security.jwt.JWTAuthorizationFilter;
import com.guardians.security.jwt.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		
		//Para liberar o h2 caso o perfil de teste esteja habilitado
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors()	//Se definido um corsConfiguration ele irá usa-lo
			.and().csrf().disable();	//Desativa proteção contra ataques csrf pois a aplicação não guarda sessões
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, SecurityRoutes.PUBLIC_MATCHERS_GET).permitAll()	//Permite métodos get apenas
			.antMatchers(HttpMethod.POST, SecurityRoutes.PUBLIC_MATCHERS_POST).permitAll()	//Permite métodos post apenas
			.antMatchers(SecurityRoutes.PUBLIC_MATCHERS).permitAll()						//Permite todos os métodos
			.mvcMatchers("/").permitAll()
			.anyRequest().authenticated();													//Qualquer outro precisará ser autenticado
		
		//Adiciona o filtro de autenticação definido no arquivo JWTAutenticationFilter
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		
		//Adiciona o filtro de autorização definido no arquivo JWTAuthorizationFilter
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Impede a aplicação de guardar sessões
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		super.configure(auth);
		
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowCredentials(false);
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
