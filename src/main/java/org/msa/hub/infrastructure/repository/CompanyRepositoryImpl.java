package org.msa.hub.infrastructure.repository;

import org.msa.hub.domain.model.Company;
import org.msa.hub.domain.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepositoryImpl extends JpaRepository<Company, UUID>, CompanyRepository {
    @Override
    Page<Company> findByIsDeletedFalse(Pageable pageable);

    @Override
    Company save(Company company);
}
