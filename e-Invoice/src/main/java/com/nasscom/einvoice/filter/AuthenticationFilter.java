package com.nasscom.einvoice.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.JwtException;

public class AuthenticationFilter extends GenericFilterBean {

	private TokenService tokenService=null;

	public AuthenticationFilter(TokenService tokenService) {
		this.tokenService =tokenService;
	}

	@SuppressWarnings("finally")
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		 HttpServletRequest  request  = (HttpServletRequest)  req;
	        HttpServletResponse response = (HttpServletResponse) res;
	        	try {
	                Optional<Authentication> authentication = tokenService.getAuthentication(request);
	                if (authentication.isPresent()) {
	                  SecurityContextHolder.getContext().setAuthentication(authentication.get());
	                }
	                else{
	                  SecurityContextHolder.getContext().setAuthentication(null);
	                }
	                filterChain.doFilter(req, res);
	            }
	            catch (JwtException e) {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().write(e.getMessage());
	    			response.getWriter().flush();
	    			response.getWriter().close();
	    			logger.debug("Valid X-AUTH-TOKEN  Required for Login.");
	            }
	            finally {
	                SecurityContextHolder.getContext().setAuthentication(null);
	                return;  // always return void
	            }
	        }
}
