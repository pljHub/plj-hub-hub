package org.msa.hub.application.exception.hub;

import org.msa.hub.global.exception.PljHubException;
import org.msa.hub.global.exception.domain.HubErrorCode;

public class HubAccessDeniedException extends PljHubException {

    public HubAccessDeniedException(){
        super(HubErrorCode.ACCESS_DENIED);
    }
}
