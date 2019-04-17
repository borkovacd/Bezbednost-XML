package com.ftn.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ftn.service.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true, jsr250Enabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	@Override
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	     return super.authenticationManagerBean();
	 }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	//final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter();
      //  http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);
    	
    	
        http
        
        	.cors().and()
            .authorizeRequests()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/api/security/**").permitAll()
               // .antMatchers("/api/security/**").hasAuthority("ADMIN")
             //   .antMatchers("/api/security/**").hasRole("ADMIN")
            .anyRequest().authenticated();
        
        	//.addFilterBefore(new CustomFilter(), BasicAuthenticationFilter.class);
        
        
          //  .addFilterBefore(new TokenAutheticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);

        http.csrf().disable();
    }

@Bean
CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new 
UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
   }
   }
