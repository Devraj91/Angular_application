package com.nasscom.einvoice;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.nasscom.einvoice.entity.Role;
import com.nasscom.einvoice.entity.User;
import com.nasscom.einvoice.repository.UserRepository;


@SpringBootApplication
//@EnableScheduling
public class EInvoiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(EInvoiceApplication.class, args);
	}

	@Autowired
	public UserRepository repo;

	@PostConstruct
	public void loadMasterData() {
		//master credential 
		Role role = new Role();
		role.setRoleName("ADMIN");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		User user = new User("Admin", "admin@nasscom.in", "admin123", roles);
		if(repo.findByEmailId(user.getEmailId())==null)
		repo.save(user);
	}
}
