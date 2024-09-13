package org.msa.hub.application.service.hubPath;

import org.msa.hub.application.dto.hubPath.HubPathListDTO;
import org.msa.hub.application.dto.hubPath.HubPathRequestDTO;
import org.msa.hub.application.dto.hubPath.HubPathResponseDTO;
import org.msa.hub.application.dto.hubPath.HubPathSequenceDTO;
import org.msa.hub.global.login.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

public interface HubPathService {

    // 허브 경로 목록 조회
    Page<HubPathListDTO> getHubPathList(Pageable pageable);

    // 허브 경로 생성
    HubPathResponseDTO createHubPath(HubPathRequestDTO hubPathRequestDTO, CurrentUser currentUser) throws UnsupportedEncodingException;

    // 허브 경로 조회
    HubPathResponseDTO findHubPathById(UUID hubPathId);

    // 허브 경로 수정
    HubPathResponseDTO updateHubPath(UUID hubPathId, HubPathRequestDTO hubPathRequestDTO, CurrentUser currentUser) throws UnsupportedEncodingException;

    // 허브 경로 삭제 - 논리적 삭제
    void deleteHubPath(UUID hubPathId, CurrentUser currentUser);

    // 허브 경로 시퀀스 조회
    List<HubPathSequenceDTO> getHubPathSequence(UUID startHubId, UUID destHubId);
}
