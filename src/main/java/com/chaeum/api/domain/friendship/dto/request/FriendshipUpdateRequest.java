package com.chaeum.api.domain.friendship.dto.request;

import com.chaeum.api.domain.friendship.entity.FriendshipStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendshipUpdateRequest {

    @NotNull
    @Schema(description = "대상 친구 관계 ID", example = "2")
    private Long friendshipId;

    @NotNull
    @Schema(description = "요청 상태", example = "ACCEPTED")
    private FriendshipStatus friendshipStatus;
}
