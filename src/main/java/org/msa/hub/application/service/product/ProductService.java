package org.msa.hub.application.service.product;

import org.msa.hub.application.dto.product.ProductListDTO;
import org.msa.hub.application.dto.product.ProductRequestDTO;
import org.msa.hub.application.dto.product.ProductResponseDTO;
import org.msa.hub.global.login.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    // 상품 목록 조회
    Page<ProductListDTO> getProductList(Pageable pageable);

    // 상품 생성
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, CurrentUser currentUser);

    // 상품 조회
    ProductResponseDTO findProductById(UUID productId);

    // 상품 수정
    ProductResponseDTO updateProduct(UUID productId, ProductRequestDTO productRequestDTO, CurrentUser currentUser);

    // 상품 삭제
    void deleteProduct(UUID productId, CurrentUser currentUser);

    // 상품 재고 차감
    void reduceProductStock(UUID productId, int quantity, CurrentUser currentUser);

    // 상품 재고 복구
    void returnProductStock(UUID productId, int quantity, CurrentUser currentUser);

    // 상품 재고 차감 - internal
    void reduceProductStockInternal(UUID productId, int quantity);

    // 상품 재고 복구 - internal
    void returnProductStockInternal(UUID productId, int quantity);
}
