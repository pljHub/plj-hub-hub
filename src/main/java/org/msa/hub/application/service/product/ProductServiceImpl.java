package org.msa.hub.application.service.product;

import lombok.RequiredArgsConstructor;
import org.msa.hub.application.exception.company.CompanyNotFoundException;
import org.msa.hub.domain.model.Company;
import org.msa.hub.domain.repository.CompanyRepository;
import org.msa.hub.domain.repository.HubRepository;
import org.msa.hub.domain.repository.ProductRepository;
import org.msa.hub.global.dto.ResponseDto;
import org.msa.hub.global.login.CurrentUser;
import org.msa.hub.global.util.UserRoleCheck;
import org.msa.hub.infrastructure.client.UserClient;
import org.msa.hub.infrastructure.dto.UserResponseDTO;
import org.msa.hub.application.exception.hub.HubNotFoundException;
import org.msa.hub.domain.model.Hub;
import org.msa.hub.application.dto.product.ProductListDTO;
import org.msa.hub.application.dto.product.ProductRequestDTO;
import org.msa.hub.application.dto.product.ProductResponseDTO;
import org.msa.hub.application.exception.product.ProductNotFoundException;
import org.msa.hub.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final HubRepository hubRepository;
    private final CompanyRepository companyRepository;
    private final UserRoleCheck userRoleCheck;
    private final UserClient userClient;


    /**
     * 상품 목록 조회
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductListDTO> getProductList(Pageable pageable) {

        Page<Product> productPage = productRepository.findAllByIsDeletedFalse(pageable);

        return productPage.map(ProductListDTO::toDTO);
    }


    /**
     * 상품 생성
     * @param productRequestDTO
     * @param currentUser
     */
    @Transactional
    @Override
    public void createProduct(ProductRequestDTO productRequestDTO, CurrentUser currentUser) {

        Hub hub = hubRepository.findById(productRequestDTO.getHubId()).orElseThrow(
                HubNotFoundException::new);

        Company company = companyRepository.findById(productRequestDTO.getCompanyId()).orElseThrow(
                CompanyNotFoundException::new);

        // 유저의 HubId
        ResponseEntity<ResponseDto<UserResponseDTO>> response = userClient.findUserById(currentUser.getCurrentUserId());
        UserResponseDTO user = response.getBody().getData();
        UUID userHubId = user.getHubId();

        // 로그인한 user의 CompanyId 조회
        UUID userCompanyHubId = user.getCompanyId();

        // 권한 검증
        userRoleCheck.isAdminOrHubManagerOrCompanyManagerForProduct(currentUser.getCurrentUserRole(),
                userHubId, hub.getId(), // HUB_MANAGER
                userCompanyHubId, company.getId()); // COMPANY_MANGER

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
     * @param productRequestDTO
     * @param currentUser
     * @return
     */
    @Transactional
    @Override
    public ProductResponseDTO updateProduct(UUID productId, ProductRequestDTO productRequestDTO, CurrentUser currentUser) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        Hub hub = hubRepository.findById(productRequestDTO.getHubId()).orElseThrow(
                HubNotFoundException::new);

        Company company = companyRepository.findById(productRequestDTO.getCompanyId()).orElseThrow(
                CompanyNotFoundException::new);

        // 유저의 HubId
        ResponseEntity<ResponseDto<UserResponseDTO>> response = userClient.findUserById(currentUser.getCurrentUserId());
        UserResponseDTO user = response.getBody().getData();
        UUID userHubId = user.getHubId();

        // UserId로 Company 조회 -> hubId 찾기

        // 로그인한 user의 CompanyId 조회
        UUID userCompanyHubId = user.getCompanyId();

        // 권한 검증
        userRoleCheck.isAdminOrHubManagerOrCompanyManagerForProduct(currentUser.getCurrentUserRole(),
                userHubId, hub.getId(), // HUB_MANAGER
                userCompanyHubId, company.getId()); // COMPANY_MANGER

        product.updateProduct(productRequestDTO,company,hub);

        return ProductResponseDTO.toDTO(product);
    }

    /**
     * 상품 삭제 - 논리적 삭제
     * @param productId
     * @param currentUser
     */
    @Transactional
    @Override
    public void deleteProduct(UUID productId, CurrentUser currentUser) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        // 유저의 HubId
        ResponseEntity<ResponseDto<UserResponseDTO>> response = userClient.findUserById(currentUser.getCurrentUserId());
        UserResponseDTO user = response.getBody().getData();
        UUID userHubId = user.getHubId();

        // 로그인한 user의 CompanyId 조회
        UUID userCompanyHubId = user.getCompanyId();

        // 권한 검증
        userRoleCheck.isAdminOrHubManagerOrCompanyManagerForProduct(currentUser.getCurrentUserRole(),
                userHubId, product.getHub().getId(), // HUB_MANAGER
                userCompanyHubId, product.getCompany().getId()); // COMPANY_MANGER

        product.deleteProduct();
    }

    /** 
     * 상품 재고 차감
     * @param productId 
     * @param quantity
     * @param currentUser
     */
    @Transactional
    @Override
    public void reduceProductStock(UUID productId, int quantity, CurrentUser currentUser) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        // 유저의 HubId
        ResponseEntity<ResponseDto<UserResponseDTO>> response = userClient.findUserById(currentUser.getCurrentUserId());
        UserResponseDTO user = response.getBody().getData();
        UUID userHubId = user.getHubId();

        // 로그인한 user의 CompanyId 조회
        UUID userCompanyHubId = user.getCompanyId();

        // 권한 검증
        userRoleCheck.isAdminOrHubManagerOrCompanyManagerForProduct(currentUser.getCurrentUserRole(),
                userHubId, product.getHub().getId(), // HUB_MANAGER
                userCompanyHubId, product.getCompany().getId()); // COMPANY_MANGER

        product.reduceProductStock(quantity);
    }


    /**
     * 상품 재고 되돌리기
     * @param productId 
     * @param quantity
     * @param currentUser
     */
    @Transactional
    @Override
    public void returnProductStock(UUID productId, int quantity, CurrentUser currentUser) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        // 유저의 HubId
        ResponseEntity<ResponseDto<UserResponseDTO>> response = userClient.findUserById(currentUser.getCurrentUserId());
        UserResponseDTO user = response.getBody().getData();
        UUID userHubId = user.getHubId();

        // 로그인한 user의 CompanyId 조회
        UUID userCompanyHubId = user.getCompanyId();

        // 권한 검증
        userRoleCheck.isAdminOrHubManagerOrCompanyManagerForProduct(currentUser.getCurrentUserRole(),
                userHubId, product.getHub().getId(), // HUB_MANAGER
                userCompanyHubId, product.getCompany().getId()); // COMPANY_MANGER

        product.returnProductStock(quantity);
    }

    /**
     * 재고 차감 - internal
     * @param productId
     * @param quantity
     */
    @Transactional
    @Override
    public void reduceProductStockInternal(UUID productId, int quantity) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        product.reduceProductStock(quantity);
    }

    /**
     * 재고 복구 - internal
     * @param productId
     * @param quantity
     */
    @Transactional
    @Override
    public void returnProductStockInternal(UUID productId, int quantity) {

        Product product = productRepository.findById(productId).orElseThrow(
                ProductNotFoundException::new);

        product.returnProductStock(quantity);
    }
}
