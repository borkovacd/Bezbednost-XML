package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.CertificateStatus;

@Repository
public interface CertificateStatusRepository extends JpaRepository<CertificateStatus, Long> {

	
}
