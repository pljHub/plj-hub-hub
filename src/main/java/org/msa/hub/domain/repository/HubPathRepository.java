package org.msa.hub.domain.repository;

import org.msa.hub.domain.model.HubPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HubPathRepository {

    Page<HubPath> findAllByIsDeletedFalse(Pageable pageable);

    List<Object[]> getHubPathSequence(@Param("startHubId") UUID startHubId, @Param("destHubId") UUID destHubId);

    HubPath save(HubPath hubPath);

    Optional<HubPath> findById(UUID hubPathId);
}
