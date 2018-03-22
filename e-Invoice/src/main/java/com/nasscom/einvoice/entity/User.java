package com.nasscom.einvoice.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This is user table entity
 * 
 * @author vipin.chaudhary1
 *
 */

@Entity
@Table(name = "user")
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "name")
	private String name;
	@Column(name = "email_id", unique = true, nullable = false)
	private String emailId;
	@Column(name = "password", nullable = false)
	private String password;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "branch_id", nullable = true)
	private Branch branch;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setFirstName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(String name, String emailId, String password) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.password = password;
	}

	public User() {
		super();
	}

	public User(String name, String emailId, String password, Set<Role> roles) {
		this(name, emailId,  password);
		this.roles = roles;
	}

}
