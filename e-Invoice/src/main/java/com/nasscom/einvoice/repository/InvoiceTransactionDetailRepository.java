package com.nasscom.einvoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasscom.einvoice.entity.InvoiceTransactionDetail;

@Repository
public interface InvoiceTransactionDetailRepository extends JpaRepository<InvoiceTransactionDetail, Long>{

	List<InvoiceTransactionDetail> findByMemberIdAndInvoiceIdOrderByCreatedDesc(Long memberId,long invoiceId);
	List<InvoiceTransactionDetail> findByInvoiceId(Long invoiceId);
	List<InvoiceTransactionDetail> findByInvoiceIdOrderByCreatedDesc(Long invoiceId);

	}
