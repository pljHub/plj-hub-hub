package org.msa.hub.global.exception.domain;

import lombok.RequiredArgsConstructor;
import org.msa.hub.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum HubPathErrorCode implements ErrorCode {

    HUB_PATH_NOT_FOUND(HttpStatus.NOT_FOUND, "허브 경로가 존재하지 않습니다."),
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
