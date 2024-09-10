package org.msa.hub.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.msa.hub.global.dto.ResponseDto;
import org.msa.hub.application.dto.hub.HubListDTO;
import org.msa.hub.application.dto.hub.HubRequestDTO;
import org.msa.hub.application.dto.hub.HubResponseDTO;
import org.msa.hub.application.service.hub.HubService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/hubs")
@RequiredArgsConstructor
@Log4j2(topic = "Hub Controller")
public class HubController {

    private final HubService hubService;

    // 허브 목록 조회
    @GetMapping("")
    public ResponseEntity<ResponseDto<Page<HubListDTO>>> getHubList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                                  @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                  @RequestParam(value = "orderBy", defaultValue = "true") boolean orderBy){

        log.info("HubController | GET getHubList");

        Page<HubListDTO> hubListDTOPage = hubService.getHubList(page,size,sortBy,orderBy);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), hubListDTOPage));
    }

    // 허브 조회
    @GetMapping("/{hubId}")
    public ResponseEntity<ResponseDto<HubResponseDTO>> findById(@PathVariable UUID hubId){

        log.info("HubController | GET FindById");

        HubResponseDTO result = hubService.findHubById(hubId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), result));
    }

    // 허브 생성
    @PostMapping("")
    public ResponseEntity<ResponseDto<String>> createHub(@RequestBody HubRequestDTO hubRequestDTO){

        log.info("HubController | POST CreateHub");

        hubService.createHub(hubRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(HttpStatus.CREATED.name(),"허브가 성공적으로 생성되었습니다."));
    }

    // 허브 수정
    @PutMapping("/{hubId}")
    public ResponseEntity<ResponseDto<HubResponseDTO>> updateHub(@PathVariable UUID hubId, @RequestBody HubRequestDTO hubRequestDTO){

        log.info("HubController | PUT UpdateHub");

        HubResponseDTO result = hubService.updateHub(hubId, hubRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), result));
    }

    // 허브 삭제 - 논리적 삭제
    @DeleteMapping("/{hubId}")
    public ResponseEntity<ResponseDto<String>> deleteHub(@PathVariable UUID hubId){

        log.info("HubController | DELETE DeleteHub");

        hubService.deleteHub(hubId);

        return ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDto.success(HttpStatus.OK.name(),"허브가 성공적으로 삭제되었습니다."));

    }

}
