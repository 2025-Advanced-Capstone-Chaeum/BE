package com.chaeum.api.global.auth.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 1209600)
public class RefreshToken {

    @Id
    private String memberId;
    private String memberEmail;
    // redis 저장소의 key 값으로는 {value}:{@Id 어노테이션을 붙여준 값이 됨}
    // 예를 들어 여기서는 memberId 에 ABC 라는 값을 넣는다면 "memberId":"ABC" 가된다

    public RefreshToken(String memberId, String memberEmail) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
    }
}
