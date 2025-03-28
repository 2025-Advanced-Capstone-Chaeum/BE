package com.chaeum.api.domain.cat.dto.response;

import com.chaeum.api.domain.cat.entity.Cat;
import java.math.BigInteger;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CatInformationResponse {

    private Long id;

    private int level;

    private BigInteger experiencePoint;

    private double levelUpPercentage;

    public static CatInformationResponse toDto(Cat cat) {
        return CatInformationResponse.builder()
            .id(cat.getId())
            .level(cat.getLevel())
            .experiencePoint(cat.getExperiencePoint())
            .levelUpPercentage(cat.getLevelUpPercentage())
            .build();
    }
}
