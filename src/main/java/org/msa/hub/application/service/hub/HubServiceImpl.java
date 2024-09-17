package org.msa.hub.application.service.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.msa.hub.domain.repository.HubRepository;
import org.msa.hub.global.login.CurrentUser;
import org.msa.hub.global.util.GeocodingUtil;
import org.msa.hub.application.dto.hub.HubListDTO;
import org.msa.hub.application.dto.hub.HubRequestDTO;
import org.msa.hub.application.dto.hub.HubResponseDTO;
import org.msa.hub.application.exception.hub.HubNotFoundException;
import org.msa.hub.domain.model.Hub;
import org.msa.hub.global.util.UserRoleCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class HubServiceImpl implements HubService {

    private final HubRepository hubRepository;

    private final GeocodingUtil geocodingUtil;
    private final UserRoleCheck userRoleCheck;


    /**
     * 허브 목록 조회
     * @param pageable 
     * @return
     */
    @Override
    public Page<HubListDTO> getHubList(Pageable pageable) {

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
    public HubResponseDTO createHub(HubRequestDTO hubRequestDTO, CurrentUser currentUser) {

        userRoleCheck.isAdminRole(currentUser.getCurrentUserRole());

        // 주소를 통해 위도 경도 계산
        GeocodingUtil.LatLong latLong = geocodingUtil.getLatLongFromAddress(hubRequestDTO.getAddress());

        Hub hub = Hub.builder()
                .name(hubRequestDTO.getName())
                .address(hubRequestDTO.getAddress())
                .latitude(latLong.getLatitude())
                .longitude(latLong.getLongitude())
                .build();

        hubRepository.save(hub);

        return HubResponseDTO.toDTO(hub);
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
    public HubResponseDTO updateHub(UUID hubId, HubRequestDTO hubRequestDTO, CurrentUser currentUser) {

        log.info("currentUser.getCurrentUserId(): " + currentUser.getCurrentUserId());
        log.info("currentUser.getCurrentUserRole(): " + currentUser.getCurrentUserRole());

        userRoleCheck.isAdminRole(currentUser.getCurrentUserRole());

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
    public void deleteHub(UUID hubId, CurrentUser currentUser) {

        userRoleCheck.isAdminRole(currentUser.getCurrentUserRole());

        Hub hub = hubRepository.findById(hubId).orElseThrow(
                HubNotFoundException::new);

        hub.deleteHub();
    }

}
