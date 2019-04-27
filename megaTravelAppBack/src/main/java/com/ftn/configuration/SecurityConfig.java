package com.ftn.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter 

{
	@Override
	@Bean
	 public AuthenticationManager authenticationManager() throws Exception {
	     return super.authenticationManager();
	 }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder();
	}

	 /*
	 @Bean
	 public ServletWebServerFactory  servletContainer() {
		 TomcatServletWebServerFactory  tomcat = new TomcatServletWebServerFactory() {
		 @Override
		 protected void postProcessContext(Context context) {
			 SecurityConstraint securityConstraint = new SecurityConstraint();
			 securityConstraint.setUserConstraint("CONFIDENTIAL");
			 SecurityCollection collection = new SecurityCollection();
			 collection.addPattern("/*");
			 securityConstraint.addCollection(collection);
			 context.addConstraint(securityConstraint);
			 }
			 };
		 tomcat.addAdditionalTomcatConnectors(getHttpConnector());
		 return tomcat;
	 }
	 
	 */
	 
	 /*
	 private Connector getHttpConnector() {
		 Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		 connector.setScheme("http");
		 connector.setPort(serverPortHttp);
		 connector.setSecure(false);
		 connector.setRedirectPort(serverPortHttps);
		 return connector;
	}
	*/
	 /*
	 private Connector getHttpConnector() 
	 {
		 Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		 connector.setScheme("http");
		 connector.setPort(8080);
		 connector.setSecure(false);
		 connector.setRedirectPort(8443);
		 return connector;
	}
	*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	//final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter();
      //  http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);
    	
    	
        http
        
        	.cors().and()
            .authorizeRequests()
                .antMatchers("/api/**").permitAll()
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
