package com.valerko.lgs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.valerko.lgs.security.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailService();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//		UserDetails admin = User.builder().username("admin").password(encoder.encode("admin")).build();
//		UserDetails user = User.builder().username("user").password(encoder.encode("user")).build();		
//		return new InMemoryUserDetailsManager(admin, user);
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)				
				.authorizeHttpRequests(auth -> auth							
						.requestMatchers("/api/**")
						.authenticated()
						.requestMatchers("/**")
						.permitAll()
						)
				.formLogin(formlogin ->formlogin
						.loginPage("/login")						
						.defaultSuccessUrl("/api/home",true)
						.failureUrl("/login?error=true")
						.permitAll());
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
