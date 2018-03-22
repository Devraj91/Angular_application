package com.nasscom.einvoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.nasscom.einvoice.entity.InvoiceFile;

@RepositoryRestResource
public interface InvoiceFileRepository extends JpaRepository<InvoiceFile, Long> {

}
