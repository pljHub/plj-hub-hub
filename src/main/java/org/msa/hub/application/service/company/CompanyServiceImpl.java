package org.msa.hub.application.service.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.msa.hub.application.dto.company.CompanyListDTO;
import org.msa.hub.application.dto.company.CompanyRequestDTO;
import org.msa.hub.application.dto.company.CompanyResponseDTO;
import org.msa.hub.application.exception.company.CompanyNotFoundException;
import org.msa.hub.domain.model.Company;
import org.msa.hub.domain.repository.CompanyRepository;
import org.msa.hub.domain.repository.HubRepository;
import org.msa.hub.global.dto.ResponseDto;
import org.msa.hub.global.login.CurrentUser;
import org.msa.hub.global.util.UserRoleCheck;
import org.msa.hub.infrastructure.client.UserClient;
import org.msa.hub.infrastructure.dto.UserResponseDTO;
import org.msa.hub.application.exception.hub.HubNotFoundException;
import org.msa.hub.domain.model.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final HubRepository hubRepository;
    private final UserRoleCheck userRoleCheck;
    private final UserClient userClient;

    /**
     * 업체 목록 조회
     * @param pageable
     * @return
     */
    @Override
    public Page<CompanyListDTO> getCompanyList(Pageable pageable) {

        Page<Company> companyPage = companyRepository.findByIsDeletedFalse(pageable);

        return companyPage.map(CompanyListDTO :: toDTO);
    }

    /**
     * 업체 생성
     *
     * @param companyRequestDTO
     * @return
     */
    @Transactional
    @Override
    public CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO, CurrentUser currentUser) {

        Hub hub = hubRepository.findById(companyRequestDTO.getHubId()).orElseThrow(
                HubNotFoundException::new);

        // 사용자 값 추출
        ResponseEntity<ResponseDto<UserResponseDTO>> response = userClient.findUserById(currentUser.getCurrentUserId());
        UserResponseDTO user = response.getBody().getData();

        // 권한 검증
        userRoleCheck.isAdminOrHubManagerForCompany(currentUser.getCurrentUserRole(), user.getHubId(), hub.getId());

        Company company = Company.builder()
                .name(companyRequestDTO.getName())
                .type(companyRequestDTO.getType())
                .hub(hub)
                .address(companyRequestDTO.getAddress())
                .build();


        company.setCreatedBy(user.getId());
        companyRepository.save(company);
        return CompanyResponseDTO.toDTO(company);
    }

    /**
     * 업체 조회
     * @param companyId 
     * @return
     */
    @Override
    public CompanyResponseDTO findCompanyById(UUID companyId) {

        Company company = companyRepository.findById(companyId).orElseThrow(
                CompanyNotFoundException::new);

        return CompanyResponseDTO.toDTO(company);
    }

    /**
     * 업체 수정
     * @param companyId
     * @param companyRequestDTO
     * @return
     */
    @Transactional
    @Override
    public CompanyResponseDTO updateCompany(UUID companyId, CompanyRequestDTO companyRequestDTO, CurrentUser currentUser) {

        Company company = companyRepository.findById(companyId).orElseThrow(
                CompanyNotFoundException::new);

        Hub hub = hubRepository.findById(companyRequestDTO.getHubId()).orElseThrow(
                HubNotFoundException::new);

        log.info("currentUser.getCurrentUserId(): " + currentUser.getCurrentUserId());
        Long userId = currentUser.getCurrentUserId();

        // 권한 검증
        ResponseEntity<ResponseDto<UserResponseDTO>> response = userClient.findUserById(userId);
        UserResponseDTO user = response.getBody().getData();
        userRoleCheck.isAdminOrHubManagerOrCompanyManagerForCompany(currentUser.getCurrentUserRole(), user.getHubId(), company.getHub().getId(), user.getCompanyId(), companyId);

        company.updateCompany(companyRequestDTO, hub, user.getId());

        return CompanyResponseDTO.toDTO(company);
    }

    /**
     * 업체 삭제 - 논리적 삭제
     * @param companyId 
     */
    @Transactional
    @Override
    public void deleteCompany(UUID companyId, CurrentUser currentUser) {

        Company company = companyRepository.findById(companyId).orElseThrow(
                CompanyNotFoundException::new);

        // 권한 검증
        ResponseEntity<ResponseDto<UserResponseDTO>> response = userClient.findUserById(currentUser.getCurrentUserId());
        UserResponseDTO user = response.getBody().getData();
        userRoleCheck.isAdminOrHubManagerForCompany(currentUser.getCurrentUserRole(), user.getHubId(), company.getHub().getId());

        company.deleteCompany(user.getId());
    }
}
