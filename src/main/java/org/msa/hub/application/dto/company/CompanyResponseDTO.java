package org.msa.hub.application.dto.company;

import lombok.Builder;
import lombok.Data;
import org.msa.hub.domain.enums.CompanyTypeEnum;
import org.msa.hub.domain.model.Company;

import java.util.UUID;

@Data
@Builder
public class CompanyResponseDTO {

    private UUID companyId;
    private String name;
    private CompanyTypeEnum type;
    private UUID hubId;
    private String address;

    public static CompanyResponseDTO toDTO (Company company){
        return CompanyResponseDTO.builder()
                .companyId(company.getId())
                .name(company.getName())
                .type(company.getType())
                .hubId(company.getHub().getId())
                .address(company.getAddress())
                .build();
    }
}
