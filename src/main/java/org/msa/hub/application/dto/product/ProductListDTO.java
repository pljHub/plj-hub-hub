package org.msa.hub.application.dto.product;

import lombok.Builder;
import lombok.Data;
import org.msa.hub.domain.model.Product;

import java.util.UUID;

@Data
@Builder
public class ProductListDTO {

    private UUID productId;
    private String name;
    private UUID companyId;
    private UUID hubId;
    private int price;
    private int stock;

    public static ProductListDTO toDTO(Product product){
        return ProductListDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .companyId(product.getCompany().getId())
                .hubId(product.getHub().getId())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
