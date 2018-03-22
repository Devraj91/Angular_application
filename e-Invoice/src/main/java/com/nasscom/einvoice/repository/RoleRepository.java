package com.nasscom.einvoice.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{
	Role findByName(String name);
}
