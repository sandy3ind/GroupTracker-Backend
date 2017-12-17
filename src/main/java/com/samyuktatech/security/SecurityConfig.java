package com.samyuktatech.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthSuccessHandler authSuccessHandler;
	
	@Autowired
	private AuthFailureHandler authFailureHandler;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterBefore(customBasicAuthenticationFilter(), BasicAuthenticationFilter.class)
	  		.authorizeRequests()
	  			.antMatchers(HttpMethod.POST, "/user").permitAll()
		  		.anyRequest().authenticated()
		  	.and()
		  		.formLogin()
		  		.loginProcessingUrl("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(authSuccessHandler)
				.failureHandler(authFailureHandler)					
				.permitAll();
	  		
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
	// Custom basic authentication filter
	@Bean
	public CustomBasicAuthenticationFilter customBasicAuthenticationFilter() throws Exception {
		CustomBasicAuthenticationFilter filter = new CustomBasicAuthenticationFilter(super.authenticationManager(),
				authFailureHandler());		
		return filter;
	}
	
	// Login Failure handler
	@Bean
	public AuthFailureHandler authFailureHandler() {
		return new AuthFailureHandler();
	}
	
	// Login Success handler
	@Bean
	public AuthSuccessHandler authSuccessHandler() {
		return new AuthSuccessHandler();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public AccessTokenStore accessTokenStore() {
		return new AccessTokenStore();
	}
	
	
}
