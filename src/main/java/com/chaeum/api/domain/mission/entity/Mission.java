package com.chaeum.api.domain.mission.entity;

import com.chaeum.api.domain.mission.dto.request.MissionCreateRequest;
import com.chaeum.api.domain.mission.dto.request.MissionUpdateRequest;
import com.chaeum.api.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "mission")
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mission_image_url", nullable = false)
    private String missionImageUrl;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "mission_type", nullable = false)
    private MissionType type;

    public static Mission toEntity(MissionCreateRequest missionCreateRequest) {
        return Mission.builder()
            .name(missionCreateRequest.getName())
            .missionImageUrl(missionCreateRequest.getMissionImageUrl())
            .description(missionCreateRequest.getDescription())
            .type(missionCreateRequest.getType())
            .build();
    }

    public void update(MissionUpdateRequest missionUpdateRequest) {
        Optional.ofNullable(missionUpdateRequest.getName()).ifPresent(this::setName);
        Optional.ofNullable(missionUpdateRequest.getMissionImage()).ifPresent(this::setMissionImageUrl);
        Optional.ofNullable(missionUpdateRequest.getDescription()).ifPresent(this::setDescription);
        Optional.ofNullable(missionUpdateRequest.getType()).ifPresent(this::setType);
    }
}
