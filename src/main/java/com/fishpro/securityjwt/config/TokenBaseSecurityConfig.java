package com.fishpro.securityjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import com.fishpro.securityjwt.exception.JwtAuthenticationEntryPoint;
import com.fishpro.securityjwt.filter.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@Order(1)
public class TokenBaseSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity
			.antMatcher("/api/**")
				.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/api/authenticate").permitAll()
				
				.antMatchers("/api/users").hasRole("USER")
				
				// all other requests need to be authenticated
				.anyRequest().authenticated().and()
				
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.exceptionHandling()
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
