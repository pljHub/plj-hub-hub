package org.msa.hub.application.service.hubPath;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.msa.hub.domain.repository.HubPathRepository;
import org.msa.hub.domain.repository.HubRepository;
import org.msa.hub.global.util.DistanceMatrixUtil;
import org.msa.hub.application.exception.hub.HubNotFoundException;
import org.msa.hub.domain.model.Hub;
import org.msa.hub.infrastructure.repository.HubRepositoryImpl;
import org.msa.hub.application.dto.hubPath.HubPathListDTO;
import org.msa.hub.application.dto.hubPath.HubPathRequestDTO;
import org.msa.hub.application.dto.hubPath.HubPathResponseDTO;
import org.msa.hub.application.dto.hubPath.HubPathSequenceDTO;
import org.msa.hub.application.exception.hubPath.HubPathNotFoundException;
import org.msa.hub.domain.model.HubPath;
import org.msa.hub.infrastructure.repository.HubPathRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class HubPathServiceImpl implements HubPathService {

    private final HubRepository hubRepository;
    private final HubPathRepository hubPathRepository;
    private final DistanceMatrixUtil distanceMatrixUtil;

    /**
     * 허브 경로 목록 조회
     * @param page 
     * @param size
     * @param sortBy
     * @param orderBy
     * @return
     */
    @Override
    public Page<HubPathListDTO> getHubPathList(int page, int size, String sortBy, boolean orderBy) {

        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = orderBy ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        Page<HubPath> hubPathPage = hubPathRepository.findAllByIsDeletedFalse(pageable);

        return hubPathPage.map(HubPathListDTO::toDTO);
    }

    /**
     * 허브 경로 생성
     * @param hubPathRequestDTO 
     * @throws UnsupportedEncodingException
     */
    @Transactional
    @Override
    public void createHubPath(HubPathRequestDTO hubPathRequestDTO) throws UnsupportedEncodingException {

        Hub startHub = hubRepository.findById(hubPathRequestDTO.getStartHubId()).orElseThrow(
                HubNotFoundException::new);

        Hub destHub = hubRepository.findById(hubPathRequestDTO.getDestHubId()).orElseThrow(
                HubNotFoundException::new);


        log.info("startHub Address: "+startHub.getAddress());
        log.info("destHub Address: "+destHub.getAddress());

        // startHub와 destHub의 주소를 비교해 시간 계산
        String duration = distanceMatrixUtil.getDurationFromGoogleMaps(startHub.getAddress(), destHub.getAddress());

        log.info("duration: " + duration);

        HubPath hubPath = HubPath.builder()
                .startHub(startHub)
                .destHub(destHub)
                .duration(duration)
                .routePath(hubPathRequestDTO.getRoutePath())
                .build();

        hubPathRepository.save(hubPath);
    }

    /**
     * 허브 경로 조회
     * @param hubPathId 
     * @return
     */
    @Override
    public HubPathResponseDTO findHubPathById(UUID hubPathId) {

        HubPath hubPath = hubPathRepository.findById(hubPathId).orElseThrow(
                HubPathNotFoundException::new);

        return HubPathResponseDTO.toDTO(hubPath);
    }

    /**
     * 허브 경로 수정
     * @param hubPathId
     * @param hubPathRequestDTO
     * @return
     * @throws UnsupportedEncodingException
     */
    @Transactional
    @Override
    public HubPathResponseDTO updateHubPath(UUID hubPathId, HubPathRequestDTO hubPathRequestDTO) throws UnsupportedEncodingException {

        HubPath hubPath = hubPathRepository.findById(hubPathId).orElseThrow(
                HubPathNotFoundException::new);

        Hub startHub = hubRepository.findById(hubPathRequestDTO.getStartHubId()).orElseThrow(
                HubNotFoundException::new);

        Hub destHub = hubRepository.findById(hubPathRequestDTO.getDestHubId()).orElseThrow(
                HubNotFoundException::new);

        // startHub와 destHub의 주소를 비교해 시간 계산
        String duration = distanceMatrixUtil.getDurationFromGoogleMaps(startHub.getAddress(), destHub.getAddress());

        hubPath.updateHubPath(startHub, destHub, duration, hubPath.getRoutePath());

        return HubPathResponseDTO.toDTO(hubPath);
    }

    /**
     * 허브 경로 삭제 - 논리적 삭제
     * @param hubPathId 
     */
    @Transactional
    @Override
    public void deleteHubPath(UUID hubPathId) {

        HubPath hubPath = hubPathRepository.findById(hubPathId).orElseThrow(
                HubPathNotFoundException::new);

        // 논리적 삭제
        hubPath.deleteHubPath();
    }

    /**
     * 허브 경로 시퀀스 조회
     * @param startHubId 
     * @param destHubId
     * @return
     */
    @Override
    public List<HubPathSequenceDTO> getHubPathSequence(UUID startHubId, UUID destHubId) {

        List<Object[]> hubPathList = hubPathRepository.getHubPathSequence(startHubId,destHubId);

        return hubPathList.stream().map(data -> HubPathSequenceDTO.builder()
                .startHubId((UUID) data[0])
                .destHubId((UUID) data[1])
                .sequence((Integer) data[2])
                .routePath((String) data[3])
                .build()).collect(Collectors.toList());
    }
}
