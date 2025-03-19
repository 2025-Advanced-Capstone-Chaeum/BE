package com.chaeum.api.domain.member.entity;

import lombok.RequiredArgsConstructor;
import java.util.EnumSet;

@RequiredArgsConstructor
public enum Role {

    DONOR("ROLE_DONOR", "Donor (기부자)"),
    RECIPIENT("ROLE_RECIPIENT", "Recipient (수혜자)"),
    ADMIN("ROLE_ADMIN", "Administrator (관리자)");

    private final String key;
    private final String description;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public Long toRoleLevel() {
        return (long) this.ordinal();
    }

    public boolean isHigherThan(Role role) {
        return this.compareTo(role) > 0;
    }

    public boolean isHigherThanOrEqual(Role role) {
        return this.compareTo(role) >= 0;
    }

    private static final EnumSet<Role> DONOR_ROLES = EnumSet.of(DONOR);
    private static final EnumSet<Role> RECIPIENT_ROLES = EnumSet.of(RECIPIENT);
    private static final EnumSet<Role> ADMIN_ROLES = EnumSet.of(ADMIN);

    public boolean isDonorRole() {
        return DONOR_ROLES.contains(this);
    }

    public boolean isRecipientRole() {
        return RECIPIENT_ROLES.contains(this);
    }

    public boolean isAdminRole() {
        return ADMIN_ROLES.contains(this);
    }
}