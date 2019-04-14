package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.CertificateModel;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateModel, Long> {

}
