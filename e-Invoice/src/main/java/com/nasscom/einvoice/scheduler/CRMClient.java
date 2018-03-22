package com.nasscom.einvoice.scheduler;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nasscom.einvoice.entity.CRMLiveAllMemberDataWrapper;
import com.nasscom.einvoice.entity.CRMLiveMemberWrapper;
import com.nasscom.einvoice.repository.CityRepository;


@Component
public class CRMClient {
	private static final Logger logger = LoggerFactory.getLogger(CRMClient.class);
	@Autowired
	RestTemplate restTemplate;
	@Value("${nasscom.crm.url}")
	private String crmEndPointUrl;
	@Value("${nasscom.crm.key}")
	private String key;
	@Value("${nasscom.crm.dsmId}")
	private String dsmId;
	@Value("${nasscom.crm.isLive}")
	private Boolean isLive;
	@Autowired
	private CityRepository cityRepository;
	Calendar now = Calendar.getInstance();
	
	public List<String> getAllMembers() {
		logger.debug("Processing getAllMembers from CRM");
		if(isLive) {
		ResponseEntity<CRMLiveMemberWrapper> responseEntity =null;
		HttpHeaders headers = getHeader();
		HttpEntity<String> request = new HttpEntity<String>(headers);
		try {
			ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			responseEntity = restTemplate.exchange(crmEndPointUrl + "getAllMembers",HttpMethod.GET,request,CRMLiveMemberWrapper.class);
		}catch(Exception ex) {
			logger.error("GetAllMembers Error : {} ",ex.getMessage());
			return new ArrayList<>();
		}
		return responseEntity.getStatusCodeValue() == HttpStatus.OK.value() ? responseEntity.getBody().getData().getMemberIdList():null;
		}
		
		else {
			ResponseEntity<String[]> responseEntity =null;
			try {
			responseEntity = restTemplate.getForEntity(crmEndPointUrl + "/get/all",String[].class);
			}catch(Exception ex) {
				logger.error("GetAllMembers Error : {} ",ex.getMessage());
				return new ArrayList<>();
			}
			return responseEntity.getStatusCodeValue() == HttpStatus.OK.value() ? new ArrayList<>(Arrays.asList(responseEntity.getBody())): new ArrayList<>();

		}

	}
	
