package com.nasscom.einvoice.service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nasscom.einvoice.entity.Address;
import com.nasscom.einvoice.entity.Branch;
import com.nasscom.einvoice.entity.CategoryFee;
import com.nasscom.einvoice.entity.City;
import com.nasscom.einvoice.entity.CityRegionMapping;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.entity.SenderDetail;
import com.nasscom.einvoice.repository.BranchRepository;
import com.nasscom.einvoice.repository.CategoryFeeRepository;
import com.nasscom.einvoice.repository.CityRegionMappingRepository;
import com.nasscom.einvoice.repository.CityRepository;
import com.nasscom.einvoice.repository.MemberRepository;
import com.nasscom.einvoice.repository.SenderDetailRepository;
import com.nasscom.einvoice.scheduler.CRMClient;
import com.nasscom.einvoice.scheduler.CrmMember;

@Service
public class MemberService {
	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	CRMClient crmClient;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	CityRegionMappingRepository cityRegionMappingRepository;
	@Autowired
	SenderDetailRepository senderDetailRepository;
	@Autowired
	CategoryFeeRepository categoryFeeRepository;
	enum Status {
		MANUAL, AUTO, NEW, UPDATE, DELETE
	}

	public Member getMemberById(Long memberId) {
		return memberRepository.getOne(memberId);
	}

	public Member saveMember(Member member) {
		return memberRepository.save(member);
	}

	public Member getMemberByEmailId(String email) {
		return memberRepository.findByEmailId(email);
	}

	public Page<Member> findPaginated(int page, int size) {
		return memberRepository.findByIsActive(true,new PageRequest(page, size));
	}

	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	public List<Member> getMemberList(String[] membershipId) {
		if (membershipId.length == 0) {
			return null;
		}
		List<Member> memberList = new ArrayList<Member>();
		for (int i = 0; i < membershipId.length; i++) {
			Member m = memberRepository.findByMembershipID(membershipId[i]);
			if (null != m)
				memberList.add(m);
		}
		return memberList;
	}

	public List<Member> syncMemberManual(String[] membershipId) {
		return syncMembers(getMemberList(membershipId), Status.MANUAL, Status.UPDATE);
	}

	private List<Member> syncMembers(List<Member> members, Status updatedBy, Status recordType) {
		if (members.isEmpty())
			return members;
		List<Member> updatedMembers = new ArrayList<>();
		switch (recordType) {
		case DELETE: {
			members.forEach(member -> {
				member.setIsActive(false);
			});
			logger.info("Marking Members inActive => {} ", members);
			updatedMembers.addAll(members);
			break;
		}
		case UPDATE: {
			members.forEach(member -> {
				try {
					logger.debug("Invoice sys Member  => {}", member);
					CrmMember crmMember = crmClient.getMember(member.getMembershipID());
					if (null != crmMember) {
						logger.info("Crm Member => {} ", crmMember);
						member.setEmailId(crmMember.getEmailId());
						member.setName(crmMember.getName());
						member.setMembershipID(crmMember.getMembershipID());
						member.setContactPerson(crmMember.getContactPerson());
						member.setMobileNo(crmMember.getMobileNo());
						member.setCategory(crmMember.getCategory());
						member.setMembershipStart(crmMember.getMembershipStart());
						member.setMembershipEnd(crmMember.getMembershipEnd());
//						if (null == member.getCalculatedSubscription() || member.getCalculatedSubscription() == 0
//								|| member.getCategory() != crmMember.getCategory()) {
//							CategoryFee categoryFee = categoryFeeRepository
//									.findByCategory(crmMember.getCategory());
//							if (null != categoryFee)
//								member.setCalculatedSubscription(categoryFee.getFee());
//							else {
//								logger.debug("New Category detected, No mapping fee found in master db, so assign zero");
//								member.setCalculatedSubscription(0.0);
//							}
//						}
						setAddress(member, crmMember);
						member.setModifiedBy(updatedBy.name());
						member.setIsActive(true);
						logger.debug("Updated Member => {}", member);
					} else {
						logger.info("Member Not available on CRM, might be remove so making inactive in system ,{}",
								member);
						member.setIsActive(false);
					}
				} catch (Exception ex) {
					logger.error("Member Not sync, due to error : {},\n {}", ex.getMessage(), ex);
				}
				updatedMembers.add(member);
			});
			break;
		}
		case NEW: {
			logger.info("Members ready to Insert =>{} ", members.size());
			updatedMembers.addAll(members);
			break;
		}
		default:
			break;
		}
		return memberRepository.save(updatedMembers);
	}

