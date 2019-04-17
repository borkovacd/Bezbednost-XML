package com.ftn.configuration;

import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter 

{
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
	 
	 private Connector getHttpConnector() 
	 {
		 Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		 connector.setScheme("http");
		 connector.setPort(8080);
		 connector.setSecure(false);
		 connector.setRedirectPort(8443);
		 return connector;
		 }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        
        	.cors().and()
            .authorizeRequests()
                .antMatchers("/api/**").permitAll();
        
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
