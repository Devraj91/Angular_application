package com.nasscom.einvoice.filter;

import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.*;
import com.nasscom.einvoice.entity.User;

public class GenerateTokenForUserFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger logger = LoggerFactory.getLogger(GenerateTokenForUserFilter.class);

	private TokenService tokenService;

	public GenerateTokenForUserFilter(String urlMapping, AuthenticationManager authenticationManager,
			TokenService tokenService) {
		super(new AntPathRequestMatcher(urlMapping));
		setAuthenticationManager(authenticationManager);
		this.tokenService = tokenService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		try {
			String jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");
			JSONObject userJSON = new JSONObject(jsonString);
			String username = userJSON.getString("username");
			String password = userJSON.getString("password");
			logger.info("username:{} and pasword:{} \n", username, password);
			final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
					password);
			return getAuthenticationManager().authenticate(authToken);
		} catch (JSONException | AuthenticationException e) {
			SessionResponse resp = new SessionResponse();
			SessionItem respItem = new SessionItem();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			logger.info("Error Type :", e.getCause());
			if (e instanceof AuthenticationException) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				response.setStatus(HttpServletResponse.SC_OK);
			}
			resp.setOperationStatus(SessionResponse.ResponseStatus.ERROR);
			resp.setOperationMessage("Login Failed");
			resp.setItem(respItem);
			String jsonRespString = ow.writeValueAsString(resp);
			response.getWriter().write(jsonRespString);
			response.getWriter().flush();
			response.getWriter().close();
			throw new AuthenticationServiceException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication authToken) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authToken);
		org.springframework.security.core.userdetails.User tokenUser = (org.springframework.security.core.userdetails.User) authToken
				.getPrincipal();
		SessionResponse resp = new SessionResponse();
		SessionItem respItem = new SessionItem();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String tokenString = tokenService.createTokenForUser(
				new User(tokenUser.getUsername(), tokenUser.getUsername(), tokenUser.getPassword()));

		respItem.setFirstName(tokenUser.getUsername());
		respItem.setLastName(tokenUser.getUsername());
		respItem.setUserId(tokenUser.getUsername());
		respItem.setEmail(tokenUser.getUsername());
		respItem.setToken(tokenString);
		// respItem.setRoles(tokenUser.getAuthorities());

		resp.setOperationStatus(SessionResponse.ResponseStatus.SUCCESS);
		resp.setOperationMessage("Login Success");
		resp.setItem(respItem);
		String jsonRespString = ow.writeValueAsString(resp);

		res.setStatus(HttpServletResponse.SC_OK);
		res.getWriter().write(jsonRespString);
		res.getWriter().flush();
		res.getWriter().close();
	}
}
