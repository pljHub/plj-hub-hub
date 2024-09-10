package org.msa.hub.application.service.company;

import lombok.RequiredArgsConstructor;
import org.msa.hub.application.dto.company.CompanyListDTO;
import org.msa.hub.application.dto.company.CompanyRequestDTO;
import org.msa.hub.application.dto.company.CompanyResponseDTO;
import org.msa.hub.application.exception.company.CompanyNotFoundException;
import org.msa.hub.domain.model.Company;
import org.msa.hub.domain.repository.CompanyRepository;
import org.msa.hub.domain.repository.HubRepository;
import org.msa.hub.infrastructure.repository.CompanyRepositoryImpl;
import org.msa.hub.application.exception.hub.HubNotFoundException;
import org.msa.hub.domain.model.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final HubRepository hubRepository;

    /**
     * 업체 목록 조회
     *
     * @param page
     * @param size
     * @param sortBy
     * @param orderBy
     * @return
     */
    @Override
    public Page<CompanyListDTO> getCompanyList(int page, int size, String sortBy, boolean orderBy) {

        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = orderBy ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        Page<Company> companyPage = companyRepository.findByIsDeletedFalse(pageable);

        return companyPage.map(CompanyListDTO :: toDTO);
    }

    /**
     * 업체 생성
     * @param companyRequestDTO 
     */
    @Transactional
    @Override
    public void createCompany(CompanyRequestDTO companyRequestDTO) {

        Hub hub = hubRepository.findById(companyRequestDTO.getHubId()).orElseThrow(
                HubNotFoundException::new);

        Company company = Company.builder()
                .name(companyRequestDTO.getName())
                .type(companyRequestDTO.getType())
                .hub(hub)
                .address(companyRequestDTO.getAddress())
                .build();

        companyRepository.save(company);
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
    public CompanyResponseDTO updateCompany(UUID companyId, CompanyRequestDTO companyRequestDTO) {

        Company company = companyRepository.findById(companyId).orElseThrow(
                CompanyNotFoundException::new);

        Hub hub = hubRepository.findById(companyRequestDTO.getHubId()).orElseThrow(
                HubNotFoundException::new);

        company.updateCompany(companyRequestDTO, hub);

        return CompanyResponseDTO.toDTO(company);
    }

    /**
     * 업체 삭제 - 논리적 삭제
     * @param companyId 
     */
    @Transactional
    @Override
    public void deleteCompany(UUID companyId) {

        Company company = companyRepository.findById(companyId).orElseThrow(
                CompanyNotFoundException::new);

        company.deleteCompany();
    }
}
