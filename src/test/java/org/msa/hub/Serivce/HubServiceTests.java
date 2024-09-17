package org.msa.hub.Serivce;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.msa.hub.application.dto.hub.HubListDTO;
import org.msa.hub.application.dto.hub.HubRequestDTO;
import org.msa.hub.application.dto.hub.HubResponseDTO;
import org.msa.hub.application.service.hub.HubService;
import org.msa.hub.global.login.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Log4j2(topic = "HubServiceTests")
public class HubServiceTests {

    @Autowired
    private HubService hubService;

    private CurrentUser currentUser;
    private HubRequestDTO hubCreateRequestDTO;
    private HubRequestDTO hubUpdateRequestDTO;
    private UUID hubId;

    @BeforeEach
    void setUp() {
        currentUser = new CurrentUser(1L, "ADMIN");

        hubCreateRequestDTO = HubRequestDTO.builder()
                .name("제주도 허브")
                .address("제주특별자치도 제주시 아라이동 3001-19")
                .build();

        hubUpdateRequestDTO = HubRequestDTO.builder()
                .name("제주특별자치도 허브")
                .address("제주특별자치도 제주시 아라이동 3001-19")
                .build();

        hubId = UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d");

    }

    /**
     * 허브 목록 조회 서비스 테스트
     */
    @Test
    @Transactional
    @DisplayName("허브 목록 조회 서비스 테스트")
    public void getHubListServiceTest(){

        // Given
        log.info("getHubListServiceTest");

        Pageable pageable = PageRequest.of(0,10);

        // When
        Page<HubListDTO> hubListDTOPage =hubService.getHubList(pageable);

        // Then
        log.info(hubListDTOPage.stream().toList());
    }

    /**
     * 허브 생성 서비스 테스트
     */
    @Test
    @Transactional
    @DisplayName("허브 생성 서비스 테스트")
    public void createHubServiceTest(){

        // Given
        log.info("getHubListServiceTest");

        // When
        HubResponseDTO dto = hubService.createHub(hubCreateRequestDTO,currentUser);

        // Then
        Assertions.assertEquals("제주도 허브", dto.getName());
        Assertions.assertEquals("제주특별자치도 제주시 아라이동 3001-19", dto.getAddress());

        log.info(dto);
    }

    @Test
    @Transactional
    @DisplayName("허브 조회 서비스 테스트")
    public void findHubByIdServiceTest(){

        // Given
        log.info("findHubByIdServiceTest");

        // When
        HubResponseDTO result = hubService.findHubById(hubId);

        // Then
        log.info(result);
    }
    
    @Test
    @Transactional
    @DisplayName("허브 수정 서비스 테스트")
    public void updateHubServiceTest(){

        // Given
        log.info("updateHubServiceTest");

        // When
        HubResponseDTO result = hubService.updateHub(hubId, hubUpdateRequestDTO, currentUser);

        // Then
        Assertions.assertEquals("제주도특별자치도 허브", result.getName());
        Assertions.assertEquals("제주특별자치도 제주시 아라이동 3001-19", result.getAddress());

        log.info(result);
    }

    @Test
    @Transactional
    @DisplayName("허브 삭제 서비스 테스트")
    public void deleteHubServiceTest(){

        // Given
        log.info("deleteHubServiceTest");

        // When
        hubService.deleteHub(hubId, currentUser);

        // Then
    }


}
