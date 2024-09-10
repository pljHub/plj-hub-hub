package org.msa.hub.application.dto.hub;

import lombok.Builder;
import lombok.Data;
import org.msa.hub.domain.model.Hub;

import java.util.UUID;

@Data
@Builder
public class HubListDTO {

    private UUID hubId;
    private String name;
    private String address;

    public static HubListDTO toDTO(Hub hub){
        return HubListDTO.builder()
                .hubId(hub.getId())
                .name(hub.getName())
                .address(hub.getAddress())
                .build();
    }
}
