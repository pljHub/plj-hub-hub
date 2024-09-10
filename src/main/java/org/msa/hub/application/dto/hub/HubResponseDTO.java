package org.msa.hub.application.dto.hub;

import lombok.Builder;
import lombok.Data;
import org.msa.hub.domain.model.Hub;

import java.util.UUID;

@Data
@Builder
public class HubResponseDTO {

    private UUID hubId;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    public static HubResponseDTO toDTO (Hub hub){
        return HubResponseDTO.builder()
                .hubId(hub.getId())
                .name(hub.getName())
                .address(hub.getAddress())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .build();
    }
}
