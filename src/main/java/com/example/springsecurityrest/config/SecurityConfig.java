package com.example.springsecurityrest.config;

import com.example.springsecurityrest.exception.CustomAccessDeniedHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@SuppressWarnings("unused")
	private UserDetailsService userDetailsService;
	@Autowired
			@Qualifier("customAuthenticationEntryPoint")
	AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	@Lazy
	private JwtAuthenticationFilter jwtRequestFilter;

	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
			throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests((authorize) ->
				{
					try {
						authorize
								.requestMatchers("/api/auth/**").permitAll()
								.requestMatchers(HttpMethod.GET, "/api/product").authenticated()
								.requestMatchers(HttpMethod.POST, "/api/product").hasRole("ADMIN")
								.requestMatchers(HttpMethod.PUT, "/api/product").hasRole("ADMIN")
								.requestMatchers(HttpMethod.DELETE, "/api/product/{productId}").hasRole("ADMIN")

								.anyRequest().authenticated()
								.and()
//								.httpBasic()
//								.and()
								.exceptionHandling()
								.authenticationEntryPoint(authenticationEntryPoint)
								.accessDeniedHandler(new CustomAccessDeniedHandler())
						;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
		)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
