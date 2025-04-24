package com.chaeum.api.domain.title.repository;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.title.entity.Title;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    Optional<Title> findTopByMemberOrderByCreatedAtDesc(Member member);
}
