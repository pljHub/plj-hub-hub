package org.msa.hub.application.service.company;

import org.msa.hub.application.dto.company.CompanyListDTO;
import org.msa.hub.application.dto.company.CompanyRequestDTO;
import org.msa.hub.application.dto.company.CompanyResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CompanyService {

    // 업체 목록 조회
    Page<CompanyListDTO> getCompanyList(int page, int size, String sortBy, boolean orderBy);
    
    // 업체 생성
    void createCompany(CompanyRequestDTO companyRequestDTO);

    // 업체 조회
    CompanyResponseDTO findCompanyById(UUID companyId);

    // 업체 수정
    CompanyResponseDTO updateCompany(UUID companyId, CompanyRequestDTO companyRequestDTO);

    // 업체 삭제
    void deleteCompany(UUID companyId);
    
}
