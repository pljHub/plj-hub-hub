package org.msa.hub.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.msa.hub.application.dto.company.CompanyRequestDTO;
import org.msa.hub.domain.enums.CompanyTypeEnum;
import org.msa.hub.global.audit.AuditingEntity;

import java.util.UUID;

@Entity
@Table(name = "p_company")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Company extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CompanyTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "hub_id", nullable = false)
    private Hub hub;

    @Column(name = "address", nullable = false)
    private String address;

    // 업체 수정
    public void updateCompany(CompanyRequestDTO companyRequestDTO, Hub hub) {
        this.name = companyRequestDTO.getName();
        this.type = companyRequestDTO.getType();
        this.hub = hub;
        this.address = companyRequestDTO.getAddress();
    }

    // 업체 삭제 - 논리적 삭제
    public void deleteCompany(){
        this.setIsDeleted(true);
    }
}
