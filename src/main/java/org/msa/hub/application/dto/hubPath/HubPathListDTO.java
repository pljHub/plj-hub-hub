package org.msa.hub.application.dto.hubPath;

import lombok.Builder;
import lombok.Data;
import org.msa.hub.domain.model.HubPath;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class HubPathListDTO implements Serializable {

    private UUID hubPathId;
    private UUID startHubId;
    private UUID destHubId;
    private String duration;
    private String routhPath;

    public static HubPathListDTO toDTO (HubPath hubPath){
        return HubPathListDTO.builder()
                .hubPathId(hubPath.getId())
                .startHubId(hubPath.getStartHub().getId())
                .destHubId(hubPath.getDestHub().getId())
                .duration(hubPath.getDuration())
                .routhPath(hubPath.getRoutePath())
                .build();
    }
}
