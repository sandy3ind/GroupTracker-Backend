package com.samyuktatech.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

	
	/**
	 * Api Authentication
	 * 
	 * @author sandeepsharma
	 *
	 */
	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {	
		
		@Autowired
		private AuthFailureHandler authFailureHandler;
		
		@Override
	    protected void configure(HttpSecurity http) throws Exception {
//			http.csrf().disable()
//				.requestMatchers()
//					.antMatchers("/user/**")
//				.and()
//				.addFilterBefore(customBasicAuthenticationFilter(), BasicAuthenticationFilter.class)
//		  		.authorizeRequests()		  			
//		  			.antMatchers(HttpMethod.POST, "/user").permitAll()
//			  		.anyRequest().authenticated();
			
			http.csrf().disable()
			.requestMatchers()
				.antMatchers("/user/**")
			.and()
	  		.authorizeRequests()
		  		.anyRequest().permitAll();
		  		
	    }	
		
		// Custom basic authentication filter
		@Bean
		public CustomBasicAuthenticationFilter customBasicAuthenticationFilter() throws Exception {
			CustomBasicAuthenticationFilter filter = new CustomBasicAuthenticationFilter(super.authenticationManager(),
					authFailureHandler);		
			return filter;
		}
	}
	
	/**
	 * Form login Authentication
	 * 
	 * @author sandeepsharma
	 *
	 */
	@Configuration                                                   
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
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
		  		.authorizeRequests()
			  	.and()
			  		.formLogin()
			  		.loginProcessingUrl("/login")
					.usernameParameter("username")
					.passwordParameter("password")
					.successHandler(authSuccessHandler)
					.failureHandler(authFailureHandler)					
					.permitAll();
		  		
	    }
		
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
