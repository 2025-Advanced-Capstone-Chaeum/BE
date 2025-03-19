package com.chaeum.api.global.auth.repository;

import com.chaeum.api.global.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository  extends CrudRepository<RefreshToken, String> {
    // JPA Repository가 아닌 Crud Repository를 상속받아야 함
}
