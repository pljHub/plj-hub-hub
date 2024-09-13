package org.msa.hub.application.service.hub;

import org.msa.hub.application.dto.hub.HubListDTO;
import org.msa.hub.application.dto.hub.HubRequestDTO;
import org.msa.hub.application.dto.hub.HubResponseDTO;
import org.msa.hub.global.login.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HubService {

    // 허브 목록 조회
    Page<HubListDTO> getHubList(Pageable pageable);

    // 허브 생성
    void createHub(HubRequestDTO hubRequestDTO, CurrentUser currentUser);

    // 허브 조회
    HubResponseDTO findHubById(UUID hubId);

    // 허브 수정
    HubResponseDTO updateHub(UUID hubId, HubRequestDTO hubRequestDTO, CurrentUser currentUser);

    // 허브 삭제
    void deleteHub(UUID hubId, CurrentUser currentUser);
}
