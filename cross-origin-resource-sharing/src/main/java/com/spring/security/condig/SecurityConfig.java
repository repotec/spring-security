package com.spring.security.condig;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain config (HttpSecurity httpSecurity) throws Exception {
		//httpSecurity.cors().disable();
		httpSecurity.cors().configurationSource((request) -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(Collections.singletonList("http://localhost:9000"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowCredentials(true);
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setMaxAge(6300L);
			return config;
		})
		.and()
		.authorizeHttpRequests((auth)->{
			auth.antMatchers("/account").authenticated();
			auth.antMatchers("/about").permitAll();
			auth.antMatchers("/h2-console/**").permitAll();
		}).httpBasic(Customizer.withDefaults());
		
		return httpSecurity.build();
	}
}
