/**
 * 
 */
package com.devpredator.mspersonaje.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configure Spring Security and authentication methods to the anime's microservice.
 * 
 * @author DevPredator
 * @since v1.0
 * @version v1.0 02/08/2021
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.employee.username.security}")
	String userEmployee;
	
	@Value("${spring.employee.password.security}")
	String passEmployee;
	
	@Value("${spring.admin.username.security}")
	String userAdmin;
	
	@Value("${spring.admin.username.security}")
	String passAdmin;
	
	@Value("${spring.role.employee.security}")
	String roleEmployee;
	
	@Value("${spring.role.admin.security}")
	String roleAdmin;
	
	/**
	 * PATHS that Spring Security has to disable or enable access to someone.
	 */
	private static final String[] AUTHENTICATION_LIST = {
			"/v2/api-docs",
			"/configuration/ui",
			"/swagger-resources",
			"/configuration/resources",
			"/configuration/security",
			"/swagger-ui/index.html",
			"/webjars/**"
	};
	
	/**
	 * Defines the users to access to the microservice documentation.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser(this.userEmployee)
		.password(this.passwordEncoder().encode(this.passEmployee))
		.roles(this.roleEmployee)
		.and()
		.withUser(this.userAdmin)
		.password(this.passwordEncoder().encode(this.passAdmin))
		.roles(this.roleEmployee, this.roleAdmin);
	}

	/**
	 * Defines the paths that the users (EMPLOYEE, ADMIN, etc) can have access.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.csrf().disable()
			.authorizeRequests()
			.and()
			.authorizeRequests()
			.antMatchers(AUTHENTICATION_LIST).authenticated()
			.antMatchers("/personaje/**").authenticated()
			.and()
			.httpBasic();
			
	}
	
	/**
	 * Generates an encoding password with Bcrypt.
	 * 
	 * @return {@link PasswordEncoder} object with the encrypted password.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

}
