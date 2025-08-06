package com.sayan.blog.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sayan.blog.app.security.JWTAuthenticationEntryPoint;
import com.sayan.blog.app.security.JWTAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JWTAuthenticationEntryPoint entryPoint;

	@Autowired
	private JWTAuthenticationFilter filter;

	@Autowired
	private DaoAuthenticationProvider authenticationProvider;


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll()
						.requestMatchers(HttpMethod.GET).permitAll()
						// .anyRequest().hasRole("ADMIN")
						.anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		http.authenticationProvider(authenticationProvider);

		return http.build();
	}

}
