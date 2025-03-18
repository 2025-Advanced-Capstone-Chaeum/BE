package com.chaeum.api.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    DONOR("ROLE_DONOR", "Donor (기부자)"),
    RECIPIENT("ROLE_RECIPIENT", "Recipient (수혜자)"),
    ADMIN("ROLE_ADMIN", "Administrator (관리자)");

    private final String key;
    private final String description;

    public Long toRoleLevel() {
        return switch (this) {
            case DONOR -> 0L;
            case RECIPIENT -> 1L;
            case ADMIN -> 2L;
        };
    }

    public boolean isHigherThan(Role role) {
        return this.toRoleLevel() > role.toRoleLevel();
    }

    public boolean isHigherThanOrEqual(Role role) {
        return this.toRoleLevel() >= role.toRoleLevel();
    }

    public boolean isDonorRole() {
        return this == DONOR;
    }

    public boolean isRecipientRole() {
        return this == RECIPIENT;
    }

    public boolean isAdminRole() {
        return this == ADMIN;
    }
}
