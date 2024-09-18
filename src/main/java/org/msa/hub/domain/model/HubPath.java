package org.msa.hub.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.msa.hub.global.audit.AuditingEntity;
import org.msa.hub.domain.model.Hub;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_hub_path")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HubPath extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "start_hub_id", nullable = false)
    private Hub startHub;

    @ManyToOne
    @JoinColumn(name = "dest_hub_id", nullable = false)
    private Hub destHub;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "route_path")
    private String routePath;

    // HubPath 수정 메서드
    public void updateHubPath(Hub StartHub, Hub destHub, String duration, String routePath, Long userId){
        this.startHub = StartHub;
        this.destHub = destHub;
        this.duration = duration;
        this.routePath = routePath;
        this.setUpdatedBy(userId);
    }

    public void deleteHubPath(Long userId){
        this.setIsDeleted(true);
        this.setDeletedBy(userId);
        this.setDeletedAt(LocalDateTime.now());
    }

}