	private Address setAddress(Member eivoiceMember, CrmMember crmMember) {
			City city = cityRepository.findByName((crmMember.getAddress().getCity()!=null)?crmMember.getAddress().getCity().getName():"");
			if (null != city)//this means set city in memberAddress only if it exists in City master
				eivoiceMember.getAddress().setCity(city);
			eivoiceMember.getAddress().setFax(crmMember.getAddress().getFax());
			eivoiceMember.getAddress().setPin(crmMember.getAddress().getPin());
			eivoiceMember.getAddress().setStreet(crmMember.getAddress().getStreet());
			eivoiceMember.getAddress().setPhone(crmMember.getAddress().getPhone());
			eivoiceMember.getAddress().setType(Address.Type.MEMBER);
			return eivoiceMember.getAddress();
	}
	
	@Transactional
	public void autoSyncMember() {
		try {
			Set<String> membershipIds = memberRepository.findAllByMembershipID();
			List<String> crmMembershipIds = crmClient.getAllMembers();
			logger.info("crmMembershipIds Actual size "+crmMembershipIds.size());
			if(!crmMembershipIds.isEmpty()) {
				if (!membershipIds.isEmpty()) {					
					//interSectionMemberIds have Ids of those member which are available in both CRM and database. 
					Set<String> interSectionMembersIds = new HashSet<>(membershipIds);
					interSectionMembersIds.retainAll(crmMembershipIds);
					logger.info("Existing Members ready to be updated from CRM update =>{} ", interSectionMembersIds.size());
		
					//deleteMemberIds have Ids of those member which are available in database but not in CRM. 
					Set<String> deletedMembersIds = new HashSet<>(membershipIds);
					deletedMembersIds.removeAll(interSectionMembersIds);
					logger.info("No longer active Members ready to be soft deleted(isActive:false) from Nasscom DB  =>{} ", deletedMembersIds.size());
					
					//newMemberIds have Ids of those member which are available in CRM but not in database. 
					List<String> newMembersIds = new ArrayList<>(crmMembershipIds);
					newMembersIds.removeAll(interSectionMembersIds);
					logger.info("New Members ready to be added from CRM =>{} ", newMembersIds.size());
					
					if (deletedMembersIds !=null && deletedMembersIds.size() > 0)
						syncMembers(getMemberList(deletedMembersIds.toArray(new String[0])), Status.AUTO, Status.DELETE);
					
					if (interSectionMembersIds !=null && interSectionMembersIds.size() > 0)
						syncMembers(getMemberList(interSectionMembersIds.toArray(new String[0])), Status.AUTO,
								Status.UPDATE);

					if (newMembersIds !=null && newMembersIds.size() > 0)
						syncMembers(getFreshMembers(newMembersIds, Status.AUTO), Status.AUTO, Status.NEW);	
					
				} else {//if database is empty,then fetch all fresh members
					logger.info("Nasscom Database is empty thus fetch all fresh members");
					logger.debug("Total member availabe on CRM : " + crmMembershipIds.size());
					if (!crmMembershipIds.isEmpty())
						syncMembers(getFreshMembers(crmMembershipIds, Status.AUTO), Status.AUTO, Status.NEW);

				}
			} else {
				logger.warn("No Data Found in CRM.Sync from CRM not possible");
				if (!membershipIds.isEmpty())
					logger.debug("Total Existig member availabe on Nasscom Database : " + membershipIds.size());
			}
		} catch (RollbackException ex) {
			logger.error("Member Not sync, rollback txn : {},\n {}", ex.getMessage(), ex);
		}
		catch (Exception ee) {
			logger.error("Member Not sync, Exception : {},\n {}", ee.getMessage(), ee);
		}
	}

