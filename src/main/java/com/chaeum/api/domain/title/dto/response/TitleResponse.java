package com.chaeum.api.domain.title.dto.response;

import com.chaeum.api.domain.title.entity.Title;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TitleResponse {

    private String name;

    public static TitleResponse toDto(Title title) {
        return TitleResponse.builder()
            .name(title.getName().getDisplayName())
            .build();
    }

    public static TitleResponse empty() {
        return TitleResponse.builder()
            .name(null)
            .build();
    }
}
