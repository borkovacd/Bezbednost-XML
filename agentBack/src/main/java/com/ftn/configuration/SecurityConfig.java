package com.ftn.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ftn.security.CheckTokenFilter;
import com.ftn.security.RestAuthenticationEntryPoint;
import com.ftn.security.TokenUtils;
import com.ftn.service.AgentService;
import com.ftn.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
    private AgentService agentService;

	@Override
	 public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .userDetailsService(agentService)
            .passwordEncoder(passwordEncoder());
    }
	
	@Bean
    public CheckTokenFilter authenticationFilter() throws Exception {
		CheckTokenFilter authenticationFilter = new CheckTokenFilter();

        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

		// za neautorizovane zahteve posalji 401 gresku
		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
		.authorizeRequests()
		.antMatchers("/api/**").permitAll()

		.anyRequest().authenticated().and()
		
		
		.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http	.csrf().disable();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
