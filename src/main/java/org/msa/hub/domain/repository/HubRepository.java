package org.msa.hub.domain.repository;

import org.msa.hub.domain.model.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HubRepository {

    Page<Hub> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Hub> findById(UUID hubId);

    Hub save(Hub hub);
}
