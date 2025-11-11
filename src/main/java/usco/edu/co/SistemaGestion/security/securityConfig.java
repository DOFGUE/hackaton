package usco.edu.co.SistemaGestion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class securityConfig {
	
	/**
	 * Codificador de contraseñas con BCrypt
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Configuración de cadena de filtros de seguridad
	 * 
	 * ESTRUCTURA DE ROLES:
	 * - SOLICITANTE: Absorbe DOCENTE, FUNCIONARIO, ESTUDIANTE_AUTORIZADO
	 * - OFICINA_MANTENIMIENTO: Gestiona requerimientos
	 * - JEFE_PROGRAMADOR_MANTENIMIENTO: Asigna tareas a operarios
	 * - OPERARIO: Ejecuta tareas de mantenimiento
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authz -> authz
				.requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/resources/**").permitAll()
				.requestMatchers("/solicitante", "/solicitante/**")
					.hasAnyRole("SOLICITANTE", "DOCENTE", "FUNCIONARIO", "ESTUDIANTE_AUTORIZADO")
				.requestMatchers("/oficina-mantenimiento", "/oficina-mantenimiento/**")
					.hasRole("OFICINA_MANTENIMIENTO")
				.requestMatchers("/jefe-programador-mantenimiento", "/jefe-programador-mantenimiento/**")
					.hasRole("JEFE_PROGRAMADOR_MANTENIMIENTO")
				.requestMatchers("/operario", "/operario/**")
					.hasRole("OPERARIO")
				.requestMatchers("/dashboard", "/dashboard/**").authenticated()
				.anyRequest().authenticated()
			)
			.formLogin(login -> login
				.loginPage("/login")
				.usernameParameter("nombreUsuario")
				.passwordParameter("contrasena")
				.defaultSuccessUrl("/dashboard", true)
				.failureUrl("/login?error=true")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
			)
			.headers(headers -> headers
				.addHeaderWriter(new StaticHeadersWriter(
					"Cache-Control", "no-cache, no-store, must-revalidate"
				))
				.addHeaderWriter(new StaticHeadersWriter(
					"Pragma", "no-cache"
				))
				.addHeaderWriter(new StaticHeadersWriter(
					"Expires", "0"
				))
				.frameOptions(frameOptions -> frameOptions.sameOrigin())
				.xssProtection()
			)
			.sessionManagement(session -> session
				.maximumSessions(1)
				.expiredUrl("/login?expired=true")
			)
			.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
}