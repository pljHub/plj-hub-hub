package org.msa.hub.application.service.hub;

import lombok.RequiredArgsConstructor;
import org.msa.hub.domain.repository.HubRepository;
import org.msa.hub.global.util.GeocodingUtil;
import org.msa.hub.application.dto.hub.HubListDTO;
import org.msa.hub.application.dto.hub.HubRequestDTO;
import org.msa.hub.application.dto.hub.HubResponseDTO;
import org.msa.hub.application.exception.hub.HubNotFoundException;
import org.msa.hub.domain.model.Hub;
import org.msa.hub.infrastructure.repository.HubRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

    private final HubRepository hubRepository;

    private final GeocodingUtil geocodingUtil;

    /**
     * Hub 목록 조회
     *
     * @param page
     * @param size
     * @param sortBy
     * @param orderBy
     * @return
     */
    @Override
    public Page<HubListDTO> getHubList(int page, int size, String sortBy, boolean orderBy) {

        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = orderBy ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        Page<Hub> hubPage = hubRepository.findAllByIsDeletedFalse(pageable);

        return hubPage.map(HubListDTO::toDTO);
    }

    /**
     * 허브 생성
     * @param hubRequestDTO 
     * @return
     */
    @Transactional
    @Override
    public void createHub(HubRequestDTO hubRequestDTO) {

        // 주소를 통해 위도 경도 계산
        GeocodingUtil.LatLong latLong = geocodingUtil.getLatLongFromAddress(hubRequestDTO.getAddress());

        Hub hub = Hub.builder()
                .name(hubRequestDTO.getName())
                .address(hubRequestDTO.getAddress())
                .latitude(latLong.getLatitude())
                .longitude(latLong.getLongitude())
                .build();

        hubRepository.save(hub);
    }

    /**
     * 허브 조회
     * @param hubId 
     * @return
     */
    @Override
    public HubResponseDTO findHubById(UUID hubId) {

        Hub hub = hubRepository.findById(hubId).orElseThrow(
                HubNotFoundException::new);
        
        return HubResponseDTO.toDTO(hub);
    }

    /**
     * 허브 수정
     * @param hubId 
     * @param hubRequestDTO
     * @return
     */
    @Transactional
    @Override
    public HubResponseDTO updateHub(UUID hubId, HubRequestDTO hubRequestDTO) {

        Hub hub = hubRepository.findById(hubId).orElseThrow(
                HubNotFoundException::new);

        // 주소를 통해 위도 경도 계산
        GeocodingUtil.LatLong latLong = geocodingUtil.getLatLongFromAddress(hubRequestDTO.getAddress());

        hub.updateHub(hubRequestDTO, latLong);

        return HubResponseDTO.toDTO(hub);
    }

    /**
     * 허브 삭제 - 논리적 삭제
     * @param hubId 
     */
    @Transactional
    @Override
    public void deleteHub(UUID hubId) {

        Hub hub = hubRepository.findById(hubId).orElseThrow(
                HubNotFoundException::new);

        hub.deleteHub();
    }
}
