package com.chaeum.api.global.auth.repository;

import com.chaeum.api.global.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository  extends CrudRepository<RefreshToken, String> {

}
