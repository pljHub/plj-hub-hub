package org.msa.hub.application.service.company;

import org.msa.hub.application.dto.company.CompanyListDTO;
import org.msa.hub.application.dto.company.CompanyRequestDTO;
import org.msa.hub.application.dto.company.CompanyResponseDTO;
import org.msa.hub.global.login.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CompanyService {

    // 업체 목록 조회
    Page<CompanyListDTO> getCompanyList(Pageable pageable);
    
    // 업체 생성
    CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO, CurrentUser currentUser);

    // 업체 조회
    CompanyResponseDTO findCompanyById(UUID companyId);

    // 업체 수정
    CompanyResponseDTO updateCompany(UUID companyId, CompanyRequestDTO companyRequestDTO, CurrentUser currentUser);

    // 업체 삭제
    void deleteCompany(UUID companyId, CurrentUser currentUser);
    
}
