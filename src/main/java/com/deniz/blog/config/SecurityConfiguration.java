package com.deniz.blog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSouce;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		String username = "deniz";
		String password = passwordEncoder.encode("deniz");

		auth.jdbcAuthentication().dataSource(dataSouce).passwordEncoder(getPasswordEncoder());
		auth.inMemoryAuthentication().withUser(username).password(password).roles("ADMIN");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/login", "/css/**", "/images/**", "/js/**", "/sass/**", "/fonts/**").permitAll();
		http.authorizeRequests().antMatchers("/admin/**").authenticated();
		http.authorizeRequests().antMatchers("/**").permitAll();
		http.authorizeRequests().anyRequest().authenticated().and().exceptionHandling().accessDeniedPage("/403");
		
		http.formLogin().loginPage("/admin/login").loginProcessingUrl("/admin/login").failureUrl("/admin/login?loginFailed=true");
		http.formLogin().defaultSuccessUrl("/admin/");
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
