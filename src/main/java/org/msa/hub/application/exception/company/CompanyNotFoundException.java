package org.msa.hub.application.exception.company;

import org.msa.hub.global.exception.PljHubException;
import org.msa.hub.global.exception.domain.CompanyErrorCode;

public class CompanyNotFoundException extends PljHubException {
    public CompanyNotFoundException(){
        super(CompanyErrorCode.COMPANY_NOT_FOUND);
    }
}
