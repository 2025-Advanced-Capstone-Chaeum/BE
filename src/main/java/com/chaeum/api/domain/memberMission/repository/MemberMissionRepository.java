package com.chaeum.api.domain.memberMission.repository;

import com.chaeum.api.domain.memberMission.entity.MemberMission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    List<MemberMission> findByMemberId(Long memberId);
}
