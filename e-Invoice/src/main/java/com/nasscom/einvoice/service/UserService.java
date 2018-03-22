package com.nasscom.einvoice.service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.entity.Role;
import com.nasscom.einvoice.entity.User;
import com.nasscom.einvoice.filter.TokenService;
import com.nasscom.einvoice.repository.BranchRepository;
import com.nasscom.einvoice.repository.RoleRepository;
import com.nasscom.einvoice.repository.UserRepository;


@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	TokenService tokenService;
	//Character array to generate random password for payment 
	char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();
	
	public User findUserByEmail(String emailId) {		
		return userRepository.findByEmailId(emailId);
	}
	
	public User createUser(User user){
		return userRepository.save(user);
	}

	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}

	public Object get(String typeId) {
		return userRepository.findOne(Long.valueOf(typeId));
	}

	public Object getAll() {
		return userRepository.findAll();
	}
	
	public Branch getBranchByName(String name) {
		return branchRepository.findByName(name);
	}
	
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}
	
	public String getTokenForPayment(Member member) {
		//make member as user, if not exist and generate token for payment url
		User user=userRepository.findByEmailId(member.getEmailId());
		if(null==user) {
			Role role = new Role();
			role.setRoleName("MEMBER");
			Set<Role> roles = new HashSet<>();
			roles.add(role);
			user = new User(member.getName(), member.getEmailId(), getRandomPassword(), roles);
			user=userRepository.save(user);
			}
		return tokenService.createTokenForUser(user);
	}
	
	private String getRandomPassword() {
		int randomStrLength=8;
		String randomStr = RandomStringUtils.random( randomStrLength, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
		logger.debug(randomStr); 
		return randomStr;
	}
}
