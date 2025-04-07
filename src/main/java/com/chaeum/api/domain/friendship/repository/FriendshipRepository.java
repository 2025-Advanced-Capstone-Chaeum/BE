package com.chaeum.api.domain.friendship.repository;

import com.chaeum.api.domain.friendship.entity.Friendship;
import com.chaeum.api.domain.friendship.entity.FriendshipStatus;
import com.chaeum.api.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsByMemberAndFriendAndStatus(Member member, Member friend, FriendshipStatus status);

    boolean existsByMemberAndFriend(Member member, Member friend);
}
