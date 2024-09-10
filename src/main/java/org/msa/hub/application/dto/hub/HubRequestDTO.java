package org.msa.hub.application.dto.hub;

import lombok.Data;

@Data
public class HubRequestDTO {

    private String name;
    private String address;
    private Long userId;
}
