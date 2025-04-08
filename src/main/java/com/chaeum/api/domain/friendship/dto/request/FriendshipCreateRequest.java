package com.chaeum.api.domain.friendship.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendshipCreateRequest {

    @NotNull
    @Schema(description = "친구 ID", example = "2")
    private Long receiverId;
}
