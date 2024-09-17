package org.msa.hub.application.dto.company;

import lombok.Builder;
import lombok.Data;
import org.msa.hub.domain.enums.CompanyTypeEnum;

import java.util.UUID;

@Data
@Builder
public class CompanyRequestDTO {

    private String name;
    private CompanyTypeEnum type;
    private UUID hubId;
    private String address;
}
