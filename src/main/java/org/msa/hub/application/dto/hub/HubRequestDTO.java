package org.msa.hub.application.dto.hub;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HubRequestDTO {

    private String name;
    private String address;
}
