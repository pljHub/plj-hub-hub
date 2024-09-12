package org.msa.hub.application.service.product;

import lombok.RequiredArgsConstructor;
import org.msa.hub.application.exception.company.CompanyNotFoundException;
import org.msa.hub.domain.model.Company;
import org.msa.hub.domain.repository.CompanyRepository;
import org.msa.hub.domain.repository.HubRepository;
import org.msa.hub.domain.repository.ProductRepository;
import org.msa.hub.infrastructure.repository.CompanyRepositoryImpl;
import org.msa.hub.application.exception.hub.HubNotFoundException;
import org.msa.hub.domain.model.Hub;
import org.msa.hub.infrastructure.repository.HubRepositoryImpl;
import org.msa.hub.application.dto.product.ProductListDTO;
import org.msa.hub.application.dto.product.ProductRequestDTO;
import org.msa.hub.application.dto.product.ProductResponseDTO;
import org.msa.hub.application.exception.product.ProductNotFoundException;
import org.msa.hub.domain.model.Product;
import org.msa.hub.infrastructure.repository.ProductRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final HubRepository hubRepository;
    private final CompanyRepository companyRepository;

    /**
     * 상품 목록 조회
     * @param page 
     * @param size
     * @param sortBy
     * @param orderBy
     * @return
     */
    @Override
    public Page<ProductListDTO> getProductList(int page, int size, String sortBy, boolean orderBy) {

        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = orderBy ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        Page<Product> productPage = productRepository.findAllByIsDeletedFalse(pageable);

        return productPage.map(ProductListDTO::toDTO);
    }

    /**
     * 상품 생성
     * @param productRequestDTO
     */
    @Transactional
    @Override
    public void createProduct(ProductRequestDTO productRequestDTO) {

        Hub hub = hubRepository.findById(productRequestDTO.getHubId()).orElseThrow(
                HubNotFoundException::new);

        Company company = companyRepository.findById(productRequestDTO.getCompanyId()).orElseThrow(
                CompanyNotFoundException::new);

        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .company(company)
                .hub(hub)
                .price(productRequestDTO.getPrice())
                .stock(productRequestDTO.getStock())
                .build();

        productRepository.save(product);
    }

    /**
     * 상품 조회
     * @param productId
     * @return
     */
    @Override
    public ProductResponseDTO findProductById(UUID productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        return ProductResponseDTO.toDTO(product);
    }

    /**
     * 상품 수정
     * @param productId
     * @return
     */
    @Transactional
    @Override
    public ProductResponseDTO updateProduct(UUID productId, ProductRequestDTO productRequestDTO) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        Hub hub = hubRepository.findById(productRequestDTO.getHubId()).orElseThrow(
                HubNotFoundException::new);

        Company company = companyRepository.findById(productRequestDTO.getCompanyId()).orElseThrow(
                CompanyNotFoundException::new);

        product.updateProduct(productRequestDTO,company,hub);

        return ProductResponseDTO.toDTO(product);
    }

    /**
     * 상품 삭제 - 논리적 삭제
     * @param productId
     */
    @Transactional
    @Override
    public void deleteProduct(UUID productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        product.deleteProduct();
    }

    /**
     * 상품 재고 차감
     * @param productId
     * @param quantity
     */
    @Transactional
    @Override
    public void reduceProductStock(UUID productId, int quantity) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        product.reduceProductStock(quantity);
    }

    /**
     * 상품 재고 되돌리기
     * @param productId 
     * @param quantity
     */
    @Transactional
    @Override
    public void returnProductStock(UUID productId, int quantity) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        product.returnProductStock(quantity);
    }
}
