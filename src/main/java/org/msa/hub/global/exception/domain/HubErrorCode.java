package org.msa.hub.global.exception.domain;

import lombok.RequiredArgsConstructor;
import org.msa.hub.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum HubErrorCode implements ErrorCode {

    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브가 존재하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
