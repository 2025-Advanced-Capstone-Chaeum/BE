package com.chaeum.api.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    DONOR("ROLE_DONOR", "Donor (기부자)", 1),
    RECIPIENT("ROLE_RECIPIENT", "Recipient (수혜자)", 2),
    ADMIN("ROLE_ADMIN", "Administrator (관리자)", 3);

    private final String key;         // 역할 키  (ex: ROLE_DONOR)
    private final String description; // 역할 설명 (ex: 기부자)
    private final int level;          // 역할 레벨 (숫자가 높을수록 높은 권한)

    // ex. 관리자가 권한 검증 시 사용
    public boolean isHigherThan(Role role) {
        return this.level > role.level;
    }

    // ex. 특정 페이지에 접근할 때 권한 검증 시 사용
    public boolean isHigherThanOrEqual(Role role) {
        return this.level >= role.level;
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
