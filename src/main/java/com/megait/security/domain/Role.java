package com.megait.security.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
// Role.ADMIN
// Role.USER

// "USER"
// "ADMIN"