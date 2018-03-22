package com.nasscom.einvoice.filter;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.User;
import com.nasscom.einvoice.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	// private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000;// 10
	// days Validity
	private static final long VALIDITY_TIME_MS = 2 * 60 * 60 * 1000; // 2 hours validity

	@Value("${security.oauth2.resource.jwt.key-value}")
	String secretKey;
	@Autowired
	private UserService userService;

	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final User user = authentication.getDetails();
		response.addHeader(AUTH_HEADER_NAME, this.createTokenForUser(user));
		logger.debug("Token generated");
	}

	public Optional<Authentication> getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final User user = this.parseUserFromToken(token);
			if (user != null) {
				return Optional.of(new UserAuthentication(user));
			}
			logger.debug("Token validated");
		} 
		return Optional.empty();
	}

	/**
	 * Get username and password value from incoming token using jwt
	 */
	public User parseUserFromToken(String token) {
		Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		if (null != body) {
			User user = userService.findUserByEmail(body.getSubject());
			if (user != null) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Create token using jwt based on username and password
	 */
	public String createTokenForUser(User user) {
		Claims claims = Jwts.claims().setSubject(user.getEmailId());
		claims.setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS));
		claims.put("password", user.getPassword());
		claims.put("uid", UUID.randomUUID());
		claims.put("time", System.currentTimeMillis());
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}
}