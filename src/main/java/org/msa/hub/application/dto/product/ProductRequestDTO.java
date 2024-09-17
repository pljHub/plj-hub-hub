package org.msa.hub.application.dto.product;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductRequestDTO {

    private String name;
    private UUID companyId;
    private UUID hubId;
    private int price;
    private int stock;
}
