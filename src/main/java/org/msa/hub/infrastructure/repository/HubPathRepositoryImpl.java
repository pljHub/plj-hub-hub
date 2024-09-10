package org.msa.hub.infrastructure.repository;

import org.msa.hub.domain.model.HubPath;
import org.msa.hub.domain.repository.HubPathRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HubPathRepositoryImpl extends JpaRepository<HubPath, UUID> , HubPathRepository {

    @Override
    Page<HubPath> findAllByIsDeletedFalse(Pageable pageable);

    // 재귀 쿼리 사용 -> 네이티브 쿼리
    @Query(value = """
        WITH RECURSIVE hub_route AS (
            SELECT start_hub_id, dest_hub_id, 1 AS sequence, route_path
            FROM p_hub_path p
            WHERE p.start_hub_id = :startHubId

            UNION ALL

            SELECT p.start_hub_id, p.dest_hub_id, hr.sequence + 1 AS sequence, p.route_path
            FROM p_hub_path p
            INNER JOIN hub_route hr ON hr.dest_hub_id = p.start_hub_id
            WHERE hr.dest_hub_id != :destHubId
        )
        SELECT * FROM hub_route ORDER BY sequence;
    """, nativeQuery = true)
    @Override
    List<Object[]> getHubPathSequence(@Param("startHubId") UUID startHubId, @Param("destHubId") UUID destHubId);

    @Override
    HubPath save(HubPath hubPath);
}
