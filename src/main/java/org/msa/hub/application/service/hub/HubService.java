package org.msa.hub.application.service.hub;

import org.msa.hub.application.dto.hub.HubListDTO;
import org.msa.hub.application.dto.hub.HubRequestDTO;
import org.msa.hub.application.dto.hub.HubResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface HubService {

    // 허브 목록 조회
    Page<HubListDTO> getHubList(int page, int size, String sortBy, boolean orderBy);

    // 허브 생성
    void createHub(HubRequestDTO hubRequestDTO);

    // 허브 조회
    HubResponseDTO findHubById(UUID hubId);

    // 허브 수정
    HubResponseDTO updateHub(UUID hubId, HubRequestDTO hubRequestDTO);

    // 허브 삭제
    void deleteHub(UUID hubId);
}
