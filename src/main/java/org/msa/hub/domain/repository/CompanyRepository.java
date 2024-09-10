package org.msa.hub.domain.repository;

import org.msa.hub.domain.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository {

    Page<Company> findByIsDeletedFalse(Pageable pageable);

    Company save(Company company);

    Optional<Company> findById(UUID companyId);
}
