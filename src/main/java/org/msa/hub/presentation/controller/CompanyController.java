package org.msa.hub.presentation.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.msa.hub.application.dto.company.CompanyListDTO;
import org.msa.hub.application.dto.company.CompanyRequestDTO;
import org.msa.hub.application.dto.company.CompanyResponseDTO;
import org.msa.hub.application.service.company.CompanyService;
import org.msa.hub.global.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
@Log4j2(topic = "Company Controller")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // 업체 목록 조회
    @GetMapping("")
    public ResponseEntity<ResponseDto<Page<CompanyListDTO>>> getCompanyList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                                                          @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                          @RequestParam(value = "orderBy", defaultValue = "true") boolean orderBy) {

        log.info("CompanyController | GET getCompanyList");

        Page<CompanyListDTO> companyListDTOPage = companyService.getCompanyList(page, size, sortBy, orderBy);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), companyListDTOPage));
    }

    // 업체 조회
    @GetMapping("/{companyId}")
    public ResponseEntity<ResponseDto<CompanyResponseDTO>> findCompanyById(@PathVariable UUID companyId) {

        log.info("CompanyController | GET findCompanyById");

        CompanyResponseDTO result = companyService.findCompanyById(companyId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), result));
    }

    // 업체 생성
    @PostMapping("")
    public ResponseEntity<ResponseDto<String>> createCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {

        log.info("CompanyController | POST createCompany");

        companyService.createCompany(companyRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseDto.success(HttpStatus.CREATED.name(), "업체가 성공적으로 생성되었습니다."));
    }

    // 업체 수정
    @PutMapping("/{companyId}")
    public ResponseEntity<ResponseDto<CompanyResponseDTO>> updateCompany(@PathVariable UUID companyId, @RequestBody CompanyRequestDTO companyRequestDTO) {

        log.info("CompanyController | PUT updateCompany");

        CompanyResponseDTO result = companyService.updateCompany(companyId, companyRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), result));
    }

    // 업체 삭제 - 논리적 삭제
    @DeleteMapping("/{companyId}")
    public ResponseEntity<ResponseDto<String>> deleteCompany(@PathVariable UUID companyId){

        log.info("CompanyController | DELETE deleteCompany");

        companyService.deleteCompany(companyId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), "업체가 성공적으로 삭제되었습니다."));
    }
}
