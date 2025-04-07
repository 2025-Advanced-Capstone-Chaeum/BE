package com.chaeum.api.domain.title.repository;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.title.entity.Title;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    List<Title> findByIsActiveTrue();

    Optional<Title> findByMemberAndIsActiveTrue(Member member);
}
