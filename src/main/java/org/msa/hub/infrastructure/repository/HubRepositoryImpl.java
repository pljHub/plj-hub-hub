package org.msa.hub.infrastructure.repository;

import org.msa.hub.domain.model.Hub;
import org.msa.hub.domain.repository.HubRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubRepositoryImpl extends JpaRepository<Hub, UUID> , HubRepository {

    @Override
    Page<Hub> findAllByIsDeletedFalse(Pageable pageable);

    @Override
    Hub save(Hub hub);
}
