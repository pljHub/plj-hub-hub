package org.msa.hub.Serivce;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.msa.hub.application.dto.company.CompanyListDTO;
import org.msa.hub.application.dto.company.CompanyRequestDTO;
import org.msa.hub.application.dto.company.CompanyResponseDTO;
import org.msa.hub.application.service.company.CompanyService;
import org.msa.hub.global.login.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.msa.hub.domain.enums.CompanyTypeEnum.MANUFACTURER;

@SpringBootTest
@Log4j2(topic = "CompanyServiceTests")
public class CompanyServiceTests {

    @Autowired
    private CompanyService companyService;

    private CurrentUser currentUser;
    private CompanyRequestDTO companyCreateRequestDTO;
    private CompanyRequestDTO companyUpdateRequestDTO;
    private UUID companyId;

    @BeforeEach
    void setUp() {
        currentUser = new CurrentUser(1L, "ADMIN");

        companyCreateRequestDTO = CompanyRequestDTO.builder()
                .hubId(UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d"))
                .address("아차산로 17")
                .name("자전거 부품 제작")
                .type(MANUFACTURER)
                .build();

        companyUpdateRequestDTO = CompanyRequestDTO.builder()
                .hubId(UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d"))
                .address("아차산로 22")
                .name("냉장고 부품 제작")
                .type(MANUFACTURER)
                .build();

        companyId = UUID.fromString("e1bea00f-4337-4d13-ad1f-ebb0405a94cb");
    }

    @Test
    @Transactional
    @DisplayName("업체 목록 조회 서비스 테스트")
    public void getCompanyListServiceTest() {

        // Given
        log.info("getCompanyListServiceTest");

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<CompanyListDTO> companyListDTOPage = companyService.getCompanyList(pageable);

        // Then
        log.info(companyListDTOPage);
    }

    @Test
    @Transactional
    @DisplayName("업체 생성 서비스 테스트")
    public void createCompanyServiceTest() {

        // Given
        log.info("createCompanyServiceTest");

        // When
        CompanyResponseDTO dto = companyService.createCompany(companyCreateRequestDTO, currentUser);

        // Then
        Assertions.assertEquals("아차산로 17", dto.getAddress());
        Assertions.assertEquals("자전거 부품 제작", dto.getName());
        Assertions.assertEquals(MANUFACTURER, dto.getType());

        log.info(dto);
    }

    @Test
    @Transactional
    @DisplayName("업체 조회 서비스 테스트")
    public void findCompanyByIdServiceTest() {

        // Given
        log.info("findCompanyByIdServiceTest");

        // When
        CompanyResponseDTO dto = companyService.findCompanyById(companyId);

        // Then
        log.info(dto);
    }

    @Test
    @Transactional
    @DisplayName("업체 수정 서비스 테스트")
    public void updateCompanyServiceTest() {

        // Given
        log.info("updateCompanyServiceTest");

        // When
        CompanyResponseDTO dto = companyService.updateCompany(companyId, companyUpdateRequestDTO, currentUser);

        // Then
        Assertions.assertEquals("아차산로 22", dto.getAddress());
        Assertions.assertEquals("냉장고 부품 제작", dto.getName());
        Assertions.assertEquals(MANUFACTURER, dto.getType());

        log.info(dto);
    }
    
    @Test
    @Transactional
    @DisplayName("업체 삭제 서비스 테스트")
    public void deleteCompanyServiceTest(){

        // Given
        log.info("deleteCompanyServiceTest");

        // When
        companyService.deleteCompany(companyId, currentUser);

        // Then
    }
}
