package org.msa.hub.application.exception.hubPath;

import org.msa.hub.global.exception.PljHubException;
import org.msa.hub.global.exception.domain.HubPathErrorCode;

public class HubPathNotFoundException extends PljHubException {
    public HubPathNotFoundException() {
        super(HubPathErrorCode.HUB_PATH_NOT_FOUND);
    }
}
