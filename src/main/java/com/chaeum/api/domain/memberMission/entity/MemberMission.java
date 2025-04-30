package com.chaeum.api.domain.memberMission.entity;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.mission.entity.Mission;
import com.chaeum.api.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "member_mission")
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_mission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Enumerated(EnumType.STRING)
    @Column(name = "mission_status", nullable = false)
    private MissionStatus status;

    @Column(name = "current_count", nullable = false)
    private int currentCount;

    @Column(name = "progress_count", nullable = false)
    private int progressCount;

    public static MemberMission create(Member member, Mission mission, int progressCount) {
        return MemberMission.builder()
            .member(member)
            .mission(mission)
            .status(MissionStatus.PENDING)
            .currentCount(0)
            .progressCount(progressCount)
            .build();
    }

    public void reset() {
        if (!isCompleted()) {
            this.currentCount = 0;
            this.status = MissionStatus.FAILED;
        }
    }

    public void increaseProgress() {
        if (isCompleted() || isFailed()) {
            return;
        }
        updateStatusToInProgress();
        increaseCurrentCount();
        if (isMissionCompleted()) {
            completeMission();
        }
    }

    private boolean isCompleted() {
        return this.status == MissionStatus.COMPLETED;
    }

    private boolean isFailed() {
        return this.status == MissionStatus.FAILED;
    }

    private void increaseCurrentCount() {
        this.currentCount++;
    }

    private boolean isMissionCompleted() {
        return this.currentCount >= this.progressCount;
    }

    private void updateStatusToInProgress() {
        if (status == MissionStatus.PENDING) {
            this.status = MissionStatus.IN_PROGRESS;
        }
    }

    private void completeMission() {
        this.status = MissionStatus.COMPLETED;
    }
}
