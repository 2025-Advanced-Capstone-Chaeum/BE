package com.chaeum.api.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterBeneficiaryRequest {

    @NotNull
    @Schema(description = "수혜자 판정 결과", example = "true")
    private boolean result;
}
