package com.nasscom.einvoice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.session.web.http.HeaderHttpSessionStrategy;
//import org.springframework.session.web.http.HttpSessionStrategy;

import com.nasscom.einvoice.filter.AuthenticationFilter;
import com.nasscom.einvoice.filter.CorsFilter;
import com.nasscom.einvoice.filter.GenerateTokenForUserFilter;
import com.nasscom.einvoice.filter.TokenService;
import com.nasscom.einvoice.service.UserService;
/**
 * Enables the security for the application
 * 
 */

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        // Filters will not get executed for the resources
	        web.ignoring().antMatchers("/","/resources/**", "/static/**", "/public/**", "/assets/**", "/h2-console/**"
	            , "/configuration/**", "/*.html", "/**.html" ,"*.css","/**.js","*.png","*.jpg", "*.gif", "*.svg", "*.ico", "/**.ttf","*.woff")
	        .antMatchers("/user/submitforgotPassword")
	        .antMatchers("/user/submitNewPassword")
	        .antMatchers("/invoice/checkUrlExpiry/*")
	        .antMatchers("/invoice/payment/ccavanuereq")
	        .antMatchers("/invoice/payment/**")
	        .antMatchers("/invoice/paymentDetails/**");
	    }
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.formLogin().disable() // disable form authentication
        .anonymous().disable() // disable anonymous user
        .authorizeRequests().anyRequest().denyAll().and()
        .exceptionHandling().and()
        .anonymous().and()
        // Disable Cross site references
        .csrf().disable()
		// Add CORS Filter
        .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
        // Custom Token based authentication based on the header previously given to the client
        .addFilterBefore(new AuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
		 // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
        .addFilterBefore(new GenerateTokenForUserFilter ("/session", authenticationManager(), tokenService), UsernamePasswordAuthenticationFilter.class)	
		.authorizeRequests()
//		.antMatchers("/invoice/*").hasAuthority("ADMIN")
//		.and()	
//      .authorizeRequests()
//		.antMatchers("/user/*").hasAuthority("MEMBER")
        .anyRequest().authenticated();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceBean());
	}
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new CustomUserDetailsService(userService);
	}

}
