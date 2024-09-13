package org.msa.hub.infrastructure.client;

import org.msa.hub.global.dto.ResponseDto;
import org.msa.hub.infrastructure.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/users/{id}/internal")
    ResponseEntity<ResponseDto<UserResponseDTO>> findUserById(@PathVariable Long id);
}
