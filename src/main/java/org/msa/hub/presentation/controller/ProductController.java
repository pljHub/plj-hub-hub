package org.msa.hub.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.msa.hub.global.dto.ResponseDto;
import org.msa.hub.application.dto.product.ProductListDTO;
import org.msa.hub.application.dto.product.ProductRequestDTO;
import org.msa.hub.application.dto.product.ProductResponseDTO;
import org.msa.hub.application.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Log4j2(topic = "Product Controller")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회
    @GetMapping("")
    public ResponseEntity<ResponseDto<Page<ProductListDTO>>> getProductList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                                                          @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                          @RequestParam(value = "orderBy", defaultValue = "true") boolean orderBy) {

        log.info("Product Controller | GET getProductList");

        Page<ProductListDTO> productListDTOPage = productService.getProductList(page, size, sortBy, orderBy);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), productListDTOPage));
    }

    // 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<ProductResponseDTO>> getProductById(@PathVariable UUID productId) {

        log.info("Product Controller | GET getProductById");

        ProductResponseDTO result = productService.findProductById(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), result));
    }

    // 상품 생성
    @PostMapping("")
    public ResponseEntity<ResponseDto<String>> createProduct(@RequestBody ProductRequestDTO productRequestDTO){

        log.info("Product Controller | POST createProduct");

        productService.createProduct(productRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseDto.success(HttpStatus.CREATED.name(), "상품이 성공적으로 생성되었습니다."));
    }

    // 상품 수정
    @PutMapping("/{productId}")
    public ResponseEntity<ResponseDto<ProductResponseDTO>> updateProduct(@PathVariable UUID productId, @RequestBody ProductRequestDTO productRequestDTO){

        log.info("Product Controller | PUT updateProduct");

        ProductResponseDTO result = productService.updateProduct(productId, productRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), result));
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto<String>> deleteProduct(@PathVariable UUID productId){

        log.info("Product Controller | DELETE deleteProduct");

        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), "상품이 성공적으로 삭제되었습니다."));
    }

    // 상품 재고 차감
    @PutMapping("/{productId}/reduceStock")
    public void reduceProductStock(@PathVariable UUID productId, @RequestParam int quantity){

        log.info("Product Controller | PUT reduceProductStock");

        productService.reduceProductStock(productId,quantity);
    }

    // 상품 재고 되돌리기
    @PutMapping("/{productId}/returnStock")
    public void returnProductStock(@PathVariable UUID productId, @RequestParam int quantity){

        log.info("Product Controller | PUT returnProductStock");

        productService.returnProductStock(productId,quantity);
    }
}
