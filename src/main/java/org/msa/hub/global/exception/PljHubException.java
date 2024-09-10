package org.msa.hub.global.exception;

import lombok.Getter;

@Getter
public class PljHubException extends RuntimeException {
    private final ErrorCode errorCode;
    public PljHubException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
