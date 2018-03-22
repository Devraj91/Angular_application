package com.nasscom.einvoice.filter;

import org.springframework.security.core.authority.AuthorityUtils;

import com.nasscom.einvoice.entity.User;

public class TokenUser extends org.springframework.security.core.userdetails.User {
    private User user;

    //For returning a normal user
    public TokenUser(User user) {
    	
        super( user.getEmailId(), user.getPassword(), AuthorityUtils.createAuthorityList("ADMIN") );
        //super(user.getUserName(), user.getPassword(), true, true, true, true,  AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
        System.out.println("user role"+user.getRoles().size());
        System.out.println("user role"+user.getRoles().toArray());
    }

    public User getUser() {
        return user;
    }

    public String getRole() {
        return user.getRoles().toString();
    }
}
