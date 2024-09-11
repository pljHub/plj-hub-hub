package org.msa.hub.application.dto.hubPath;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class HubPathSequenceDTO implements Serializable {

    private UUID startHubId;
    private UUID destHubId;
    private int sequence;
    private String routePath;

}
