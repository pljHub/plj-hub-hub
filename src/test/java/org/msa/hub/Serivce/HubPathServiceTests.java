package org.msa.hub.Serivce;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.msa.hub.application.dto.hubPath.HubPathListDTO;
import org.msa.hub.application.dto.hubPath.HubPathRequestDTO;
import org.msa.hub.application.dto.hubPath.HubPathResponseDTO;
import org.msa.hub.application.dto.hubPath.HubPathSequenceDTO;
import org.msa.hub.application.service.hubPath.HubPathService;
import org.msa.hub.global.login.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2(topic = "HubPathServiceTests")
public class HubPathServiceTests {

    @Autowired
    private HubPathService hubPathService;
    private CurrentUser currentUser;
    private HubPathRequestDTO hubPathCreateRequestDTO;
    private HubPathRequestDTO hubPathUpdateRequestDTO;
    private UUID hubPathId;
    private UUID startHubId;
    private UUID destHubId;


    @BeforeEach
    void setUp() {
        currentUser = new CurrentUser(1L, "ADMIN");

        hubPathCreateRequestDTO = HubPathRequestDTO.builder()
                .startHubId(UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d"))
                .destHubId(UUID.fromString("78a8bd22-0893-413e-97b8-ac68e6dee5e0"))
                .routePath("서울 → 경기 북부")
                .build();

        hubPathUpdateRequestDTO = HubPathRequestDTO.builder()
                .startHubId(UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d"))
                .destHubId(UUID.fromString("78a8bd22-0893-413e-97b8-ac68e6dee5e0"))
                .routePath("서울에서 경기 북부")
                .build();

        hubPathId = UUID.fromString("ac0d99e9-dd7d-4f68-9698-f389df1d7acd");

        startHubId = UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d");
        destHubId = UUID.fromString("088ac0ac-8951-44ff-8d2e-25a7f1461540");

    }


    @Test
    @Transactional
    @DisplayName("허브 경로 목록 조회 서비스 테스트")
    public void getHubPathListServiceTest(){

        // Given
        log.info("getHubPathListServiceTest");

        Pageable pageable = PageRequest.of(0,10);

        // When
        Page<HubPathListDTO> hubPathListDTOPage = hubPathService.getHubPathList(pageable);

        // Then
        log.info(hubPathListDTOPage.stream().toList());
    }
    
    @Test
    @Transactional
    @DisplayName("허브 경로 생성 서비스 테스트")
    public void createHubPathServiceTest() throws UnsupportedEncodingException {

        // Given
        log.info("createHubPathServiceTest");

        // When
        HubPathResponseDTO dto = hubPathService.createHubPath(hubPathCreateRequestDTO, currentUser);

        // Then
        Assertions.assertEquals("서울 → 경기 북부", dto.getRoutePath());

        log.info(dto);
    }

    @Test
    @Transactional
    @DisplayName("허브 경로 조회 서비스 테스트")
    public void findHubPathByIdServiceTest(){

        // Given
        log.info("findHubPathByIdServiceTest");

        // When
        HubPathResponseDTO dto = hubPathService.findHubPathById(hubPathId);

        // Then
        log.info(dto);
    }

    @Test
    @Transactional
    @DisplayName("허브 경로 수정 서비스 테스트")
    public void updateHubPathServiceTest() throws UnsupportedEncodingException {

        // Given
        log.info("updateHubPathServiceTest");

        // When
        HubPathResponseDTO dto = hubPathService.updateHubPath(hubPathId, hubPathUpdateRequestDTO, currentUser);

        // Then
        Assertions.assertEquals("서울에서 경기 북부", dto.getRoutePath());

        log.info(dto);
    }
    
    @Test
    @Transactional
    @DisplayName("허브 경로 삭제 서비스 테스트")
    public void deleteHubPathServiceTest(){

        // Given
        log.info("deleteHubPathServiceTest");

        // When
        hubPathService.deleteHubPath(hubPathId, currentUser);

        // Then
    }
    
    @Test
    @Transactional
    @DisplayName("허브 경로 시퀀스 조회 서비스 테스트")
    public void getHubPathSequenceServiceTest(){

        // Given
        log.info("getHubPathSequenceServiceTest");

        // When
        List<HubPathSequenceDTO> hubPathSequenceDTOList = hubPathService.getHubPathSequence(startHubId,destHubId);

        // Then
        log.info(hubPathSequenceDTOList.stream().toList());
    }
}
