package org.msa.hub.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.msa.hub.global.audit.AuditingEntity;
import org.msa.hub.global.util.GeocodingUtil;
import org.msa.hub.application.dto.hub.HubRequestDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_hub")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Hub extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    public void updateHub(HubRequestDTO hubRequestDTO, GeocodingUtil.LatLong latLong, Long userId){
        this.name = hubRequestDTO.getName();
        this.address = hubRequestDTO.getAddress();
        this.latitude = latLong.getLatitude();
        this.longitude = latLong.getLongitude();
        this.setUpdatedBy(userId);
    }

    public void deleteHub(Long userId){
        this.setIsDeleted(true);
        this.setDeletedBy(userId);
        this.setDeletedAt(LocalDateTime.now());
    }
}
