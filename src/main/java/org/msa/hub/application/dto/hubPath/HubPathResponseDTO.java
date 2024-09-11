package org.msa.hub.application.dto.hubPath;

import lombok.Builder;
import lombok.Data;
import org.msa.hub.domain.model.HubPath;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class HubPathResponseDTO implements Serializable {

    private UUID hubPathId;
    private String startHubName;
    private String destHubName;
    private String duration;
    private String routePath;

    public static HubPathResponseDTO toDTO (HubPath hubPath){
        return HubPathResponseDTO.builder()
                .hubPathId(hubPath.getId())
                .startHubName(hubPath.getStartHub().getName())
                .destHubName(hubPath.getDestHub().getName())
                .duration(hubPath.getDuration())
                .routePath(hubPath.getRoutePath())
                .build();
    }
}