	public HttpHeaders getHeader()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept","application/json");
		headers.add("Authorization", "bearer "+key);		
		headers.add("dsmId", dsmId);		
		return headers;
	}
	
	public CrmMember getMember(String membershipId) {
		logger.debug("Processing getMember from CRM {} ", membershipId);
		if(isLive) {
		CrmMember member=new CrmMember();
		ResponseEntity<CRMLiveAllMemberDataWrapper> responseEntity = null;
		HttpHeaders headers = getHeader();
		HttpEntity<String> request = new HttpEntity<String>(headers);
		try {
		responseEntity = restTemplate.exchange(crmEndPointUrl + "getMember?memberId="+membershipId,HttpMethod.GET,request,CRMLiveAllMemberDataWrapper.class);
		}catch(Exception ex) {
			logger.error("GetMember Error : {} ",ex.getMessage());
			return null;
		}
		List<List<com.nasscom.einvoice.entity.CRMMember>> crmMember=responseEntity.getBody().getData();
        if(crmMember==null)
            return null;
		Iterator<List<com.nasscom.einvoice.entity.CRMMember>> memberList=crmMember.iterator();
		 com.nasscom.einvoice.entity.Address address = new com.nasscom.einvoice.entity.Address();
		 com.nasscom.einvoice.entity.City city = new com.nasscom.einvoice.entity.City();
		 HashMap<String,String> membershipPlans=new HashMap<String,String>(); 
		 for(com.nasscom.einvoice.entity.CRMMember crmember:memberList.next())
		 {
			 if(crmember.getFieldName().startsWith("Membership Plan:")) 
				 membershipPlans.put(crmember.getFieldName(), crmember.getFieldValue());
			 
			 if(crmember.getFieldName().equalsIgnoreCase("Account Id"))
				 member.setMemberId(Long.valueOf(crmember.getFieldValue())); 
			 
			 if(crmember.getFieldName().equalsIgnoreCase("Email"))
				 member.setEmailId(crmember.getFieldValue()); 
			 
			 if(crmember.getFieldName().equalsIgnoreCase("Membership ID"))
				 member.setMembershipID(crmember.getFieldValue());
			 
			 if(crmember.getFieldName().equalsIgnoreCase("Account Name"))
				 member.setName(crmember.getFieldValue());
			 
			 if(crmember.getFieldName().equalsIgnoreCase("Primary Phone"))
				 member.setMobileNo(crmember.getFieldValue());
			 
			 if(crmember.getFieldName().equalsIgnoreCase("Account Owner"))
				 member.setContactPerson(crmember.getFieldValue());
			 try
			 {
			 if(crmember.getFieldName().equalsIgnoreCase("Date of Joining"))
			 {
				 String str = crmember.getFieldValue();
				 if(null==str || str.equals(""))
				 {
					 throw new ParseException("Empty Date of Joining", 0);
				 }
				 DateFormat formatter = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");    
				 formatter.setTimeZone(TimeZone.getTimeZone("GMT")); 
				 Date today=formatter.parse(str);
				 java.time.LocalDateTime ldt = java.time.LocalDateTime.ofInstant(today.toInstant(), ZoneId.systemDefault());
				 member.setMembershipStart(ldt);
			 }
			 
			 if(crmember.getFieldName().equalsIgnoreCase("Membership Deletion Date"))
			 {
				 String str = crmember.getFieldValue();
				 if(null==str || str.equals(""))
				 {
					 throw new ParseException("Empty Membership End Date", 0);
				 }
				 DateFormat formatter = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");    
				 formatter.setTimeZone(TimeZone.getTimeZone("GMT")); 
				 Date today=formatter.parse(str);
				 java.time.LocalDateTime ldt = java.time.LocalDateTime.ofInstant(today.toInstant(), ZoneId.systemDefault());
				 member.setMembershipEnd(ldt);
			 }
			 }
			 catch(ParseException ex)
			 {
				 logger.debug("ParseException parsing CRM member start and end date",crmember," : ",ex.getMessage()); 
			 }
			 catch(Exception e) {
				 logger.debug("Exception parsing CRM member start and end date",crmember," : ",e.getMessage());
			 }
							 
		     if(crmember.getFieldName().equalsIgnoreCase("Billing Street"))
				 address.setStreet(crmember.getFieldValue());
			
			 if(crmember.getFieldName().equalsIgnoreCase("Billing City"))
				address.setCity(cityRepository.findByNameIgnoreCase(crmember.getFieldValue()));
			 
			 if(crmember.getFieldName().equalsIgnoreCase("Billing Zip"))
				 address.setPin(crmember.getFieldValue());
			  
			 member.setAddress(address);
		 }
		
		 String category = getCategoryForRecentMembershipPlan(membershipPlans);
			 member.setCategory(category);
		return member;}
		
		else {
			ResponseEntity<CrmMember> responseEntity = null;
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		    HttpEntity<String> entity = new HttpEntity<>(headers);
			try {
			//responseEntity = restTemplate.getForEntity(crmEndPointUrl + "/getMemberData?memberId= "+membershipId,CrmMember.class);
			responseEntity = restTemplate.exchange(crmEndPointUrl+ "/getMemberData?memberId="+membershipId, HttpMethod.GET,entity,CrmMember.class);

			}catch(Exception ex) {
				logger.error("GetMember Error : {} ",ex.getMessage());
				return null;
			}
			return responseEntity.getStatusCodeValue() == HttpStatus.OK.value() ? responseEntity.getBody() : null;


		}
	}

	
	
	/**
	 * Returns the category based on most recent financialYear's Membership Plans coming from CRM
	 *Sample membershipPlans' fieldName/Value coming from CRM: 
	 * [fieldName=Membership Plan: FY 2015-16, fieldValue=E]
     * [fieldName=Membership Plan: FY 2016-17, fieldValue=E]
     * [fieldName=Membership Plan: FY 2017-18, fieldValue=E]
	 * @param membershipPlans
	 * @return category
	 */
	private String getCategoryForRecentMembershipPlan(HashMap<String, String> membershipPlans) {
		 Set<Integer> finanStartYearSet=new HashSet<Integer>();
		 for(String fieldName:membershipPlans.keySet()) {
			 if(!("").equals(membershipPlans.get(fieldName)) && !("NA").equalsIgnoreCase(membershipPlans.get(fieldName))){
					int startIndex=	 fieldName.indexOf("FY")+3;
					int endIndex=	 fieldName.indexOf("-");
					if(startIndex !=-1 && endIndex!=-1) {
						Integer finanStartYear = new Integer(fieldName.substring(startIndex, endIndex));
						finanStartYearSet.add(finanStartYear);
					}
			 }
		 }
		 int mostRecentFinanStartYear=Collections.max(finanStartYearSet);
		 int mostRecentFinanEndYear=mostRecentFinanStartYear+1;
		 String mostRecentMembershipPlanKey="Membership Plan: FY "+mostRecentFinanStartYear+""+"-"+(mostRecentFinanEndYear+"").substring(2, 4);
		 String category=membershipPlans.get(mostRecentMembershipPlanKey);
		return category;
	}

	/*public void updateMember(CrmMember member) {
		logger.debug("Processing updateMember to CRM");
		try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<CrmMember> request = new HttpEntity<>(member, headers);
		restTemplate.put(crmEndPointUrl + "/update", request);
		}catch(Exception ex) {
			logger.error("Update CRM Member Error : {} ",ex.getMessage());
			return;	
		}
		logger.debug("UpdatedMember to CRM Completed.");
	}*/
}
