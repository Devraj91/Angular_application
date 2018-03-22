package com.nasscom.einvoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nasscom.einvoice.entity.InvoiceDetail;
import com.nasscom.einvoice.entity.Member;
import com.nasscom.einvoice.entity.User;

@RepositoryRestResource
public interface InvoiceRepository extends JpaRepository<InvoiceDetail, Long>, JpaSpecificationExecutor<User> {

	InvoiceDetail findByInvoiceId(Long invoiceId);

	@Query("select i FROM InvoiceDetail i where i.member=? order by i.created desc ")
	List<InvoiceDetail> findByMemberByOrderByCreated(Member member);
	
	//@Query("select i FROM InvoiceDetail i where i.invoiceFile.isCancel=false order by i.created desc ")
	@Query("select i FROM InvoiceDetail i order by i.created desc ")
	List<InvoiceDetail> findInvoiceDetailsOrderByCreated();
	
	List<InvoiceDetail> findByMemberAndYearOrderByCreated(Member member,int year);
	
	InvoiceDetail findByMemberAndYear(Member member,String currentFinYear);
	
	InvoiceDetail findByToEmail(String toEmail);
}
