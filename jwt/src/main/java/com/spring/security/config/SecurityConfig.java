package com.spring.security.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.spring.security.filter.JwtGeneratorFilter;
import com.spring.security.filter.JwtValidatorFilter;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain config (HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.cors().configurationSource(corsConfigurationSource())
		.and()
		.csrf().disable()
		.addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(new JwtGeneratorFilter(), BasicAuthenticationFilter.class)
        .authorizeHttpRequests((auth)->{
			auth.antMatchers("/account").authenticated();
			auth.antMatchers("/h2-console/**").permitAll();
		}).httpBasic(Customizer.withDefaults());
		
		return httpSecurity.build();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("*"));
        corsConfig.setAllowedMethods(Collections.singletonList("*"));
        corsConfig.setAllowedHeaders(Collections.singletonList("*"));
        corsConfig.setExposedHeaders(Arrays.asList("Authorization"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(6300L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        
        return source;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}