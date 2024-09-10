package org.msa.hub.global.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentUser {
    private Long currentUserId;
    private String currentUserRole;
}
