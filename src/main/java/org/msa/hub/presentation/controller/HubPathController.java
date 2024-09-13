package org.msa.hub.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.msa.hub.global.dto.ResponseDto;
import org.msa.hub.application.dto.hubPath.HubPathListDTO;
import org.msa.hub.application.dto.hubPath.HubPathRequestDTO;
import org.msa.hub.application.dto.hubPath.HubPathResponseDTO;
import org.msa.hub.application.dto.hubPath.HubPathSequenceDTO;
import org.msa.hub.application.service.hubPath.HubPathService;
import org.msa.hub.global.login.CurrentUser;
import org.msa.hub.global.login.Login;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hub-path")
@RequiredArgsConstructor
@Log4j2(topic = "HubPath Controller")
public class HubPathController {

    private final HubPathService hubPathService;

    // 허브 경로 목록 조회
    @GetMapping("")
    public ResponseEntity<ResponseDto<Page<HubPathListDTO>>> getHubPathList(Pageable pageable) {

        log.info("HubPathController | GET getHubPathList");

        Page<HubPathListDTO> hubPathListDTOPage = hubPathService.getHubPathList(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), hubPathListDTOPage));
    }

    // 허브 경로 조회
    @GetMapping("/{hubPathId}")
    public ResponseEntity<ResponseDto<HubPathResponseDTO>> findHubPathById(@PathVariable UUID hubPathId) {

        log.info("HubPathController | GET findHubPathById");

        HubPathResponseDTO result = hubPathService.findHubPathById(hubPathId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), result));
    }

    // 허브 경로 생성
    @PostMapping("")
    public ResponseEntity<ResponseDto<String>> createHubPath(@RequestBody HubPathRequestDTO hubPathRequestDTO, @Login CurrentUser currentUser) throws UnsupportedEncodingException {

        log.info("HubPathController | POST createHubPath");
        log.info(hubPathRequestDTO.getStartHubId());
        log.info(hubPathRequestDTO.getDestHubId());

        hubPathService.createHubPath(hubPathRequestDTO, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(HttpStatus.CREATED.name(),"허브 경로가 성공적으로 생성되었습니다."));
    }

    // 허브 경로 수정
    @PutMapping("/{hubPathId}")
    public ResponseEntity<ResponseDto<HubPathResponseDTO>> updateHubPath(@PathVariable UUID hubPathId, @RequestBody HubPathRequestDTO hubPathRequestDTO, @Login CurrentUser currentUser) throws UnsupportedEncodingException {

        log.info("HubPathController | PUT updateHubPath");

        HubPathResponseDTO result = hubPathService.updateHubPath(hubPathId, hubPathRequestDTO, currentUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), result));
    }

    // 허브 경로 삭제 - 논리적 삭제
    @DeleteMapping("/{hubPathId}")
    public ResponseEntity<ResponseDto<String>> deleteHubPath(@PathVariable UUID hubPathId, @Login CurrentUser currentUser) {

        log.info("HubPathController | DELETE deleteHubPath");

        hubPathService.deleteHubPath(hubPathId, currentUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(),"허브 경로가 성공적으로 삭제되었습니다."));
    }

    // 허브 경로 시퀀스 조회
    @GetMapping("/sequence")
    public ResponseEntity<ResponseDto<List<HubPathSequenceDTO>>> getHubPathSequence(@RequestParam(value = "startHubId") UUID startHubId,
                                                                       @RequestParam(value = "destHubId") UUID destHubId) {

        log.info("HubPathController | GET GetHubPathSequence");

        List<HubPathSequenceDTO> sequenceDTOList = hubPathService.getHubPathSequence(startHubId, destHubId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), sequenceDTOList));
    }

}