	private List<Member> getFreshMembers(List<String> memberIds, Status updatedBy) {
		List<Member> newMembers = new ArrayList<>();
		try {
			memberIds.forEach(memberId -> {
				CrmMember crmMember = crmClient.getMember(memberId);
				Member member = new Member(crmMember.getEmailId(), crmMember.getMembershipID(), crmMember.getName(),
						crmMember.getMobileNo(), crmMember.getCategory(), crmMember.getContactPerson(),
						crmMember.getMembershipStart(), crmMember.getMembershipEnd(),
						new Address());
				member.setModifiedBy(updatedBy.name());
				setAddress(member, crmMember);
				member.setIsActive(true);
//				CategoryFee categoryFee = categoryFeeRepository
//						.findByCategory(crmMember.getCategory());
//				if (null != categoryFee)
//					member.setCalculatedSubscription(categoryFee.getFee());
//				else {
//					logger.debug("New Category detected, No mapping fee found in master db, so assign zero");
//					member.setCalculatedSubscription(0.0);
//				}
				newMembers.add(member);
			});
			logger.debug("Total members fetched from CRM : {} ", newMembers.size());
		} catch (Exception ex) {
			logger.error("Member Not fetched from CRM, due to error : {},\n {}", ex.getMessage(), ex);
		}
		return newMembers;
	}
	
	public Double getSubscription(Member member)
	{
		CategoryFee categoryFee = categoryFeeRepository
				.findByCategory(member.getCategory());
		return (null != categoryFee)?categoryFee.getFee():null;
	}

	public List<Character> findDistinctByCategory() {
		return memberRepository.findDistinctByCategory();
	}

	public List<Member> findByCategory(String category) {
		return memberRepository.findByCategory(category);
	}

//	public void updateSubscription(Character category, String memberId, Double subscriptionFee) {
//		if (null != category && category != 0 && !memberId.isEmpty()) {
//			if ("ALL".equalsIgnoreCase(memberId)) {
//				List<Member> members = memberRepository.findByCategory(Character.toString(category));
//				members.forEach(member -> {
//					Double fee = categoryFeeRepository.findByCategory(Character.toString(category)).getFee();
//					member.setManualSubscription(fee);
//					member.setCalculatedSubscription(fee);
//				});
//				memberRepository.save(members);
//			} else {
//				Member member = memberRepository.findByEmailId(memberId);
//				// in case of single member update
//				member.setManualSubscription(subscriptionFee);
//				memberRepository.save(member);
//			}
//		}
//	}
	
	/**
	 * Update subscription on Category update
	 * @param category
	 * @param fee
	 */
//	public void updateMemberSubscription(String category,Double fee) {
//		List<Member> members = memberRepository.findByCategory(category);
//		members.forEach(member -> {
//			member.setCalculatedSubscription(fee);
//		});
//		memberRepository.save(members);
//	}
	
	public Branch getBranch(Member member) {
        Assert.notNull(member, "Member can not be null. Branch Not found. ");
		Branch branch = null ;
		CityRegionMapping cityRegionMapping=null;
		try {
			City city = member.getAddress().getCity();
			Assert.notNull(city, "The member's address is not mapped to city--> "+member.getAddress());
			cityRegionMapping=cityRegionMappingRepository.findByCityId(city.getId());
			Assert.notNull(cityRegionMapping, "The cityRegionMapping not found for the given member's city "+city);
			branch=cityRegionMapping.getBranch();
			Assert.notNull(branch, "The  Branch/Region not found for the given member's city "+city);
		} catch (Exception e) {
			logger.error("Exception encountered while getting Nasscom Branch of a Member "+e);
		}
		return branch;
	}
	
	public List<Branch> getBranches() {
		return branchRepository.findAll();
	}

	public List<SenderDetail> getSenderDetails() {
		return senderDetailRepository.findAll();
	}	
	
	}
