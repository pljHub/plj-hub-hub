package org.msa.hub.infrastructure.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String role;
    private String slackId;
    private UUID hubId;
    private UUID companyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
