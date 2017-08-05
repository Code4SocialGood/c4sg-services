package org.c4sg.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Value("${auth0.audience}")
  private String audience;

  @Value("${auth0.issuer}")
  private String issuer;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    JwtWebSecurityConfigurer
    .forRS256(audience, issuer)
    .configure(http)
    .authorizeRequests()
    .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
    .antMatchers(HttpMethod.GET, "/api/organizations/**").permitAll()
    .antMatchers(HttpMethod.GET, "/api/skills/**").permitAll()
    .antMatchers(HttpMethod.GET, "/api/projects/**").permitAll()
    .antMatchers(HttpMethod.GET, "/api/chat/**").permitAll()
    .antMatchers(HttpMethod.POST, "/api/users").hasAnyAuthority("VOLUNTEER","ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.POST, "/api/projects").hasAnyAuthority("ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.POST, "/api/organizations/**").hasAnyAuthority("ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.PUT, "/api/users/**").hasAnyAuthority("VOLUNTEER","ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.PUT, "/api/projects/**").hasAnyAuthority("ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.PUT, "/api/organizations/**").hasAnyAuthority("ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.PUT, "/api/skills/user/skills/**").hasAnyAuthority("VOLUNTEER","ADMIN","ORGANIZATION")    
    .antMatchers(HttpMethod.PUT, "/api/skills/project/skills/**").hasAnyAuthority("ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyAuthority("VOLUNTEER","ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.DELETE, "/api/projects/**").hasAnyAuthority("ADMIN","ORGANIZATION")
    .antMatchers(HttpMethod.DELETE, "/api/organizations/**").hasAnyAuthority("ADMIN","ORGANIZATION")
    .anyRequest().authenticated();
  }
  
  @Override
  public void configure(WebSecurity web) throws Exception {
      web.ignoring()
      	.antMatchers("/v2/api-docs", "/configuration/ui", 
    		  "/swagger-resources", "/configuration/security", 
    		  "/swagger-ui.html", "/webjars/**")
      	.antMatchers(HttpMethod.OPTIONS);
  }
}
