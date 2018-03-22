package com.nasscom.einvoice.filter;

import java.util.Collection;

import javax.security.auth.Subject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.nasscom.einvoice.entity.User;

public class UserAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;
	private final User user;
	private boolean authenticated = true;

	public UserAuthentication(User user) {
		this.user = user;
	}

	@Override
	public String getName() {
		return user.getEmailId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public User getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user.getEmailId();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	@Override
	public boolean implies(Subject arg0) {
		return false;
	}
}