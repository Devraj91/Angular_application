package com.nasscom.einvoice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.entity.User;

@RepositoryRestResource
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<User> {

	Member findByMemberId(Long memberId);

	Member findByEmailId(String emailId);
	
	Member findByMembershipID(String membershipId);

	@Query("Select m.emailId from Member m")
	Set<String> findAllByEmailId();
	
	@Query("Select m.membershipID from Member m")
	Set<String> findAllByMembershipID();

	@Query("Select distinct m.category from Member m")
	List<Character> findDistinctByCategory();
	
	@Query("Select m  from Member m where m.category=?")
	List<Member> findByCategory(String category);

	List<Member> findByAddressCity(City city);
	
	Page<Member> findByIsActive(Boolean isActive, Pageable pageable);
	
	List<Member> findByIsActive(Boolean isActive);
	
    Member findByEmailIdAndIsActive(String emailId, Boolean isActive);
	
    Member findByMembershipIDAndIsActive(String membershipID, Boolean isActive); 
}
