package com.nasscom.einvoice.security;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;

import com.nasscom.einvoice.entity.User;
import com.nasscom.einvoice.service.UserService;

/**
 * Custom User detail class responsible for fetching User and iths authorities
 * @author vipin.chaudhary1
 *
 */
public class CustomUserDetailsService implements UserDetailsService {

	private final UserService userService;

	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		try {
			// userName is email id
			User user = userService.findUserByEmail(userName);
			if (user == null) {
				return null;
			}
			return new org.springframework.security.core.userdetails.User(user.getEmailId(),user.getPassword(), getAuthorities(user));
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("User not found");
		}
	}
	public static String base64Encode(String token) {
	    byte[] encodedBytes = Base64.encode(token.getBytes());
	    return new String(encodedBytes, Charset.forName("UTF-8"));
	}


	public static String base64Decode(String token) {
	    byte[] decodedBytes = Base64.decode(token.getBytes());
	    return new String(decodedBytes, Charset.forName("UTF-8"));
	}
	/*
	 * Converts the ROLES in to AUTHORITIES
	 */
	private Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		user.getRoles().forEach((role) -> {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			authorities.add(grantedAuthority);
		});			
		return authorities;
	}

}
