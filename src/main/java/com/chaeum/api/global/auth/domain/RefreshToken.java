package com.chaeum.api.global.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 1209600)
public class RefreshToken {

    @Id
    private String memberId;
    private String memberEmail;
    private String refreshToken;

    public RefreshToken(String memberId, String memberEmail, String refreshToken) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.refreshToken = refreshToken;
    }

    // 토큰 갱신
    public void updateToken(String newToken) {
        this.refreshToken = newToken;
    }
}
