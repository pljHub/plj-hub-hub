package org.msa.hub.application.service.product;

import org.msa.hub.application.dto.product.ProductListDTO;
import org.msa.hub.application.dto.product.ProductRequestDTO;
import org.msa.hub.application.dto.product.ProductResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {

    // 상품 목록 조회
    Page<ProductListDTO> getProductList(int page, int size, String sortBy, boolean orderBy);

    // 상품 생성
    void createProduct(ProductRequestDTO productRequestDTO);

    // 상품 조회
    ProductResponseDTO findProductById(UUID productId);

    // 상품 수정
    ProductResponseDTO updateProduct(UUID productId, ProductRequestDTO productRequestDTO);

    // 상품 삭제
    void deleteProduct(UUID productId);

    // 상품 재고 차감
    void reduceProductStock(UUID productId, int quantity);

    // 상품 재고 되돌리기
    void returnProductStock(UUID productId, int quantity);
}
