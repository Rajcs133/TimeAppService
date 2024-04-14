package com.microservice.timesheet.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

/**
 * OAuth resource configuration.
 *
 * @author Josh Cummings
 */

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class OAuth2ResourceServerSecurityConfiguration {

	@Autowired
	private JwtAuthConverter jwtAuthConverter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		/*
		 * http .authorizeHttpRequests((authorize) -> authorize
		 * .anyRequest().authenticated());
		 */
		
		http
		.authorizeHttpRequests().anyRequest().permitAll();

        http
                .oauth2ResourceServer(server -> server
                        .jwt()
                        .jwtAuthenticationConverter(jwtAuthConverter));

        http
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}

	

}