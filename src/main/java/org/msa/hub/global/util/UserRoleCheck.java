package org.msa.hub.global.util;

import org.msa.hub.application.exception.hub.HubAccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserRoleCheck {

    // ADMIN Check
    public boolean isAdminRole(String role) {

        if ("ADMIN".equals(role)) {
            return true;  // 권한이 "ADMIN"이면 true 반환
        } else {
            throw new HubAccessDeniedException();  // "ADMIN"이 아니면 예외 발생
        }
    }

    // 업체 생성 삭제 권한 관리 - ADMIN || HUB_MANAGER
    public boolean isAdminOrHubManagerForCompany(String role, UUID userHubId, UUID hubId) {

        if (role.equals("ADMIN")){
            return true;
        } else if( role.equals("HUB_MANAGER") && userHubId.equals(hubId)) {
            return true;
        } else {
            throw new HubAccessDeniedException();
        }
    }

    // 업체 수정 권한 관리 -- ADMIN || HUB_MANGER || COMPANY_MANAGER
    public boolean isAdminOrHubManagerOrCompanyManagerForCompany(String role, UUID hubId, UUID companyHubId, UUID findCompanyId, UUID companyId) {

        if (role.equals("ADMIN")) {
            return true;
        } else if (role.equals("HUB_MANAGER") && hubId.equals(companyHubId)) {
            return true;
        } else if (role.equals("COMPANY_MANAGER") && findCompanyId.equals(companyId)) {
            return true;
        } else {
            throw new HubAccessDeniedException();
        }
    }

    // 상품 권한 관리 - ADMIN || HUB_MANAGER(hub_id == Product.hub.id) || Company_MANAGER
    public boolean isAdminOrHubManagerOrCompanyManagerForProduct(String role, UUID userHubId, UUID hubId, UUID userCompanyHubId, UUID companyHubId) {
        if (role.equals("ADMIN")) {
            return true;

            // 허브 매니저: CurrentUser로 조회한 HubId == Request로 받은 hub의 hubID
        } else if (role.equals("HUB_MANAGER") && userHubId.equals(hubId)) {
            return true;

            // 허브 업체: 자신의 업체의 상품만 생성 및 수정 가능 CurrentUser로 조회한 Company.hub.id == Request로 받은 CompanyId의 hubId
        } else if(role.equals("COMPANY_MANAGER") && userCompanyHubId.equals(companyHubId)){
            return true;
        }else {
            throw new HubAccessDeniedException();
        }
    }
}