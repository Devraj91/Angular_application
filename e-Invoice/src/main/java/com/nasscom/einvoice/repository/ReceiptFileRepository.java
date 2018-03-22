package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nasscom.einvoice.entity.InvoiceReceiptFile;

public interface ReceiptFileRepository extends JpaRepository<InvoiceReceiptFile, Long>{

}
