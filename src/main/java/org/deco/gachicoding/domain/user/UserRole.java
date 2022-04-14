package org.deco.gachicoding.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    ADMIN("ROLE_ADMIN","관리자"),
    USER("ROLE_USER","회원"),
    GUEST("ROLE_GUEST","비회원");

    private final String key;
    private final String value;

}
