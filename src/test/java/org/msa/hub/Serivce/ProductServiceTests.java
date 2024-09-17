package org.msa.hub.Serivce;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.msa.hub.application.dto.product.ProductListDTO;
import org.msa.hub.application.dto.product.ProductRequestDTO;
import org.msa.hub.application.dto.product.ProductResponseDTO;
import org.msa.hub.application.service.product.ProductService;
import org.msa.hub.global.login.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Log4j2(topic = "ProductServiceTests")
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    private CurrentUser currentUser;
    private ProductRequestDTO productCreateRequestDTO;
    private ProductRequestDTO productUpdateRequestDTO;
    private UUID productId;

    @BeforeEach
    void setUp() {
        currentUser = new CurrentUser(1L, "ADMIN");

        productCreateRequestDTO = ProductRequestDTO.builder()
                .hubId(UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d"))
                .companyId(UUID.fromString("e1bea00f-4337-4d13-ad1f-ebb0405a94cb"))
                .name("기어")
                .price(5000)
                .stock(200)
                .build();

        productUpdateRequestDTO = ProductRequestDTO.builder()
                .hubId(UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d"))
                .companyId(UUID.fromString("e1bea00f-4337-4d13-ad1f-ebb0405a94cb"))
                .name("기어")
                .price(7000)
                .stock(200)
                .build();

        productId = UUID.fromString("243bbfcd-a540-49d3-a4d7-691757d78142");
    }
    
    @Test
    @Transactional
    @DisplayName("상품 목록 조회 서비스 테스트")
    public void getProductListServiceTest(){

        // Given
        log.info("getProductListServiceTest");

        Pageable pageable = PageRequest.of(0,10);

        // When
        Page<ProductListDTO> productListDTOPage = productService.getProductList(pageable);

        // Then
        log.info(productListDTOPage.stream().toList());
    }

    @Test
    @Transactional
    @DisplayName("상품 생성 서비스 테스트")
    public void createProductServiceTest(){

        // Given
        log.info("createProductServiceTest");

        // When
        ProductResponseDTO dto = productService.createProduct(productCreateRequestDTO, currentUser);

        // Then
        Assertions.assertEquals("기어",dto.getName());
        Assertions.assertEquals(5000,dto.getPrice());
        Assertions.assertEquals(200,dto.getStock());
        
        log.info(dto);
    }
    
    @Test
    @Transactional
    @DisplayName("상품 조회 서비스 테스트")
    public void findProductByIdServiceTest(){
        
        // Given
        log.info("findProductByIdServiceTest");
        
        // When
        ProductResponseDTO dto = productService.findProductById(productId);
        
        // Then
        log.info(dto);
    }
    
    @Test
    @Transactional
    @DisplayName("상품 수정 서비스 테스트")
    public void updateProductServiceTest(){

        // Given
        log.info("updateProductServiceTest");

        // When
        ProductResponseDTO dto =  productService.updateProduct(productId, productUpdateRequestDTO, currentUser);

        // Then
        Assertions.assertEquals("기어",dto.getName());
        Assertions.assertEquals(7000,dto.getPrice());
        Assertions.assertEquals(200,dto.getStock());

        log.info(dto);
    }

    @Test
    @Transactional
    @DisplayName("상품 삭제 서비스 테스트")
    public void deleteProductServiceTest(){

        // Given
        log.info("deleteProductServiceTest");

        // When
        productService.deleteProduct(productId, currentUser);

        // Then
    }
    
    @Test
    @Transactional
    @DisplayName("상품 재고 차감 서비스 테스트")
    public void reduceProductStockServiceTest(){

        // Given
        log.info("reduceProductStockServiceTest");

        // When
        productService.reduceProductStock(productId, 2, currentUser);

        // Then
        log.info(productService.findProductById(productId));
    }

    @Test
    @Transactional
    @DisplayName("상품 재고 복구 서비스 테스트")
    public void returnProductStockServiceTest(){

        // Given
        log.info("returnProductStockServiceTest");

        // When
        productService.returnProductStock(productId, 2, currentUser);

        // Then
        log.info(productService.findProductById(productId));
    }
}
