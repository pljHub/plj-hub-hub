package org.msa.hub.application.exception.hub;

import org.msa.hub.global.exception.PljHubException;
import org.msa.hub.global.exception.domain.HubErrorCode;

public class HubNotFoundException extends PljHubException {
    public HubNotFoundException() {
        super(HubErrorCode.HUB_NOT_FOUND);
    }
}
